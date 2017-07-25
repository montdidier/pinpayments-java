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

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.practiceinsight.pinpayments.pojo.Charge;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ParamsHelper} class.
 *
 * @author delight.wjk@gmail.com
 */
public class ParamsHelperTest {

  private ParamsHelper paramsHelper;
  private TestDataFactory dataFactory;

  @BeforeMethod
  public final void setUp() throws Exception {
    final Injector injector = Guice.createInjector(new GsonModule());
    paramsHelper = injector.getInstance(ParamsHelper.class);
    dataFactory = injector.getInstance(TestDataFactory.class);
  }

  @Test
  public final void testToFlatParams() throws Exception {
    // Given
    final Charge charge = dataFactory.buildCharge(TestCard.SuccessVisa);

    // When
    final Map<String, String> flatParams = paramsHelper.toFlatParams(charge);

    // Then
    assertThat(flatParams).isNotEmpty();
    final int expectedSize = 19;
    assertThat(flatParams.size()).isEqualTo(expectedSize);
  }
}
