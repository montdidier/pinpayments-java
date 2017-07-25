# Webhook Endpoints API

The Webhook Endpoints API allows you to create and view your webhook endpoints to enable your website to receive push notifications of events that occur on your Pin Payments account.

## Creates a new Webhook endpoint

```
@Inject
private WebhookEndpointsApi endpointsApi;

public final void myBusinessMethod() {
  final WebhookEndpointResponse response = endpointsApi
      .create(ImmutableWebhookEndpoint.builder()
        .url("https://example.org/webhooks/" + System.currentTimeMillis()).build());
  assertThat(response.response().token()).isNotBlank();
}
```

## Lists Webhook endpoints

```
@Inject
private WebhookEndpointsApi endpointsApi;

public final void myBusinessMethod() {
  final WebhookEndpointListResponse list = endpointsApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isNotEmpty();
}
```

## Fetches a Webhook endpoint

```
@Inject
private WebhookEndpointsApi endpointsApi;

public final void myBusinessMethod() {
  final String endpointToken = "endpoint-token";
  final WebhookEndpointResponse fetched = endpointsApi.fetch(endpointToken);
  assertThat(fetched.response().token()).isEqualTo(endpointToken);
}
```

## Deletes a Webhook endpoint

```
@Inject
private WebhookEndpointsApi endpointsApi;

public final void myBusinessMethod() {
  final String endpointToken = "endpoint-token";
  final DeletionResult deletionResult = endpointsApi.delete(endpointToken);
  assertThat(deletionResult.success()).isTrue();
}
```
