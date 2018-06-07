# Overview

This is a Java wrapper around pinpayments.com API, which follows the structure of PinPayments' original RESTful API and covers all RESTful services completely.

All wrapper methods have been covered with integration tests against PinPayments' test environment.

## Why?

When I woke up in the morning at 4am, I realised PinPayments didn't even have a Java language support for its nice RESTful API. I felt I should create one, so I spent my Sunday working on it, and then this small library was born.

## Add Maven/Gradle dependency

PinPayments Java is available in Maven central repository:

[http://search.maven.org/#search%7Cga%7C1%7Cpinpayments-java](http://search.maven.org/#search%7Cga%7C1%7Cpinpayments-java)

To add the dependency to your `build.gradle` (for Gradle projects) or `pom.xml` (for Maven projects), use the following configuration:

For Gradle projects:

```
compile 'io.practiceinsight:pinpayments-java:1.0.2'
```

For Maven projects:

```
<dependency>
    <groupId>io.practiceinsight</groupId>
    <artifactId>pinpayments-java</artifactId>
    <version>1.0.2</version>
</dependency>
```

If you would like to use the 1.0.3-SNAPSHOT release, use this configuration.

For Gradle projects:

```
compile 'io.practiceinsight:pinpayments-java:1.0.3-SNAPSHOT'
```

For Maven projects:

```
<dependency>
    <groupId>io.practiceinsight</groupId>
    <artifactId>pinpayments-java</artifactId>
    <version>1.0.3-SNAPSHOT</version>
</dependency>
```

In order to use snapshot releases you also need to add the Sonatype snapshots repository to your POM:

```
<repositories>
    <repository>
        <id>sonatype-nexus-snapshots</id>
        <url>http://oss.sonatype.org/content/repositories/snapshots</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>false</enabled>
        </releases>
    </repository>
</repositories>
```

## Configure Guice Module

PinPayments Java is based on Google Guice. To use this library, simply add the following Guice module when initialising your Guice injector.

```
final String pinSecretKey = "Your PinPayments secret key";
Guice.createInjector(new PinPaymentsModule(Env.Test, pinSecretKey));
```

And then you can inject different API wrappers in your classes and use the wrapper methods.

```
@Inject
private ChargesApi chargesApi;

public final void myBusinessMethod() {
  final ChargeResponse chargeResponse = chargesApi.create(ImmutableCharge.builder()
    .email("roland@pinpayments.com")
    .currency("AUD")
    .description("test charge")
    .amount(400)
    .ipAddress("203.192.1.172")
    .capture(String.valueOf(capture))
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
    .metadata(ImmutableMap.of("OrderNumber", "123456", "CustomerName", "Roland Robot"))
    .build());
  assertThat(chargeResponse.response().token()).isNotBlank();
}
```
