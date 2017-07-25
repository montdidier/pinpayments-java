# Transfers API

The transfers API allows you to send money to Australian bank accounts, and to retrieve details of previous transfers.

## Creates a new transfer

```
@Inject
private TransfersApi transfersApi;

public final void myBusinessMethod() {
  final String recipientToken = "recipient-token";
  final TransferResponse transfer = transfersApi.create(ImmutableTransfer.builder()
    .amount(400)
    .currency("AUD")
    .description("Earnings for may")
    .recipient(recipientToken).build());
  assertThat(transfer.response().token()).isNotBlank();
}
```

## Lists transfers

```
@Inject
private TransfersApi transfersApi;

public final void myBusinessMethod() {
  final TransferListResponse list = transfersApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isNotEmpty();
}
```

## Searches transfers

```
@Inject
private TransfersApi transfersApi;

public final void myBusinessMethod() {
  final ImmutableSearchCriteria criteria = ImmutableSearchCriteria.builder()
      .query("test").build();
  final TransferListResponse result = transfersApi.search(criteria);
  assertThat(result.response()).isNotEmpty();
}
```

## Fetches a transfer

```
@Inject
private TransfersApi transfersApi;

public final void myBusinessMethod() {
  final String transferToken = "transfer-token";
  final TransferResponse fetchedResponse = transfersApi.fetch(transferToken);
  assertThat(fetchedResponse.response().token()).isEqualTo(transferToken);
}
```

## Lists transfer line items

```
@Inject
private TransfersApi transfersApi;

public final void myBusinessMethod() {
  final String transferToken = "transfer-token";
  final TransferLineItemListResponse fetchedResponse = transfersApi
      .listLineItems(transferToken, 1);
  assertThat(fetchedResponse.response()).isNotEmpty();
}
```
