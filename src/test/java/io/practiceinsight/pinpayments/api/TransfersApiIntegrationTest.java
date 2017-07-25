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
import io.practiceinsight.pinpayments.pojo.ImmutableSearchCriteria;
import io.practiceinsight.pinpayments.pojo.RecipientResponse;
import io.practiceinsight.pinpayments.pojo.TransferLineItemListResponse;
import io.practiceinsight.pinpayments.pojo.TransferListResponse;
import io.practiceinsight.pinpayments.pojo.TransferResponse;

/**
 * Integration tests for {@link TransfersApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class TransfersApiIntegrationTest extends AbstractBaseTest {

  private RecipientsApi recipientsApi;
  private TransfersApi transfersApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    recipientsApi = inject(RecipientsApi.class);
    transfersApi = inject(TransfersApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final RecipientResponse recipient = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = recipient.response().token();
    final TransferResponse transfer = transfersApi.create(
        dataFactory.buildTransfer(recipientToken));
    assertThat(transfer.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final TransferListResponse list = transfersApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testSearch() throws Exception {
    // Given
    final ImmutableSearchCriteria criteria = ImmutableSearchCriteria.builder()
        .query("test").build();

    // When
    final TransferListResponse result = transfersApi.search(criteria);

    // Then
    assertThat(result.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final RecipientResponse recipient = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = recipient.response().token();
    final TransferResponse transfer = transfersApi.create(
        dataFactory.buildTransfer(recipientToken));
    final String transferToken = transfer.response().token();

    // When
    final TransferResponse fetchedResponse = transfersApi.fetch(transferToken);

    // Then
    assertThat(fetchedResponse.response().token()).isEqualTo(transferToken);
  }

  @Test(groups = "integration")
  public final void testListLineItems() throws Exception {
    // Given
    final RecipientResponse recipient = recipientsApi.create(dataFactory.buildRecipient());
    final String recipientToken = recipient.response().token();
    final TransferResponse transfer = transfersApi.create(
        dataFactory.buildTransfer(recipientToken));
    final String transferToken = transfer.response().token();

    // When
    final TransferLineItemListResponse fetchedResponse = transfersApi
        .listLineItems(transferToken, 1);

    // Then
    assertThat(fetchedResponse.response()).isNotEmpty();
  }

}
