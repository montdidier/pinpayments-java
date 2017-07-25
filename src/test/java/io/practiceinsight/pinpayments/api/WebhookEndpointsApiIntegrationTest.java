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
import io.practiceinsight.pinpayments.pojo.DeletionResult;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointListResponse;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointResponse;

/**
 * Integration tests for {@link WebhookEndpointsApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class WebhookEndpointsApiIntegrationTest extends AbstractBaseTest {

  private WebhookEndpointsApi endpointsApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    endpointsApi = inject(WebhookEndpointsApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final WebhookEndpointResponse response = endpointsApi
        .create(dataFactory.buildWebhookEndpoint());
    assertThat(response.response().token()).isNotBlank();
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final WebhookEndpointListResponse list = endpointsApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isNotEmpty();
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    // Given
    final WebhookEndpointResponse endpointResponse = endpointsApi
        .create(dataFactory.buildWebhookEndpoint());
    final String endpointToken = endpointResponse.response().token();

    // When
    final WebhookEndpointResponse fetched = endpointsApi.fetch(endpointToken);

    // Then
    assertThat(fetched.response().token()).isEqualTo(endpointToken);
  }

  @Test(groups = "integration")
  public final void testDelete() throws Exception {
    // Given
    final WebhookEndpointResponse endpointResponse = endpointsApi
        .create(dataFactory.buildWebhookEndpoint());
    final String endpointToken = endpointResponse.response().token();

    // When
    final DeletionResult deletionResult = endpointsApi.delete(endpointToken);

    // Then
    assertThat(deletionResult.success()).isTrue();
  }

}
