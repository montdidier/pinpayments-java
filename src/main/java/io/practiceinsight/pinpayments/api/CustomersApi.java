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
import io.practiceinsight.pinpayments.api.impl.CustomersApiImpl;
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.CardListResponse;
import io.practiceinsight.pinpayments.pojo.CardResponse;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.Customer;
import io.practiceinsight.pinpayments.pojo.CustomerListResponse;
import io.practiceinsight.pinpayments.pojo.CustomerResponse;
import io.practiceinsight.pinpayments.pojo.DeletionResult;

/**
 * The customers API allows you to store a customer’s email and credit card details.
 * A customer can then be used with the charges API to create multiple charges over time.
 *
 * <p>Customers can have multiple cards associated with them, and one will be considered
 * the customer’s primary card. The card object in returned customer information represents
 * this primary card. It contains a member called primary, which says whether the card is
 * a customer’s primary card; its value will always be true.</p>
 *
 * @see <a href="https://pinpayments.com/docs/api/customers">Customers API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(CustomersApiImpl.class)
public interface CustomersApi {

  /**
   * Creates a new customer and returns its details.
   *
   * @param customer The new customer specified.
   * @return The details of the new customer specified.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CustomerResponse create(Customer customer) throws IOException;

  /**
   * Returns a paginated list of all customers.
   *
   * @param page The page number specified.
   * @return A paginated list of all customers.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CustomerListResponse list(int page) throws IOException;

  /**
   * Returns the details of a customer.
   *
   * @param customerToken The customer token specified.
   * @return The details of the customer fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CustomerResponse fetch(String customerToken) throws IOException;

  /**
   * Updates the given details of a customer and returns its details. You can update the
   * customer’s cards in one of four ways:
   *
   * <ul>
   *   <li>You can use the card[...] parameters to create a new card that will replace
   *   the customer’s primary card. The customer’s current primary card will be deleted;
   *   you will not be able to recover it.</li>
   *   <li>You can use the card_token parameter to replace the customer’s primary card
   *   with a card already belonging to the customer or an already-existing unused card
   *   (such as one created through the card tokens API). The customer’s current primary
   *   card will be deleted; you will not be able to recover it.</li>
   *   <li>You can use the primary_card_token parameter to switch the customer’s primary
   *   card to a card already belonging to the customer or to an existing unused card
   *   (such as one created through the card tokens API). The current primary card will
   *   become a non-primary card of the customer.</li>
   *   <li>You can use none of the above parameters. The customer’s cards will not change.</li>
   * </ul>
   *
   * <p>In addition, you can update the customer’s email address.</p>
   *
   * @param customerToken The customer token specified.
   * @param customer The new customer specified.
   * @return The details of the customer updated.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CustomerResponse update(String customerToken, Customer customer) throws IOException;

  /**
   * Deletes a customer and all of its cards. You will not be able to recover them.
   *
   * @param customerToken The customer token specified.
   * @return The deletion result.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  DeletionResult delete(String customerToken) throws IOException;

  /**
   * Returns a paginated list of a customer’s charges.
   *
   * @param customerToken The customer token specified.
   * @param page The page number specified.
   * @return A paginated list of a customer’s charges.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  ChargeListResponse listCharges(String customerToken, int page) throws IOException;

  /**
   * Returns a paginated list of a customer’s cards.
   *
   * @param customerToken The customer token specified.
   * @param page The page number specified.
   * @return A paginated list of a customer’s cards.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CardListResponse listCards(final String customerToken, int page) throws IOException;

  /**
   * Creates an additional card for the specified customer and returns its details.
   * The customer’s primary card will not be changed by this operation.
   *
   * @param customerToken The customer token specified.
   * @param card The card specified.
   * @return The details of the card specified.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CardResponse createCard(String customerToken, Card card) throws IOException;

  /**
   * Deletes a customer’s non-primary card. You will not be able to recover it.
   *
   * @param customerToken The customer token specified.
   * @param cardToken The card token specified.
   * @return The deletion result.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  DeletionResult deleteCard(String customerToken, String cardToken) throws IOException;
}
