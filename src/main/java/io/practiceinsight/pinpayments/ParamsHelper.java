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

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author delight.wjk@gmail.com
 */
public class ParamsHelper {

  @Inject
  private Gson gson;

  public final Map<String, String> toFlatParams(final Object object) {
    final Map<String, Object> paramsMap = toParamsMap(object);
    for (final Entry<String, Object> entry : paramsMap.entrySet()) {
      final Object value = entry.getValue();
      if (value instanceof Double) {
        paramsMap.put(entry.getKey(), ((Double) value).intValue());
      }
    }
    return flattenParams(paramsMap);
  }

  private Map<String, Object> toParamsMap(final Object object) {
    final String json = gson.toJson(object);
    final Map<String, Object> paramsMap = Maps.newHashMap();
    return gson.fromJson(json, paramsMap.getClass());
  }

  private Map<String, String> flattenParams(final Map<String, Object> params) {
    final Map<String, String> flatParams = Maps.newHashMap();
    for (final Map.Entry<String, Object> entry : params.entrySet()) {
      final String key = entry.getKey();
      final Object value = entry.getValue();
      if (value instanceof Map<?, ?>) {
        final Map<String, Object> flatNestedMap = Maps.newHashMap();
        final Map<?, ?> nestedMap = (Map<?, ?>) value;
        for (final Map.Entry<?, ?> nested : nestedMap.entrySet()) {
          flatNestedMap.put(String.format("%s[%s]", key, nested.getKey()), nested.getValue());
        }
        flatParams.putAll(flattenParams(flatNestedMap));
      } else if (value == null) {
        flatParams.put(key, "");
      } else {
        flatParams.put(key, value.toString());
      }
    }
    return flatParams;
  }
}
