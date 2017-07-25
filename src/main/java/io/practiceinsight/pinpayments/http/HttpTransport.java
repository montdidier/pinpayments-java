/*
 * Copyright (c) 2016 Practice Insight
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.practiceinsight.pinpayments.http;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * URL Fetcher which can send HTTP requests using core JDK API.
 *
 * @author delight.wjk@gmail.com
 */
@SuppressWarnings("ClassDataAbstractionCoupling")
public class HttpTransport {

  private static final Logger log = LoggerFactory.getLogger(HttpTransport.class); // NOPMD

  private static final int READ_TIMEOUT = 10000;
  private static final int CONNECT_TIMEOUT = 15000;

  private String url;
  private int readTimeout = HttpTransport.READ_TIMEOUT;
  private int connectTimeout = HttpTransport.CONNECT_TIMEOUT;
  private HttpMethod httpMethod = HttpMethod.GET;
  private List<AbstractMap.SimpleEntry<String, String>> headers = Lists.newArrayList();
  private List<AbstractMap.SimpleEntry<String, String>> params = Lists.newArrayList();
  private ResourceManager resourceManager = new ResourceManager();

  /**
   * Specifies URL to this {@link HttpTransport} instance.
   *
   * @param theUrl The URL specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport setUrl(final String theUrl) {
    this.url = theUrl;
    return this;
  }

  /**
   * Specifies read timeout to this {@link HttpTransport} instance.
   *
   * @param theReadTimeout The read timeout specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport setReadTimeout(final int theReadTimeout) {
    this.readTimeout = theReadTimeout;
    return this;
  }

  /**
   * Specifies connect timeout to this {@link HttpTransport} instance.
   *
   * @param theConnectTimeout The connect timeout specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport setConnectTimeout(final int theConnectTimeout) {
    this.connectTimeout = theConnectTimeout;
    return this;
  }

  /**
   * Specifies request method to this {@link HttpTransport} instance.
   *
   * @param theHttpMethod The request method specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport setHttpMethod(final HttpMethod theHttpMethod) {
    this.httpMethod = theHttpMethod;
    return this;
  }

  /**
   * Adds a new parameter to this {@link HttpTransport} instance.
   *
   * @param name The parameter name specified.
   * @param value The parameter value specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport addHeader(final String name, final String value) {
    this.headers.add(new AbstractMap.SimpleEntry<>(name, value));
    return this;
  }

  /**
   * Adds a new parameter to this {@link HttpTransport} instance.
   *
   * @param name The parameter name specified.
   * @param value The parameter value specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport addParam(final String name, final String value) {
    this.params.add(new AbstractMap.SimpleEntry<>(name, value));
    return this;
  }

  /**
   * Adds a new parameter to this {@link HttpTransport} instance.
   *
   * @param newParams The parameters specified.
   * @return This {@link HttpTransport} instance.
   */
  public final HttpTransport addParams(final Map<String, String> newParams) {
    for (final Entry<String, String> entry : newParams.entrySet()) {
      addParam(entry.getKey(), entry.getValue());
    }
    return this;
  }

  /**
   * Executes the fetching logic.
   *
   * @return The response of the HTTP request.
   * @throws IOException If IO errors occur.
   */
  public final HttpResponse execute() throws IOException {
    Preconditions.checkNotNull(url);
    final HttpURLConnection conn = prepareConnection();
    conn.connect();
    return readResponse(conn);
  }

