# Card Tokens API

The card tokens API allows you to securely store credit card details in exchange for a card token. This card token can then be used to create a single charge with the charges API, or to create multiple charges over time using the customers API.

Returned card information contains a member called primary, which says whether the card is a customer’s primary card. Its value is true if the card is a customer’s primary card, false if it is a non-primary card of the customer, and null if it is not associated with a customer.

A card token can only be used once, to create either a charge or a customer. If no charge or customer is created within 1 month, the token is automatically expired.

## Creates a new card token

```
@Inject
private CardTokensApi cardTokensApi;

public final void myBusinessMethod() {
  final CardResponse cardResponse = cardTokensApi.create(ImmutableCard.builder()
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
  assertThat(cardResponse.response().token()).isNotBlank();
}
```
