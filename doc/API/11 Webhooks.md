# Webhooks API

The webhooks API allows you to view and replay webhook requests that have been sent to your webhook endpoints. When an event is created, a unique webhook request is created and sent to each webhook endpoint.

Replaying a webhook re-sends a webhook request to its original URL. It will not re-send other webhook requests for the same event. Replaying a webhook will reset the error information recorded for the original request and record any new errors that occur during the replay.

## Lists webhooks

```
@Inject
private WebhooksApi webhooksApi;

public final void myBusinessMethod() {
  final WebhookListResponse list = webhooksApi.list(1);
  assertThat(list).isNotNull();
  assertThat(list.response()).isEmpty();
}
```

## Fetches a webhook

```
@Inject
private WebhooksApi webhooksApi;

public final void myBusinessMethod() {
  final String webhookToken = "webhook-token";
  final WebhookResponse fetched = webhooksApi.fetch(webhookToken);
  assertThat(fetched.response().token()).isEqualTo(webhookToken);
}
```

## Replays a webhook

```
@Inject
private WebhooksApi webhooksApi;

public final void myBusinessMethod() {
  final String webhookToken = "webhook-token";
  final WebhookResponse replayed = webhooksApi.replay(webhookToken);
  assertThat(replayed.response().token()).isEqualTo(webhookToken);
}
```
