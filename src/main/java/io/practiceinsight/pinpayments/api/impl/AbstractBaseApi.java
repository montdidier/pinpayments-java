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

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.practiceinsight.pinpayments.ParamsHelper;
import io.practiceinsight.pinpayments.annotation.PinBaseUrl;
import io.practiceinsight.pinpayments.annotation.PinSecretKey;
import io.practiceinsight.pinpayments.annotation.PinVersion;
import io.practiceinsight.pinpayments.http.HttpMethod;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.http.HttpStatus;
import io.practiceinsight.pinpayments.http.HttpTransport;
import io.practiceinsight.pinpayments.pojo.CardListResponse;
import io.practiceinsight.pinpayments.pojo.CardResponse;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.ChargeResponse;
import io.practiceinsight.pinpayments.pojo.CustomerResponse;
import io.practiceinsight.pinpayments.pojo.DeletionResult;
import io.practiceinsight.pinpayments.pojo.ErrorResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableCardListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableCardResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableChargeListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableChargeResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableCustomerResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableDeletionResult;
import io.practiceinsight.pinpayments.pojo.ImmutableErrorResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableRefundListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableRefundResponse;
import io.practiceinsight.pinpayments.pojo.RefundListResponse;
import io.practiceinsight.pinpayments.pojo.RefundResponse;
import io.practiceinsight.pinpayments.pojo.SearchCriteria;

/**
 * @author delight.wjk@gmail.com
 */
public abstract class AbstractBaseApi {

  private static final Logger log = LoggerFactory.getLogger(AbstractBaseApi.class); // NOPMD

  @Inject
  @PinBaseUrl
  private String pinBaseUrl;
  @Inject
  @PinVersion
  private String pinVersion;
  @Inject
  @PinSecretKey
  private String pinSecretKey;
  @Inject
  private Gson gson;
  @Inject
  private ParamsHelper paramsHelper;

  protected final String getApiUrl() {
    return String.format("%s/%s", getApiBase(), getApiName());
  }

  protected final String getApiBase() {
    return String.format("%s/%s", pinBaseUrl, pinVersion);
  }

  protected abstract String getApiName();

  protected final HttpTransport httpTransportForPost() {
    return httpTransport()
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .setHttpMethod(HttpMethod.POST);
  }

  protected final HttpTransport httpTransportForPut() {
    return httpTransport()
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .setHttpMethod(HttpMethod.PUT);
  }

  protected final HttpTransport httpTransportForGet() {
    return httpTransport().setHttpMethod(HttpMethod.GET);
  }

  protected final HttpTransport httpTransportForDelete() {
    return httpTransport().setHttpMethod(HttpMethod.DELETE);
  }

  private HttpTransport httpTransport() {
    return new HttpTransport()
        .addHeader("Authorization", String.format("Basic %s:", getEncodedSecretKey()));
  }

  private String getEncodedSecretKey() {
    return BaseEncoding.base64().encode(pinSecretKey.getBytes(Charsets.UTF_8));
  }

  protected final DeletionResult getDeletionResult(final HttpResponse httpResponse) {
    final DeletionResult result;
    if (httpResponse.getStatusCode() == HttpStatus.NO_CONTENT.getCode()) {
      result = ImmutableDeletionResult.builder().success(true).build();
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableDeletionResult.builder().success(false)
          .errorResponse(errorResponse)
          .build();
    }
    return result;
  }

  protected final <T> T executeList(final String url, final Class<? extends T> clazz)
      throws IOException {
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), clazz);
  }

  protected final <T> T executeSearch(final String url, final SearchCriteria searchCriteria,
                                      final Class<? extends T> clazz)
      throws IOException {
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url)
        .addParams(paramsHelper.toFlatParams(searchCriteria)).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), clazz);
  }

  protected final CustomerResponse getCustomerResponse(
      final HttpStatus expectedStatus, final HttpResponse httpResponse) {
    final CustomerResponse result;
    if (httpResponse.getStatusCode() == expectedStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableCustomerResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableCustomerResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final CardResponse getCardResponse(
      final HttpStatus httpStatus, final HttpResponse httpResponse) {
    final CardResponse result;
    if (httpResponse.getStatusCode() == httpStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableCardResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableCardResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final CardListResponse getCardListResponse(
      final HttpStatus httpStatus, final HttpResponse httpResponse) {
    final CardListResponse result;
    if (httpResponse.getStatusCode() == httpStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableCardListResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableCardListResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final ChargeResponse getChargeResponse(
      final HttpStatus expectedStatus, final HttpResponse httpResponse) {
    final ChargeResponse result;
    if (httpResponse.getStatusCode() == expectedStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableChargeResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableChargeResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final ChargeListResponse getChargeListResponse(final HttpResponse httpResponse) {
    final ChargeListResponse result;
    if (httpResponse.getStatusCode() == HttpStatus.OK.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableChargeListResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableChargeListResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final RefundResponse getRefundResponse(
      final HttpStatus expectedStatus, final HttpResponse httpResponse) {
    final RefundResponse result;
    if (httpResponse.getStatusCode() == expectedStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableRefundResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableRefundResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }

  protected final RefundListResponse getRefundListResponse(
      final HttpStatus expectedStatus, final HttpResponse httpResponse) {
    final RefundListResponse result;
    if (httpResponse.getStatusCode() == expectedStatus.getCode()) {
      result = gson.fromJson(httpResponse.getContent(), ImmutableRefundListResponse.class);
    } else {
      final ErrorResponse errorResponse = gson.fromJson(
          httpResponse.getError(), ImmutableErrorResponse.class);
      result = ImmutableRefundListResponse.builder().errorResponse(errorResponse).build();
    }
    return result;
  }
}
