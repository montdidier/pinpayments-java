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

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Nullable;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * @author delight.wjk@gmail.com
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ChargeResult extends ChargeBase {

  String token();

  boolean success();

  String createdAt();

  String statusMessage();

  @Nullable
  String errorMessage();

  CardResult card();

  boolean captured();

  boolean authorisationExpired();

  List<Transfer> transfer();

  BigDecimal amountRefunded();

  @Nullable
  BigDecimal totalFees();

  @Nullable
  BigDecimal merchantEntitlement();

  boolean refundPending();

  String settlementCurrency();
}
