# Java Wrapper for PinPayments API

This is a Java wrapper around `pinpayments.com` API, which follows the structure of PinPayments' original RESTful API and covers all RESTful services completely.

All wrapper methods have been covered with integration tests against PinPayments' test environment.

## Why?

When I woke up in the morning at 4am, I realised PinPayments didn't even have a Java language support for its nice RESTful API. I felt I should create one, so I spent my Sunday working on it, and then this small library was born.

## How to use?

Check out the project and build:

```
make build
```

If you want to install the latest release version to your local Maven repo, please simply run:

```
make publish
```
