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

package io.practiceinsight.pinpayments;

import com.google.common.collect.ImmutableMap;

import io.practiceinsight.pinpayments.pojo.BankAccount;
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.Charge;
import io.practiceinsight.pinpayments.pojo.Customer;
import io.practiceinsight.pinpayments.pojo.ImmutableBankAccount;
import io.practiceinsight.pinpayments.pojo.ImmutableCard;
import io.practiceinsight.pinpayments.pojo.ImmutableCharge;
import io.practiceinsight.pinpayments.pojo.ImmutableCustomer;
import io.practiceinsight.pinpayments.pojo.ImmutableRecipient;
import io.practiceinsight.pinpayments.pojo.ImmutableRefund;
import io.practiceinsight.pinpayments.pojo.ImmutableTransfer;
import io.practiceinsight.pinpayments.pojo.ImmutableWebhookEndpoint;
import io.practiceinsight.pinpayments.pojo.Recipient;
import io.practiceinsight.pinpayments.pojo.Refund;
import io.practiceinsight.pinpayments.pojo.Transfer;
import io.practiceinsight.pinpayments.pojo.WebhookEndpoint;

/**
 * @author delight.wjk@gmail.com
 */
public class TestDataFactory {

  public final Charge buildCharge(final TestCard testCard) {
    return buildCharge(testCard, true);
  }

  /**
   * Builds a {@link Charge} object for tests.
   *
   * @param capture Specifies whether we capture this charge or not.
   * @return The a {@link Charge} object built.
   */
  public final Charge buildCharge(final TestCard testCard, final boolean capture) {
    final int amount = 400;
    return ImmutableCharge.builder()
        .email("roland@pinpayments.com")
        .currency("AUD")
        .description("test charge")
        .amount(amount)
        .ipAddress("203.192.1.172")
        .capture(String.valueOf(capture))
        .card(buildCard(testCard))
        .metadata(ImmutableMap.of("OrderNumber", "123456", "CustomerName", "Roland Robot"))
        .build();
  }

  /**
   * Builds a {@link Card} object for tests.
   *
   * @return The a {@link Card} object built.
   */
  public final Card buildCard(final TestCard testCard) {
    return ImmutableCard.builder()
        .number(testCard.getCardNumber())
        .expiryMonth("05")
        .expiryYear("2018")
        .cvc("123")
        .name("Roland Robot")
        .addressLine1("42 Sevenoaks St")
        .addressLine2("")
        .addressCity("Lathlain")
        .addressPostcode("6454")
        .addressState("WA")
        .addressCountry("Australia")
        .build();
  }

  public final Customer buildCustomer() {
    return ImmutableCustomer.builder().email("roland@pinpayments.com")
        .card(buildCard(TestCard.SuccessMasterCard)).build();
  }

  public final Refund buildRefund() {
    return ImmutableRefund.builder().build();
  }

  public final Recipient buildRecipient() {
    return ImmutableRecipient.builder()
        .email("roland@pinpayments.com")
        .name("Mr Roland Robot")
        .bankAccount(buildBankAccount()).build();
  }

  public final BankAccount buildBankAccount() {
    return ImmutableBankAccount.builder()
        .name("Mr Roland Robot")
        .bsb("123456")
        .number("987654321").build();
  }

  public final Transfer buildTransfer(final String recipient) {
    final int amount = 400;
    return ImmutableTransfer.builder()
        .amount(amount)
        .currency("AUD")
        .description("Earnings for may")
        .recipient(recipient).build();
  }

  public final WebhookEndpoint buildWebhookEndpoint() {
    return ImmutableWebhookEndpoint.builder()
        .url("https://example.org/webhooks/" + System.currentTimeMillis()).build();
  }
}
