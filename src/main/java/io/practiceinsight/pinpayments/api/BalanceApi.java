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
import io.practiceinsight.pinpayments.pojo.BalanceResponse;
import io.practiceinsight.pinpayments.api.impl.BalanceApiImpl;

/**
 * The balance API allows you to see the current balance of funds in your Pin Payments
 * account. You can use this to confirm whether a
 * <a href="https://pinpayments.com/docs/api/transfers">transfer</a> is possible.
 *
 * @see <a href="https://pinpayments.com/docs/api/balance">Balance API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(BalanceApiImpl.class)
public interface BalanceApi {

  /**
   * Returns the current balance of your Pin Payments account. Transfers can only be made
   * using the funds in the available balance. The pending amount will become available
   * after the 7 day settlement schedule on your charges.
   *
   * @return The current balance of your Pin Payments account.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  BalanceResponse fetch() throws IOException;
}
