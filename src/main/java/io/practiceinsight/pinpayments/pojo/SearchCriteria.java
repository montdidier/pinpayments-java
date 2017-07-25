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

package io.practiceinsight.pinpayments.pojo;

import javax.annotation.Nullable;
import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.joda.time.DateTime;

/**
 * @author delight.wjk@gmail.com
 */
@SuppressWarnings("AbstractClassName")
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public abstract class SearchCriteria { // NOPMD

  @Nullable
  public abstract String query();

  @Nullable
  public abstract DateTime startDate();

  @Nullable
  public abstract DateTime endDate();

  @Nullable
  public abstract String sort();

  /**
   * The direction in which to sort the transfers (1 for ascending or -1 for descending).
   * Default value is 1.
   *
   * @return The direction specified.
   */
  @SuppressWarnings("DesignForExtension")
  @Value.Default
  public int direction() {
    return 1;
  }
}
