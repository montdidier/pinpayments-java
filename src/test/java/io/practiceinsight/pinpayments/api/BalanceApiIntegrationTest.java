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

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.practiceinsight.pinpayments.pojo.BalanceResponse;

/**
 * Integration tests for {@link BalanceApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class BalanceApiIntegrationTest extends AbstractBaseTest {

  private BalanceApi balanceApi;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    balanceApi = inject(BalanceApi.class);
  }

  @Test(groups = "integration")
  public final void testFetch() throws Exception {
    final BalanceResponse fetched = balanceApi.fetch();
    assertThat(fetched).isNotNull();
    assertThat(fetched.response().available()).isNotNull();
    assertThat(fetched.response().pending()).isNotNull();
  }

}
