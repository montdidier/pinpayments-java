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
import io.practiceinsight.pinpayments.api.impl.RecipientsApiImpl;
import io.practiceinsight.pinpayments.pojo.Recipient;
import io.practiceinsight.pinpayments.pojo.RecipientListResponse;
import io.practiceinsight.pinpayments.pojo.RecipientResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;

/**
 * The recipients API allows you to post bank account details and retrieve a token that
 * you can safely store in your app. You can send funds to recipients using
 * the <a href="https://pinpayments.com/docs/api/transfers">transfers API</a>.
 *
 * @see <a href="https://pinpayments.com/docs/api/recipients">Recipients API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(RecipientsApiImpl.class)
public interface RecipientsApi {

  /**
   * Creates a new recipient and returns its details.
   *
   * @param recipient The recipient specified.
   * @return The details of the new recipient created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RecipientResponse create(Recipient recipient) throws IOException;

  /**
   * Returns a paginated list of all recipients.
   *
   * @param page The page number specified.
   * @return A paginated list of all recipients.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RecipientListResponse list(int page) throws IOException;

  /**
   * Returns the details of a recipient.
   *
   * @param recipientToken The recipient token specified.
   * @return The details of the recipient fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RecipientResponse fetch(String recipientToken) throws IOException;

  /**
   * Updates the given details of a recipient and returns its details.
   *
   * @param recipientToken The recipient token specified.
   * @param recipient The recipient which contains new information specified.
   * @return The details of the recipient updated.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  RecipientResponse update(String recipientToken, Recipient recipient) throws IOException;

  /**
   * Returns a paginated list of a recipient's transfers.
   *
   * @param recipientToken The recipient token specified.
   * @param page The page number specified.
   * @return A paginated list of a recipient's transfers.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  TransferListResponse listTransfers(final String recipientToken, int page) throws IOException;

}
