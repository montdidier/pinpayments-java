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
import io.practiceinsight.pinpayments.pojo.CardResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link CardTokensApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class CardTokensApiIntegrationTest extends AbstractBaseTest {

  private CardTokensApi cardTokensApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    cardTokensApi = inject(CardTokensApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final Card card = dataFactory.buildCard(TestCard.SuccessVisa);
    final CardResponse cardResponse = cardTokensApi.create(card);
    assertThat(cardResponse.response().token()).isNotBlank();
  }

}
