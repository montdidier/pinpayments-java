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
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.practiceinsight.pinpayments.ParamsHelper;
import io.practiceinsight.pinpayments.api.BankAccountsApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.BankAccount;
import io.practiceinsight.pinpayments.pojo.BankAccountResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableBankAccountResponse;

/**
 * Implementation of {@link BankAccountsApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class BankAccountsApiImpl extends AbstractBaseApi implements BankAccountsApi {

  private static final Logger log = LoggerFactory.getLogger(BankAccountsApiImpl.class); // NOPMD

  @Inject
  private ParamsHelper paramsHelper;
  @Inject
  private Gson gson;

  @Override
  public final BankAccountResponse create(final BankAccount bankAccount) throws IOException {
    final Map<String, String> flatParams = paramsHelper.toFlatParams(bankAccount);
    final String url = getApiUrl();
    final HttpResponse httpResponse = httpTransportForPost()
        .setUrl(url)
        .addParams(flatParams)
        .execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableBankAccountResponse.class);
  }

  @Override
  protected final String getApiName() {
    return "bank_accounts";
  }
}
