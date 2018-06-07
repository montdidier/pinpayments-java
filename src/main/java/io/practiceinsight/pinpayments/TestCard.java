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

/**
 * Test cards provided by PinPayments. For more information please see:
 * <a href="https://pinpayments.com/docs/api/test-cards">https://pinpayments.com/docs/api/test-cards</a>
 *
 * @author delight.wjk@gmail.com
 */
public enum TestCard {
  SuccessVisa("4200000000000000"),
  SuccessMasterCard("5520000000000000"),
  CardDeclinedVisa("4100000000000001"),
  CardDeclinedMasterCard("5560000000000001"),
  InsufficientFundsVisa("4000000000000002"),
  InsufficientFundsMasterCard("5510000000000002"),
  InvalidCvvVisa("4900000000000003"),
  InvalidCvvMasterCard("5550000000000003"),
  InvalidCardVisa("4800000000000004"),
  InvalidCardMasterCard("5500000000000004"),
  ProcessingErrorVisa("4700000000000005"),
  ProcessingErrorMasterCard("5590000000000005"),
  SuspectedFraudVisa("4600000000000006"),
  SuspectedFraudMasterCard("5540000000000006"),
  UnknownVisa("4400000000000099"),
  UnknownMasterCard("5530000000000099");

  private String cardNumber;

  TestCard(final String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public final String getCardNumber() {
    return cardNumber;
  }
}
