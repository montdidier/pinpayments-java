# Bank Accounts API

The bank account API allows you to securely store bank account details in exchange for a bank account token. This token can then be used to create a recipient using the recipients API.

A bank account token can only be used once to create a recipient. The token automatically expires after 1 month if it hasn’t been used.

## Creates a new bank account

```
@Inject
private BalanceApi balanceApi;

public final void myBusinessMethod() {
  final BankAccountResponse bankAccount = bankAccountsApi.create(ImmutableBankAccount.builder()
    .name("Mr Roland Robot")
    .bsb("123456")
    .number("987654321").build());
  assertThat(bankAccount.response().token()).isNotBlank();
}
```
