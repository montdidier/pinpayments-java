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
import io.practiceinsight.pinpayments.api.impl.RefundsApiImpl;
import io.practiceinsight.pinpayments.pojo.Refund;
import io.practiceinsight.pinpayments.pojo.RefundListResponse;
import io.practiceinsight.pinpayments.pojo.RefundResponse;

/**
 * The refunds API allows you to refund a charge, and retrieve the details of previous refunds.
 *
 * @see <a href="https://pinpayments.com/docs/api/refunds">Refunds API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(RefundsApiImpl.class)
public interface RefundsApi {

  /**
   * Returns a paginated list of all refunds.
   *
   * @param page The page number specified.
   * @return A paginated list of all refunds.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RefundListResponse list(int page) throws IOException;

  /**
   * Returns details for a specific refund.
   *
   * @param refundToken The refund token specified.
   * @return The details of the refund fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RefundResponse fetch(String refundToken) throws IOException;

  /**
   * Creates a new refund, and returns its details.
   *
   * @param chargeToken The charge token specified.
   * @param refund The refund to be created.
   * @return The details of the refund created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RefundResponse create(String chargeToken, Refund refund) throws IOException;

  /**
   * Returns a list of all refunds for this charge.
   *
   * @param chargeToken The charge token specified.
   * @param page The page number specified.
   * @return The list of all refunds for this charge.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RefundListResponse listForCharge(String chargeToken, int page) throws IOException;
}
