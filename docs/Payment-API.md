# Payment API
This document describes common APIs for all payment connectors

## Payment Connector
This is an interface class defines the standard functions that any payment connector needs to define.

The main methods implemented by connectors are: 
* authorize: Authorize an amount on the customers card
* completeAuthorize: Handle return from off-site gateways after authorization
* capture: Capture an amount you have previously authorized
* purchase: Authorize and immediately capture an amount on the customers card
* completePurchase: Handle return from off-site gateways after purchase
* refund: Refund an already processed transaction
* void: Generally can only be called up to 24 hours after submitting a transaction
* createCard: The returned response object includes a cardReference, which can be used for future transactions
* updateCard: Update a stored card
* deleteCard: Delete a stored card

All connector methods take an `Map<String, Object>` of parametters as an argument and return Payment Request object. Each connector differs in which parameters are required, and the connector will throw InvalidRequestException if you omit any required parameters.

All connectors will accept a subset of these options:
* card: The credit card object
* token: The merchant token of connector
* amount: Amount on the customers card
* currency: The currency object
* description: The description of connector
* transactionId: A merchant transaction ID
* clientIp: The client ip of the customer
* returnUrl: The return url of on-site website
* cancelUrl: The cancel url of on-site website

Example:
```java
    PaymentConnector connector = ConnectorFactory.getConnector(MyConnector.class);
    // Initialise the connector by a Map<String, Object> parametters
    connector.initialize(params);

    // Create a credit card object
    CreditCard card = new CreditCard();

    // Do an authorisation transaction on the gateway
    if (connector.supportsAuthorize()) {
        connector.authorize(params);
    }
    else {
        throw new RuntimeException("Gateway does not support authorize()");
    }
```

## Payment Request
This is an interface class defines the standard functions that any OpenCPS Payment request interface needs to be able to provide.
Request objects are usually created using the `createRequest()` method of the connector and then actioned using methods within the request.
To make the request to the payment gateway. Request objects using the Google Http Client for requesting or Java Servlet for redirecting.

Example -- validating and sending a request:
```java
    PaymentConnector connector = ConnectorFactory.getConnector(MyConnector.class);
    PaymentRequest request = connector.myRequest(params);
    try {
        request.validate();
        PaymentResponse response = request.send();
    } catch (InvalidRequestException e) {
        // something went wrong
    }
```

## Payment Response
This is an interface class defines the standard functions that any OpenCPS Payment response interface needs to be able to provide.
Objects of this interface are usually created in the Payment Request object as the return parameters from the `send()` method.

There are two main types of response:
* Payment was successful (standard response)
* Website requires redirect to off-site payment form (redirect response)

### Successful Response
For a successful responses, a reference will normally be generated, which can be used to capture or refund the transaction at a later date. The following methods are always available:
```java
    PaymentResponse response = connector.purchase(params).send();
    response.isSuccessful(); // is the response successful?
    response.isRedirect(); // is the response a redirect?
    response.getTransactionReference(); // a reference generated by the payment connector
    response.getMessage(); // a message generated by the payment connector
```
In addition, most connectors will override the response object, and provide access to any extra fields returned by the connector.

### Redirect Response
The redirect response is further broken down by whether the customer’s browser must redirect using GET (RedirectResponse object), or POST (FormRedirectResponse). These could potentially be combined into a single response class, with a `getRedirectMethod()`.
After processing a payment, the cart should check whether the response requires a redirect, and if so, redirect accordingly:
```java
    PaymentResponse response = connector.purchase(params).send();
    if (response.isSuccessful()) {
        // payment is complete
    }
    else if(response.isRedirect()) {
        response.redirect(); // this will automatically forward the customer
    }
    else {
        // not successful
    }
```
The customer isn’t automatically forwarded on, because often the cart or developer will want to customize the redirect method (or if payment processing is happening inside an AJAX call they will want to return JS to the browser instead).
To display your own redirect page, simply call `getRedirectUrl()` on the response, then display it accordingly:
```java
    String url = response.getRedirectUrl();
    // for a form redirect, you can also call the following method:
    Map<String, String> data = response.getRedirectData(); // An Map of fields which must be posted to the redirectUrl
```