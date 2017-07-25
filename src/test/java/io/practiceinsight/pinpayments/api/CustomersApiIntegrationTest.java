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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.practiceinsight.pinpayments.TestCard;
import io.practiceinsight.pinpayments.TestDataFactory;
import io.practiceinsight.pinpayments.pojo.Card;
import io.practiceinsight.pinpayments.pojo.CardListResponse;
import io.practiceinsight.pinpayments.pojo.CardResponse;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.CustomerListResponse;
import io.practiceinsight.pinpayments.pojo.CustomerResponse;
import io.practiceinsight.pinpayments.pojo.DeletionResult;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link CustomersApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class CustomersApiIntegrationTest extends AbstractBaseTest {

  private CustomersApi customersApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    customersApi = inject(CustomersApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    assertThat(customerResponse.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final CustomerListResponse list = customersApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final CustomerResponse fetchedResponse = customersApi.fetch(customerToken);

    // Then
    assertThat(fetchedResponse.response().token()).isEqualTo(customerToken);
  }

  @Test(groups = "integration")
  public final void testUpdate() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final CustomerResponse updated = customersApi
        .update(customerToken, dataFactory.buildCustomer());

    // Then
    assertThat(updated.response().token()).isEqualTo(customerToken);
  }

  @Test(groups = "integration")
  public final void testDelete() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final DeletionResult deletionResult = customersApi.delete(customerToken);

    // Then
    assertThat(deletionResult.success()).isTrue();
  }

  @Test(groups = "integration")
  public final void testListCharges() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final ChargeListResponse response = customersApi.listCharges(customerToken, 1);

    // Then
    assertThat(response.response()).isEmpty();
  }

  @Test(groups = "integration")
  public final void testListCards() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final CardListResponse response = customersApi.listCards(customerToken, 1);

    // Then
    assertThat(response.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testCreateCard() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();

    // When
    final Card card = dataFactory.buildCard(TestCard.SuccessVisa);
    final CardResponse cardResponse = customersApi.createCard(customerToken, card);

    // Then
    assertThat(cardResponse).isNotNull();
  }

  @Test(groups = "integration")
  public final void testDeleteCard() throws Exception {
    // Given
    final CustomerResponse customerResponse = customersApi.create(dataFactory.buildCustomer());
    final String customerToken = customerResponse.response().token();
    final Card card = dataFactory.buildCard(TestCard.SuccessVisa);
    final CardResponse cardResponse = customersApi.createCard(customerToken, card);
    final String cardToken = cardResponse.response().token();

    // When
    final DeletionResult result = customersApi.deleteCard(customerToken, cardToken);

    // Then
    assertThat(result.success()).isTrue();
  }

}
