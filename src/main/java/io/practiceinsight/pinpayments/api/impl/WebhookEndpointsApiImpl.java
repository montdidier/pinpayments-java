/*
 * Copyright (c) 2017 Practice Insight
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

package io.practiceinsight.pinpayments.api.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import io.practiceinsight.pinpayments.ParamsHelper;
import io.practiceinsight.pinpayments.api.WebhookEndpointsApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.DeletionResult;
import io.practiceinsight.pinpayments.pojo.ImmutableWebhookEndpointListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableWebhookEndpointResponse;
import io.practiceinsight.pinpayments.pojo.WebhookEndpoint;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointListResponse;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointResponse;

/**
 * Implementation of {@link WebhookEndpointsApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class WebhookEndpointsApiImpl extends AbstractBaseApi implements WebhookEndpointsApi {

  private static final Logger log = LoggerFactory.getLogger(WebhookEndpointsApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;
  @Inject
  private Gson gson;

  @Override
  public final WebhookEndpointResponse create(final WebhookEndpoint endpoint) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(endpoint);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableWebhookEndpointResponse.class);
  }

  @Override
  public final WebhookEndpointListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableWebhookEndpointListResponse.class);
  }

  @Override
  public final WebhookEndpointResponse fetch(final String endpointToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), endpointToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableWebhookEndpointResponse.class);
  }

  @Override
  public final DeletionResult delete(final String endpointToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), endpointToken);
    final HttpResponse httpResponse = httpTransportForDelete().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getDeletionResult(httpResponse);
  }

  @Override
  protected final String getApiName() {
    return "webhook_endpoints";
  }
}
