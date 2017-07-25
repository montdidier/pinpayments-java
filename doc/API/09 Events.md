# Events API

The events API allows you to view the activity on your account. Note that some actions may create multiple events.

It’s expected that additional event types will be introduced over time. Event types currently include:

* charge.authorised
* charge.captured
* charge.failed
* charge.refunded
* customer.created
* customer.updated
* customer.deleted
* recipient.created
* recipient.updated
* recipient.deleted
* refund.created
* transfer.created

## Lists events

```
@Inject
private EventsApi eventsApi;

public final void myBusinessMethod() {
  final EventListResponse list = eventsApi.list(1);
  assertThat(list.response()).isNotEmpty();
}
```

## Fetches a event

```
@Inject
private EventsApi eventsApi;

public final void myBusinessMethod() {
  final String eventToken = "event-token";
  final EventResponse fetched = eventsApi.fetch(eventToken);
  assertThat(fetched.response()).isNotNull();
}
```
