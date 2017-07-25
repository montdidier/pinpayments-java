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

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.practiceinsight.pinpayments.TestDataFactory;
import io.practiceinsight.pinpayments.pojo.RecipientListResponse;
import io.practiceinsight.pinpayments.pojo.RecipientResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;

/**
 * Integration tests for {@link RecipientsApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class RecipientsApiIntegrationTest extends AbstractBaseTest {

  private RecipientsApi recipientsApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    recipientsApi = inject(RecipientsApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final RecipientResponse response = recipientsApi.create(dataFactory.buildRecipient());
    assertThat(response.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final RecipientListResponse list = recipientsApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final RecipientResponse response = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = response.response().token();

    // When
    final RecipientResponse fetched = recipientsApi.fetch(recipientToken);

    // Then
    assertThat(fetched.response().token()).isEqualTo(recipientToken);
  }

  @Test(groups = "integration")
  public final void testUpdate() throws Exception {
    // Given
    final RecipientResponse response = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = response.response().token();

    // When
    final RecipientResponse updated = recipientsApi
        .update(recipientToken, dataFactory.buildRecipient());

    // Then
    assertThat(updated.response().token()).isEqualTo(recipientToken);
  }

  @Test(groups = "integration")
  public final void testListTransfers() throws Exception {
    // Given
    final RecipientResponse response = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = response.response().token();

    // When
    final TransferListResponse listResponse = recipientsApi.listTransfers(recipientToken, 1);

    // Then
    assertThat(listResponse.response()).isEmpty();
  }

}
