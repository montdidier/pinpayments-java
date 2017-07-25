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
import io.practiceinsight.pinpayments.pojo.WebhookListResponse;
import io.practiceinsight.pinpayments.pojo.WebhookResponse;
import io.practiceinsight.pinpayments.pojo.WebhookResult;

/**
 * Integration tests for {@link WebhooksApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class WebhooksApiIntegrationTest extends AbstractBaseTest {

  private WebhooksApi webhooksApi;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    webhooksApi = inject(WebhooksApi.class);
  }

  @Test(groups = "integration")
  public final void testList() throws Exception {
    final WebhookListResponse list = webhooksApi.list(1);
    assertThat(list).isNotNull();
    assertThat(list.response()).isEmpty();
  }

  @Test(groups = "integration", enabled = false)
  public final void testFetch() throws Exception {
    // TODO(Jake): Cannot do integration tests for now because no webhooks data exists.
    // Given
    final WebhookListResponse list = webhooksApi.list(1);
    final WebhookResult webhookResult = list.response().get(0);
    final String webhookToken = webhookResult.token();

    // When
    final WebhookResponse fetched = webhooksApi.fetch(webhookToken);

    // Then
    assertThat(fetched.response().token()).isEqualTo(webhookToken);
  }

  @Test(groups = "integration", enabled = false)
  public final void testReplay() throws Exception {
    // TODO(Jake): Cannot do integration tests for now because no webhooks data exists.
    // Given
    final WebhookListResponse list = webhooksApi.list(1);
    final WebhookResult webhookResult = list.response().get(0);
    final String webhookToken = webhookResult.token();

    // When
    final WebhookResponse replayed = webhooksApi.replay(webhookToken);

    // Then
    assertThat(replayed.response().token()).isEqualTo(webhookToken);
  }

}
