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

import com.google.inject.ImplementedBy;
import java.io.IOException;
import io.practiceinsight.pinpayments.api.impl.WebhooksApiImpl;
import io.practiceinsight.pinpayments.pojo.WebhookListResponse;
import io.practiceinsight.pinpayments.pojo.WebhookResponse;

/**
 * The webhooks API allows you to view and replay webhook requests that have been sent
 * to your webhook endpoints. When an event is created, a unique webhook request is
 * created and sent to each webhook endpoint.
 *
 * <p>Replaying a webhook re-sends a webhook request to its original URL. It will not
 * re-send other webhook requests for the same event. Replaying a webhook will reset the
 * error information recorded for the original request and record any new errors that
 * occur during the replay.</p>
 *
 * @see <a href="https://pinpayments.com/docs/api/webhooks">Webhooks API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(WebhooksApiImpl.class)
public interface WebhooksApi {

  /**
   * Returns a paginated list of all webhooks.
   *
   * @param page The page number specified.
   * @return A paginated list of all webhooks.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookListResponse list(int page) throws IOException;

  /**
   * Returns the details of a webhook.
   *
   * @param webhookToken The webhook token specified.
   * @return The details of a webhook.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookResponse fetch(String webhookToken) throws IOException;

  /**
   * Replays a webhook.
   *
   * @param webhookToken The webhook token specified.
   * @return The details of the webhook replayed.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookResponse replay(String webhookToken) throws IOException;
}
