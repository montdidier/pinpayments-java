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
import io.practiceinsight.pinpayments.api.RecipientsApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableRecipientListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableRecipientResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableTransferListResponse;
import io.practiceinsight.pinpayments.pojo.Recipient;
import io.practiceinsight.pinpayments.pojo.RecipientListResponse;
import io.practiceinsight.pinpayments.pojo.RecipientResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;

/**
 * Implementation of {@link RecipientsApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class RecipientsApiImpl extends AbstractBaseApi implements RecipientsApi {

  private static final Logger log = LoggerFactory.getLogger(RecipientsApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;
  @Inject
  private Gson gson;

  @Override
  public final RecipientResponse create(final Recipient recipient) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(recipient);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableRecipientResponse.class);
  }

  @Override
  public final RecipientListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableRecipientListResponse.class);
  }

  @Override
  public final RecipientResponse fetch(final String recipientToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), recipientToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableRecipientResponse.class);
  }

  @Override
  public final RecipientResponse update(
      final String recipientToken, final Recipient recipient) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(recipient);
    final String url = String.format("%s/%s", getApiUrl(), recipientToken);
    final HttpResponse httpResponse = httpTransportForPut()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableRecipientResponse.class);
  }

  @Override
  public final TransferListResponse listTransfers(
      final String recipientToken, final int page) throws IOException {
    final String url = String.format("%s/%s/transfers", getApiUrl(), recipientToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableTransferListResponse.class);
  }

  @Override
  protected final String getApiName() {
    return "recipients";
  }
}