  /**
   * Prepares a {@link HttpURLConnection} instance with variables specified in
   * this {@link HttpTransport} instance.
   *
   * @return The {@link HttpURLConnection} instance created.
   * @throws IOException If IO errors occur.
   */
  private HttpURLConnection prepareConnection() throws IOException {
    final String queryStr = getQuery(params);
    if (httpMethod == HttpMethod.GET) {
      url = url + "?" + queryStr;
    }
    final URL urlObj = new URL(url);
    final HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
    conn.setReadTimeout(readTimeout);
    conn.setConnectTimeout(connectTimeout);
    conn.setRequestMethod(httpMethod.name());
    conn.setDoInput(true);
    conn.setDoOutput(true);
    setHeaders(conn);
    if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
      writeToConnection(conn, queryStr);
    }
    return conn;
  }

  /**
   * Sets headers in HTTP connection.
   *
   * @param conn The HTTP connection specified.
   */
  private void setHeaders(final HttpURLConnection conn) {
    for (final AbstractMap.SimpleEntry<String, String> header : headers) {
      conn.setRequestProperty(header.getKey(), header.getValue());
    }
  }

  /**
   * Reads response from the HTTP connection.
   *
   * @param conn The HTTP connection specified.
   * @return Response object.
   * @throws IOException If IO errors occur.
   */
  private HttpResponse readResponse(final HttpURLConnection conn) throws IOException {
    final int responseCode = conn.getResponseCode();
    final String content = readContent(conn);
    final String error = readError(conn);
    return new HttpResponse(responseCode, content, error);
  }

  /**
   * Gets query string of parameters.
   *
   * @return The query string of parameters.
   * @throws UnsupportedEncodingException If encoding errors occur.
   */
  public final String getQueryString() throws UnsupportedEncodingException {
    return getQuery(params);
  }

  /**
   * Reads string content from the {@link HttpURLConnection} object.
   *
   * @param conn The {@link HttpURLConnection} object specified.
   * @return The string content.
   * @throws IOException If IO errors occur.
   */
  private String readContent(final HttpURLConnection conn) {
    String content = StringUtils.EMPTY;
    try {
      final InputStream inputStream = conn.getInputStream();
      content = resourceManager.with(inputStream).run(new ResourceHandler<String>() {
        @Override
        public String handle(final Closeable closeable) throws IOException {
          return IOUtils.toString(inputStream, Charsets.UTF_8);
        }
      });
    } catch (final IOException ex) {
      log.error(ex.getMessage(), ex);
    }
    return content;
  }

  /**
   * Reads string content from the {@link HttpURLConnection} object.
   *
   * @param conn The {@link HttpURLConnection} object specified.
   * @return The string content.
   * @throws IOException If IO errors occur.
   */
  private String readError(final HttpURLConnection conn) throws IOException {
    final InputStream errorStream = conn.getErrorStream();
    String error = null;
    if (errorStream != null) {
      error = resourceManager.with(errorStream).run(new ResourceHandler<String>() {
        @Override
        public String handle(final Closeable closeable) throws IOException {
          return IOUtils.toString(errorStream, Charsets.UTF_8);
        }
      });
    }
    return error;
  }

  /**
   * Writes body data string to the {@link HttpURLConnection} object.
   *
   * @param conn The {@link HttpURLConnection} object specified.
   * @param bodyData The body data string specified.
   * @throws IOException If IO errors occur.
   */
  private void writeToConnection(final HttpURLConnection conn, final String bodyData)
      throws IOException {
    final OutputStream outputStream = conn.getOutputStream();
    resourceManager.with(outputStream).run(new VoidResourceHandler() {
      @Override
      public void handle(final Closeable closeable) throws IOException {
        final BufferedWriter writer =
            new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        resourceManager.with(writer).run(new VoidResourceHandler() {
          @Override
          public void handle(final Closeable closeable) throws IOException {
            writer.write(bodyData);
            writer.flush();
          }
        });
      }
    });
  }

  /**
   * Gets query string from parameters specified.
   *
   * @param theParams The parameters specified.
   * @return The query string created.
   * @throws UnsupportedEncodingException If encoding related errors occur.
   */
  private String getQuery(final List<AbstractMap.SimpleEntry<String, String>> theParams)
      throws UnsupportedEncodingException {
    final StringBuilder result = new StringBuilder();
    boolean first = true;
    for (final AbstractMap.SimpleEntry<String, String> pair : theParams) {
      if (first) {
        first = false;
      } else {
        result.append("&");
      }
      // result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
      result.append(pair.getKey());
      result.append("=");
      result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
    }
    return result.toString();
  }
}
