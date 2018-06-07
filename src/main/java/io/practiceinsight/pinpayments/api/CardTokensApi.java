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
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.CardResponse;
import io.practiceinsight.pinpayments.api.impl.CardTokensApiImpl;

/**
 * The card tokens API allows you to securely store credit card details in exchange
 * for a card token. This card token can then be used to create a single charge with
 * the charges API, or to create multiple charges over time using the customers API.
 *
 * <p>Returned card information contains a member called primary, which says whether
 * the card is a customer’s primary card. Its value is true if the card is a customer’s
 * primary card, false if it is a non-primary card of the customer, and null if it is
 * not associated with a customer.
 *
 * <p>A card token can only be used once, to create either a charge or a customer. If
 * no charge or customer is created within 1 month, the token is automatically expired.
 *
 * @see <a href="https://pinpayments.com/docs/api/cards">https://pinpayments.com/docs/api/cards</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(CardTokensApiImpl.class)
public interface CardTokensApi {

  /**
   * Creates a card token and returns its details.
   *
   * @param card The card specified.
   * @return The details of the card created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  CardResponse create(Card card) throws IOException;
}
