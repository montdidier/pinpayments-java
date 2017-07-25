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
import io.practiceinsight.pinpayments.api.ChargesApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.http.HttpStatus;
import io.practiceinsight.pinpayments.pojo.Charge;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.ChargeResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableChargeListResponse;
import io.practiceinsight.pinpayments.pojo.SearchCriteria;

/**
 * Implementation of {@link ChargesApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class ChargesApiImpl extends AbstractBaseApi implements ChargesApi {

  private static final Logger log = LoggerFactory.getLogger(ChargesApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;

  @Override
  public final ChargeResponse create(final Charge charge) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(charge);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getChargeResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  public final ChargeResponse capture(final String chargeToken) throws IOException {
    final String url = String.format("%s/%s/capture", getApiUrl(), chargeToken);
    final HttpResponse httpResponse = httpTransportForPut().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getChargeResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  public final ChargeListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableChargeListResponse.class);
  }

  @Override
  public final ChargeListResponse search(final SearchCriteria searchCriteria) throws IOException {
    return executeSearch(getApiUrl(), searchCriteria, ImmutableChargeListResponse.class);
  }

  @Override
  public final ChargeResponse fetch(final String chargeToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), chargeToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getChargeResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  protected final String getApiName() {
    return "charges";
  }
}
