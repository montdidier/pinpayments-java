/*
 * Copyright (c) 2016 Practice Insight
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

package io.practiceinsight.pinpayments.http;

import java.io.Closeable;
import java.io.IOException;

/**
 * Resource handler interface whose implementation will contain actual resource processing logic.
 *
 * @author delight.wjk@gmail.com
 * @param <T> The result type.
 */
public interface ResourceHandler<T> {

  /**
   * Handles the resource and returns the result.
   *
   * @param closeable The closeable resource specified.
   * @return The result of resource processing.
   * @throws IOException If IO errors occur.
   */
  T handle(Closeable closeable) throws IOException;
}
