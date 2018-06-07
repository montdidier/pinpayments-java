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
import io.practiceinsight.pinpayments.pojo.DeletionResult;
import io.practiceinsight.pinpayments.pojo.WebhookEndpoint;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointListResponse;
import io.practiceinsight.pinpayments.pojo.WebhookEndpointResponse;
import io.practiceinsight.pinpayments.api.impl.WebhookEndpointsApiImpl;

/**
 * The Webhook Endpoints API allows you to create and view your webhook endpoints to enable
 * your website to receive push notifications of events that occur on your Pin Payments account.
 *
 * @see <a href="https://pinpayments.com/docs/api/webhook_endpoints">Webhook Endpoints API Beta</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(WebhookEndpointsApiImpl.class)
public interface WebhookEndpointsApi {

  /**
   * Creates a new webhook endpoint and returns its details.
   *
   * @param endpoint The endpoint specified.
   * @return The details of the endpoint created.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookEndpointResponse create(WebhookEndpoint endpoint) throws IOException;

  /**
   * Returns a paginated list of all webhook endpoints.
   *
   * @param page The page number specified.
   * @return A paginated list of all webhook endpoints.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookEndpointListResponse list(int page) throws IOException;

  /**
   * Returns the details of a webhook endpoint.
   *
   * @param endpointToken The endpoint token specified.
   * @return The details of the webhook endpoint fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  WebhookEndpointResponse fetch(String endpointToken) throws IOException;

  /**
   * Deletes a webhook endpoint and all of its webhook requests. You will not be able to
   * recover them.
   *
   * @param endpointToken The endpoint token specified.
   * @return The deletion result.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  DeletionResult delete(String endpointToken) throws IOException;
}
