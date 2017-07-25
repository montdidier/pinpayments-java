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

import com.google.common.base.Strings;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.Test;
import io.practiceinsight.pinpayments.PinPaymentsModule;

/**
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public abstract class AbstractBaseTest {

  protected final Injector getInjector() {
    final String pinSecretKey = System.getenv("pinSecretKey");
    if (Strings.isNullOrEmpty(pinSecretKey)) {
      throw new IllegalStateException("Please specify environment variable 'pinSecretKey'.");
    }
    return Guice.createInjector(new PinPaymentsModule(PinPaymentsModule.Env.Test, pinSecretKey));
  }

  protected final <T> T inject(final Class<T> clazz) {
    return getInjector().getInstance(clazz);
  }
}
