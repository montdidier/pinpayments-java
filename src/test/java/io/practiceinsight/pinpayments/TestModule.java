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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.inject.AbstractModule;

import java.util.ServiceLoader;

/**
 * Main Guice module for Pin Payments API.
 *
 * @author delight.wjk@gmail.com
 */
public class TestModule extends AbstractModule {

  @Override
  protected final void configure() {
    bind(Gson.class).toInstance(createGson());
  }

  private Gson createGson() {
    final GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
    for (final TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
      gsonBuilder.registerTypeAdapterFactory(factory);
    }
    return gsonBuilder.create();
  }

  /**
   * @author delight.wjk@gmail.com
   */
  public enum Env {
    Test,
    Prod
  }
}
