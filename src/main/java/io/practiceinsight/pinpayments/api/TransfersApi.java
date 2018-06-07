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
import io.practiceinsight.pinpayments.api.impl.TransfersApiImpl;
import io.practiceinsight.pinpayments.pojo.SearchCriteria;
import io.practiceinsight.pinpayments.pojo.Transfer;
import io.practiceinsight.pinpayments.pojo.TransferLineItemListResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;
import io.practiceinsight.pinpayments.pojo.TransferResponse;

/**
 * The transfers API allows you to send money to Australian bank accounts, and to
 * retrieve details of previous transfers.
 *
 * @see <a href="https://pinpayments.com/docs/api/transfers">Transfers API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(TransfersApiImpl.class)
public interface TransfersApi {

  /**
   * Creates a new transfer and returns its details.
   *
   * @param transfer The transfer specified.
   * @return The details of the transfer created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferResponse create(Transfer transfer) throws IOException;

  /**
   * Returns a paginated list of all transfers.
   *
   * @param page The page number specified.
   * @return A paginated list of all transfers.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferListResponse list(int page) throws IOException;

  /**
   * Returns a paginated list of transfers matching the search criteria.
   *
   * @param searchCriteria The search criteria specified.
   * @return A paginated list of transfers matching the search criteria.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferListResponse search(SearchCriteria searchCriteria) throws IOException;

  /**
   * Returns the details of a transfer.
   *
   * @param transferToken The transfer token specified.
   * @return The details of the transfer fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferResponse fetch(String transferToken) throws IOException;

  /**
   * Returns the line items associated with transfer.
   *
   * @param transferToken The transfer token specified.
   * @param page The page number specified.
   * @return The line items associated with transfer.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferLineItemListResponse listLineItems(final String transferToken, int page)
      throws IOException;
}
