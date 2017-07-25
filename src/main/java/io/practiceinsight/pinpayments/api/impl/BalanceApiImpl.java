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

import io.practiceinsight.pinpayments.api.BalanceApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.BalanceResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableBalanceResponse;

/**
 * Implementation of {@link BalanceApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class BalanceApiImpl extends AbstractBaseApi implements BalanceApi {

  private static final Logger log = LoggerFactory.getLogger(BalanceApiImpl.class); // NOPMD

  @Inject
  private Gson gson;

  @Override
  public final BalanceResponse fetch() throws IOException {
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableBalanceResponse.class);
  }

  @Override
  protected final String getApiName() {
    return "balance";
  }
}
