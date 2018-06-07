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

package io.practiceinsight.pinpayments.api;

import com.google.inject.ImplementedBy;
import java.io.IOException;
import io.practiceinsight.pinpayments.pojo.BankAccount;
import io.practiceinsight.pinpayments.pojo.BankAccountResponse;
import io.practiceinsight.pinpayments.api.impl.BankAccountsApiImpl;

/**
 * The bank account API allows you to securely store bank account details in exchange
 * for a bank account token. This token can then be used to create a recipient using the
 * <a href="https://pinpayments.com/docs/api/recipients">recipients API</a>.
 *
 * <p>A bank account token can only be used once to create a recipient. The token automatically
 * expires after 1 month if it hasn't been used.</p>
 *
 * @see <a href="https://pinpayments.com/docs/api/bank-accounts">Bank Accounts API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(BankAccountsApiImpl.class)
public interface BankAccountsApi {

  /**
   * Creates a bank account token and returns its details.
   *
   * @param bankAccount The bank account specified.
   * @return The details of the bank account created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  BankAccountResponse create(BankAccount bankAccount) throws IOException;
}
