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
import io.practiceinsight.pinpayments.pojo.EventListResponse;
import io.practiceinsight.pinpayments.pojo.EventResponse;
import io.practiceinsight.pinpayments.api.impl.EventsApiImpl;

/**
 * The events API allows you to view the activity on your account. Note that some actions
 * may create multiple events.
 *
 * @see <a href="https://pinpayments.com/docs/api/events">Events API</a>
 * @author delight.wjk@gmail.com
 */
@ImplementedBy(EventsApiImpl.class)
public interface EventsApi {

  /**
   * Returns a paginated list of all events.
   *
   * @param page The page number specified.
   * @return A paginated list of all events.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  EventListResponse list(int page) throws IOException;

  /**
   * Returns the details of an event.
   *
   * @param eventToken The event token specified.
   * @return The details of the event fetched.
   * @throws IOException If IO errors occur while calling PinPayments REST API.
   */
  EventResponse fetch(String eventToken) throws IOException;
}
