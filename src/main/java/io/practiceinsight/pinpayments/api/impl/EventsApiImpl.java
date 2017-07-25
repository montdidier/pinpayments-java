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

package io.practiceinsight.pinpayments.api.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.practiceinsight.pinpayments.api.EventsApi;
import io.practiceinsight.pinpayments.http.HttpResponse;
import io.practiceinsight.pinpayments.pojo.EventListResponse;
import io.practiceinsight.pinpayments.pojo.EventResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableEventListResponse;
import io.practiceinsight.pinpayments.pojo.ImmutableEventResponse;

/**
 * Implementation of {@link EventsApi} interface.
 *
 * @author delight.wjk@gmail.com
 */
public class EventsApiImpl extends AbstractBaseApi implements EventsApi {

  private static final Logger log = LoggerFactory.getLogger(EventsApiImpl.class); // NOPMD

  @Inject
  private Gson gson;

  @Override
  public final EventListResponse list(final int page) throws IOException {
    return executeList(getApiUrl(), ImmutableEventListResponse.class);
  }

  @Override
  public final EventResponse fetch(final String eventToken) throws IOException {
    final String url = String.format("%s/%s", getApiUrl(), eventToken);
    final HttpResponse httpResponse = httpTransportForGet().setUrl(url).execute();
    log.debug("httpResponse: " + httpResponse);
    return gson.fromJson(httpResponse.getContent(), ImmutableEventResponse.class);
  }

  @Override
  protected final String getApiName() {
    return "events";
  }
}
