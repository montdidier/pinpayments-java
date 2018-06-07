# Charges API

The charges API allows you to create new credit card charges, and to retrieve details of previous charges.

## Creates a new charge

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final ChargeResponse chargeResponse = chargesApi.create(ImmutableCharge.builder()
    .email("roland@pinpayments.com")
    .currency("AUD")
    .description("test charge")
    .amount(400)
    .ipAddress("203.192.1.172")
    .capture(String.valueOf(capture))
    .card(ImmutableCard.builder()
      .number("5520000000000000")
      .expiryMonth("05")
      .expiryYear("2018")
      .cvc("123")
      .name("Roland Robot")
      .addressLine1("42 Sevenoaks St")
      .addressLine2("")
      .addressCity("Lathlain")
      .addressPostcode("6454")
      .addressState("WA")
      .addressCountry("Australia")
      .build())
    .metadata(ImmutableMap.of("OrderNumber", "123456", "CustomerName", "Roland Robot"))
    .build());
  assertThat(chargeResponse.response().token()).isNotBlank();
}
```

## Captures a charge

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final String chargeToken = "charge-token";
  final ChargeResponse chargeResponse = chargesApi.capture(chargeToken);
  assertThat(chargeResponse.response().token()).isNotBlank();
}
```

## Lists charges

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final ChargeListResponse list = chargesApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isNotEmpty();
}
```

## Search charges

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final ImmutableSearchCriteria criteria = ImmutableSearchCriteria.builder()
    .query("test").build();

  final ChargeListResponse result = chargesApi.search(criteria);
  assertThat(result.response()).isNotEmpty();
}
```

## Fetch a charge

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final String chargeToken = "charge-token";
  final ChargeResponse fetchedResponse = chargesApi.fetch(chargeToken);
  assertThat(fetchedResponse.response().token()).isEqualTo(chargeToken);
}
```
