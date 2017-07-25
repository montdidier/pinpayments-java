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
import io.practiceinsight.pinpayments.pojo.Charge;
import io.practiceinsight.pinpayments.pojo.ChargeListResponse;
import io.practiceinsight.pinpayments.pojo.ChargeResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableSearchCriteria;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ChargesApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class ChargesApiIntegrationTest extends AbstractBaseTest {

  private ChargesApi chargesApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    chargesApi = inject(ChargesApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessMasterCard);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    assertThat(chargeResponse.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testCreate_withInvalidCard() throws Exception {
    final Charge charge = dataFactory.buildCharge(TestCard.InvalidCvvMasterCard);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    assertThat(chargeResponse.errorResponse()).isNotNull();
    assertThat(chargeResponse.errorResponse().error()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testCapture() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessMasterCard, false);
    final ChargeResponse chargeResponse = chargesApi.create(charge);

    // When
    final ChargeResponse capturedResponse = chargesApi.capture(chargeResponse.response().token());

    // Then
    assertThat(capturedResponse.response().captured()).isTrue();
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final ChargeListResponse list = chargesApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testSearch() throws Exception {
    // Given
    final ImmutableSearchCriteria criteria = ImmutableSearchCriteria.builder()
        .query("test").build();

    // When
    final ChargeListResponse result = chargesApi.search(criteria);

    // Then
    assertThat(result.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessMasterCard);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    final String chargeToken = chargeResponse.response().token();

    // When
    final ChargeResponse fetchedResponse = chargesApi.fetch(chargeToken);

    // Then
    assertThat(fetchedResponse.response().token()).isEqualTo(chargeToken);
  }

}
