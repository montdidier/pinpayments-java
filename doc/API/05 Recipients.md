# Recipients API

The recipients API allows you to post bank account details and retrieve a token that you can safely store in your app. You can send funds to recipients using the transfers API.

## Creates a new recipient

```
@Inject
private RecipientsApi recipientsApi;

public final void myBusinessMethod() {
  final RecipientResponse response = recipientsApi.create(ImmutableRecipient.builder()
    .email("roland@pinpayments.com")
    .name("Mr Roland Robot")
    .bankAccount(buildBankAccount()).build());
  assertThat(response.response().token()).isNotBlank();
}
```

## Lists recipients

```
@Inject
private RecipientsApi recipientsApi;

public final void myBusinessMethod() {
  final RecipientListResponse list = recipientsApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isNotEmpty();
}
```

## Fetches a recipient

```
@Inject
private RecipientsApi recipientsApi;

public final void myBusinessMethod() {
  final String recipientToken = "recipient-token";
  final RecipientResponse fetched = recipientsApi.fetch(recipientToken);
  assertThat(fetched.response().token()).isEqualTo(recipientToken);
}
```

## Lists transfers of a recipient

```
@Inject
private RecipientsApi recipientsApi;

public final void myBusinessMethod() {
  final String recipientToken = "recipient-token";
  final TransferListResponse listResponse = recipientsApi.listTransfers(recipientToken, 1);
  assertThat(listResponse.response()).isEmpty();
}
```

