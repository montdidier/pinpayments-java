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
import io.practiceinsight.pinpayments.api.TransfersApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableTransferLineItemListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableTransferListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableTransferResponse;
import io.practiceinsight.pinpayments.pojo.SearchCriteria;
import io.practiceinsight.pinpayments.pojo.Transfer;
import io.practiceinsight.pinpayments.pojo.TransferLineItemListResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;
import io.practiceinsight.pinpayments.pojo.TransferResponse;

/**
 * Implementation of {@link TransfersApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class TransfersApiImpl extends AbstractBaseApi implements TransfersApi {

  private static final Logger log = LoggerFactory.getLogger(TransfersApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;
  @Inject
  private Gson gson;

  @Override
  public final TransferResponse create(final Transfer transfer) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(transfer);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableTransferResponse.class);
  }

  @Override
  public final TransferListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableTransferListResponse.class);
  }

  @Override
  public final TransferListResponse search(final SearchCriteria searchCriteria) throws IOException {
    return executeSearch(getApiUrl(), searchCriteria, ImmutableTransferListResponse.class);
  }

  @Override
  public final TransferResponse fetch(final String transferToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), transferToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableTransferResponse.class);
  }

  @Override
  public final TransferLineItemListResponse listLineItems(
      final String transferToken, final int page) throws IOException {
    final String url = String.format("%s/%s/line_items", getApiUrl(), transferToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableTransferLineItemListResponse.class);
  }

  @Override
  protected final String getApiName() {
    return "transfers";
  }
}
