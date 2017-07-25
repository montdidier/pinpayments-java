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
import io.practiceinsight.pinpayments.api.CardTokensApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.http.HttpStatus;
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.CardResponse;

/**
 * Implementation of {@link CardTokensApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class CardTokensApiImpl extends AbstractBaseApi implements CardTokensApi {

  private static final Logger log = LoggerFactory.getLogger(CardTokensApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;

  @Override
  public final CardResponse create(final Card card) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(card);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return getCardResponse(HttpStatus.CREATED, httpResponse);
  }

  @Override
  protected final String getApiName() {
    return "cards";
  }
}
