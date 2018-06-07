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
import io.practiceinsight.pinpayments.api.impl.ChargesApiImpl;
import io.practiceinsight.pinpayments.pojo.Charge;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.ChargeResponse;
import io.practiceinsight.pinpayments.pojo.SearchCriteria;

/**
 * The charges API allows you to create new credit card charges, and to retrieve details
 * of previous charges.
 *
 * @see <a href="https://pinpayments.com/docs/api/charges">Charges API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(ChargesApiImpl.class)
public interface ChargesApi {

  /**
   * Creates a new charge and returns its details. This may be a
   * <a href="https://pinpayments.com/docs/api#long-running-requests">long-running request</a>.
   *
   * @param charge The charge specified.
   * @return The details of the new charge created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeResponse create(Charge charge) throws IOException;

  /**
   * Captures a previously authorised charge and returns its details. Currently, you
   * can only capture the full amount that was originally authorised.
   *
   * @param chargeToken The charge token specified.
   * @return The details of the charge captured.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeResponse capture(String chargeToken) throws IOException;

  /**
   * Returns a paginated list of all charges.
   *
   * @param page The page number specified.
   * @return A paginated list of all charges.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeListResponse list(int page) throws IOException;

  /**
   * Returns a paginated list of charges matching the search criteria.
   *
   * @param searchCriteria The search criteria specified.
   * @return A paginated list of charges matching the search criteria.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeListResponse search(SearchCriteria searchCriteria) throws IOException;

  /**
   * Returns the details of a charge.
   *
   * @param chargeToken The charge token specified.
   * @return The details of the charge fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeResponse fetch(String chargeToken) throws IOException;

}
