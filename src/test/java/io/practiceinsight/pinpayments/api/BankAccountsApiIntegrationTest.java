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
import io.practiceinsight.pinpayments.TestDataFactory;
import io.practiceinsight.pinpayments.pojo.BankAccountResponse;

/**
 * Integration tests for {@link BankAccountsApi} class.
 *
 * @author delight.wjk@gmail.com
 */
@Test(groups = "integration")
public class BankAccountsApiIntegrationTest extends AbstractBaseTest {

  private BankAccountsApi bankAccountsApi;
  private TestDataFactory dataFactory;

  @BeforeMethod(groups = "integration")
  public final void setUp() throws Exception {
    bankAccountsApi = inject(BankAccountsApi.class);
    dataFactory = inject(TestDataFactory.class);
  }

  @Test(groups = "integration")
  public final void testCreate() throws Exception {
    final BankAccountResponse bankAccount = bankAccountsApi.create(dataFactory.buildBankAccount());
    assertThat(bankAccount.response().token()).isNotBlank();
  }

}
