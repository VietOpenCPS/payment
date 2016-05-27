# Payment API
This document describes common APIs for all payment connectors

## Payment Connector
This is an interface class defines the standard functions that any payment connector needs to define.
- **The main methods implemented by connectors are**: 
   + authorize: Authorize an amount on the customers card
   + completeAuthorize: Handle return from off-site gateways after authorization
   + capture: Capture an amount you have previously authorized
   + purchase: Authorize and immediately capture an amount on the customers card
   + completePurchase: Handle return from off-site gateways after purchase
   + refund: Refund an already processed transaction
   + void: Generally can only be called up to 24 hours after submitting a transaction
   + createCard: The returned response object includes a cardReference, which can be used for future transactions
   + updateCard: Update a stored card
   + deleteCard: Delete a stored card

All connector methods take an `Map<String, Object>` of parametters as an argument. Each connector differs in which parameters are required, and the gateway will throw InvalidRequestException if you omit any required parameters.

All gateways will accept a subset of these options:
* card: The credit card object
* token: The merchant token of connector
* amount: Amount on the customers card
* currency: The currency object
* description: The description of connector
* transactionId: A merchant transaction ID
* clientIp: The client ip of the customer
* returnUrl: The return url of on-site website
* cancelUrl: The cancel url of on-site website

## Payment Request
This is an interface class defines the standard functions that any OpenCPS Payment request interface needs to be able to provide.

## Payment Response
This is an interface class defines the standard functions that any OpenCPS Payment response interface needs to be able to provide.
