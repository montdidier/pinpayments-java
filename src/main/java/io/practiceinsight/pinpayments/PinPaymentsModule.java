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

package io.practiceinsight.pinpayments;

import com.google.inject.AbstractModule;

import io.practiceinsight.pinpayments.annotation.PinBaseUrl;
import io.practiceinsight.pinpayments.annotation.PinSecretKey;
import io.practiceinsight.pinpayments.annotation.PinVersion;

/**
 * Main Guice module for Pin Payments API.
 *
 * @author delight.wjk@gmail.com
 */
public class PinPaymentsModule extends AbstractModule {

  private static final String PIN_BASEURL_TEST = "https://test-api.pinpayments.com";
  private static final String PIN_BASEURL_PROD = "https://api.pinpayments.com";
  private static final String PIN_VERSION = "1";

  private final Env env;
  private final String pinSecretKey;

  public PinPaymentsModule(final Env env, final String pinSecretKey) {
    super();
    this.env = env;
    this.pinSecretKey = pinSecretKey;
  }

  @Override
  protected final void configure() {
    install(new GsonModule());
    if (env == Env.Prod) {
      bind(String.class).annotatedWith(PinBaseUrl.class).toInstance(PIN_BASEURL_PROD);
    } else {
      bind(String.class).annotatedWith(PinBaseUrl.class).toInstance(PIN_BASEURL_TEST);
    }
    bind(String.class).annotatedWith(PinVersion.class).toInstance(PIN_VERSION);
    bind(String.class).annotatedWith(PinSecretKey.class).toInstance(pinSecretKey);
  }

  /**
   * @author delight.wjk@gmail.com
   */
  public enum Env {
    Test,
    Prod
  }
}
