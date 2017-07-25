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
import io.practiceinsight.pinpayments.api.RefundsApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.http.HttpStatus;
import io.practiceinsight.pinpayments.pojo.ImmutableRefundListResponse;
import io.practiceinsight.pinpayments.pojo.Refund;
import io.practiceinsight.pinpayments.pojo.RefundListResponse;
import io.practiceinsight.pinpayments.pojo.RefundResponse;

/**
 * Implementation of {@link RefundsApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class RefundsApiImpl extends AbstractBaseApi implements RefundsApi {

  private static final Logger log = LoggerFactory.getLogger(RefundsApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;

  @Override
  public final RefundListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableRefundListResponse.class);
  }

  @Override
  public final RefundResponse fetch(final String refundToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), refundToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getRefundResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  public final RefundResponse create(
      final String chargeToken, final Refund refund) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(refund);
    final String url = String.format("%s/charges/%s/refunds", getApiBase(), chargeToken);
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getRefundResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  public final RefundListResponse listForCharge(
      final String chargeToken, final int page) throws IOException {
    final String url = String.format("%s/charges/%s/refunds", getApiBase(), chargeToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return getRefundListResponse(HttpStatus.OK, httpResponse);
  }

  @Override
  protected final String getApiName() {
    return "refunds";
  }
}
