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
import io.practiceinsight.pinpayments.pojo.ChargeResponse;
import io.practiceinsight.pinpayments.pojo.RefundListResponse;
import io.practiceinsight.pinpayments.pojo.RefundResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link RefundsApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class RefundsApiIntegrationTest extends AbstractBaseTest {

  private ChargesApi chargesApi;
  private RefundsApi refundsApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    chargesApi = inject(ChargesApi.class);
    refundsApi = inject(RefundsApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final RefundListResponse list = refundsApi.list(1);
    assertThat(list).isNotNull();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessVisa);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    final String chargeToken = chargeResponse.response().token();
    final RefundResponse refundResponse = refundsApi.create(chargeToken, dataFactory.buildRefund());
    final String refundToken = refundResponse.response().token();

    // When
    final RefundResponse fetched = refundsApi.fetch(refundToken);

    // Then
    assertThat(fetched).isNotNull();
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessVisa);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    final String chargeToken = chargeResponse.response().token();

    // When
    final RefundResponse refundResponse = refundsApi.create(chargeToken, dataFactory.buildRefund());
    assertThat(refundResponse.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testListForCharge() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessVisa);
    final ChargeResponse chargeResponse = chargesApi.create(charge);
    final String chargeToken = chargeResponse.response().token();
    refundsApi.create(chargeToken, dataFactory.buildRefund());

    // When
    final RefundListResponse listForCharge = refundsApi.listForCharge(chargeToken, 1);
    assertThat(listForCharge.response()).isNotEmpty();
  }

}
