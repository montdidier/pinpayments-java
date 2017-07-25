# Balance API

The balance API allows you to see the current balance of funds in your Pin Payments account. You can use this to confirm whether a transfer is possible.

## Gets the balance

```
@Inject
private BalanceApi balanceApi;

public final void myBusinessMethod() {
  final BalanceResponse fetched = balanceApi.fetch();
  assertThat(fetched).isNotNull();
  assertThat(fetched.response().available()).isNotNull();
  assertThat(fetched.response().pending()).isNotNull();
}
```
