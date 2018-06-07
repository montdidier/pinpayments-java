# Customers API

The customers API allows you to store a customer’s email and credit card details. A customer can then be used with the charges API to create multiple charges over time.

Customers can have multiple cards associated with them, and one will be considered the customer’s primary card. The card object in returned customer information represents this primary card. It contains a member called primary, which says whether the card is a customer’s primary card; its value will always be true.

## Creates a new customer

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final CustomerResponse customerResponse = customersApi.create(ImmutableCustomer.builder()
    .email("roland@pinpayments.com")
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
    .build());
  assertThat(customerResponse.response().token()).isNotBlank();
}
```

## Lists customers

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final CustomerListResponse list = customersApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isNotEmpty();
}
```

## Fetches a customer

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final CustomerResponse fetchedResponse = customersApi.fetch(customerToken);
  assertThat(fetchedResponse.response().token()).isEqualTo(customerToken);
}
```

## Updates a customer

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final CustomerResponse updated = customersApi
      .update(customerToken, ImmutableCustomer.builder()
         .email("roland@pinpayments.com")
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
         .build());
  assertThat(updated.response().token()).isEqualTo(customerToken);
}
```

## Deletes a customer

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final DeletionResult deletionResult = customersApi.delete(customerToken);
  assertThat(deletionResult.success()).isTrue();
}
```

## Lists charges


```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final ChargeListResponse response = customersApi.listCharges(customerToken, 1);
  assertThat(response.response()).isEmpty();
}
```

## Lists cards


```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final CardListResponse response = customersApi.listCards(customerToken, 1);
  assertThat(response.response()).isNotEmpty();
}
```

## Creates a new card

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final CardResponse card = customersApi.createCard(customerToken, ImmutableCard.builder()
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
    .build());
  assertThat(card).isNotNull();
}
```

## Deletes a new card

```
@Inject
private CustomersApi customersApi;

public final void myBusinessMethod() {
  final String customerToken = "customer-token";
  final String cardToken = "card-token";
  final DeletionResult result = customersApi.deleteCard(customerToken, cardToken);
  assertThat(result.success()).isTrue();
}
```
