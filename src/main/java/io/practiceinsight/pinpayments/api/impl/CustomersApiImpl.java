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

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import io.practiceinsight.pinpayments.ParamsHelper;
import io.practiceinsight.pinpayments.api.CustomersApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.http.HttpStatus;
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.CardListResponse;
import io.practiceinsight.pinpayments.pojo.CardResponse;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.Customer;
import io.practiceinsight.pinpayments.pojo.CustomerListResponse;
import io.practiceinsight.pinpayments.pojo.CustomerResponse;
import io.practiceinsight.pinpayments.pojo.DeletionResult;
import io.practiceinsight.pinpayments.pojo.ImmutableCustomerListResponse;

/**
 * Implementation of {@link CustomersApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class CustomersApiImpl extends AbstractBaseApi implements CustomersApi {

  private static final Logger log = LoggerFactory.getLogger(CustomersApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;

  @Override
  public final CustomerResponse create(final Customer customer) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(customer);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getCustomerResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  public final CustomerListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableCustomerListResponse.class);
  }

  @Override
  public final CustomerResponse fetch(final String customerToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getCustomerResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  public final CustomerResponse update(
      final String customerToken, final Customer customer) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(customer);
    final String url = String.format("%s/%s", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForPut()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getCustomerResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  public final DeletionResult delete(final String customerToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForDelete().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getDeletionResult(httpResponse);
  }

  @Override
  public final ChargeListResponse listCharges(
      final String customerToken, final int page) throws IOException {
    final String url = String.format("%s/%s/charges", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getChargeListResponse(httpResponse);
  }

  @Override
  public final CardListResponse listCards(
      final String customerToken, final int page) throws IOException {
    final String url = String.format("%s/%s/cards", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getCardListResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  public final CardResponse createCard(
      final String customerToken, final Card card) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(card);
    final String url = String.format("%s/%s/cards", getApiUrl(), customerToken);
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getCardResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  public final DeletionResult deleteCard(
      final String customerToken, final String cardToken) throws IOException {
    final String url = String.format("%s/%s/cards/%s", getApiUrl(), customerToken, cardToken);
    final HttpResponse httpResponse = httpTransportForDelete().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getDeletionResult(httpResponse);
  }

  @Override
  protected final String getApiName() {
    return "customers";
  }
}
