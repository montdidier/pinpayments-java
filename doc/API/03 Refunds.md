# Refunds API

The refunds API allows you to refund a charge, and retrieve the details of previous refunds.

## Lists refunds

```
@Inject
private RefundsApi refundsApi;

public final void myBusinessMethod() {
  final RefundListResponse list = refundsApi.list(1);
  assertThat(list).isNotNull();
}
```

## Fetches a refund

```
@Inject
private RefundsApi refundsApi;

public final void myBusinessMethod() {
  final String refundToken = "refund-token";
  final RefundResponse fetched = refundsApi.fetch(refundToken);
  assertThat(fetched).isNotNull();
}
```

## Creates a new refund

```
@Inject
private RefundsApi refundsApi;

public final void myBusinessMethod() {
  final RefundResponse refundResponse = refundsApi.create(chargeToken, ImmutableRefund.builder().build());
  assertThat(refundResponse.response().token()).isNotBlank();
}
```

## Lists refunds for a charge

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String chargeToken = "charge-token";
  final RefundListResponse listForCharge = refundsApi.listForCharge(chargeToken, 1);
  assertThat(listForCharge.response()).isNotEmpty();
}
```
