/**
* OpenCPS Payment is the open source Core Payment Connector software
* Copyright (C) 2016-present OpenCPS community

* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero General Public License for more details.
* You should have received a copy of the GNU Affero General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
*/
package org.opencps.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opencps.payment.api.PaymentItem;
import org.opencps.payment.api.PaymentRequest;
import org.opencps.payment.api.PaymentResponse;
import org.opencps.payment.exception.InvalidRequestException;

/**
 * This abstract class implements PaymentRequest and defines a basic
 * set of functions that all OpenCPS Payment Requests are intended to include.
 * Requests of this class are usually created using the createRequest
 * method of the connector and then actioned using methods within this
 * class or a class that extends this class.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public abstract class RequestBase implements PaymentRequest {

    /**
     * The request parameters
     */
    protected Map<String, String> parameters;

    /**
     * The HTTP transport object.
     */
    protected ConnectorBase connector;

    /**
     * The item parameters
     */
    protected List<PaymentItem> items;

    /**
     * An associated PaymentResponse.
     */
    protected PaymentResponse response;

    /**
     * The credit card parameter
     */
    protected CreditCard card;

    protected static Boolean zeroAmountAllowed = true;

    protected static Boolean negativeAmountAllowed = false;

    public RequestBase(ConnectorBase connector) {
        this.connector = connector;
        parameters = new HashMap<String, String>();
        items = new ArrayList<PaymentItem>();
    }

    /**
     * (non-Javadoc)
     * @throws InvalidRequestException 
     * @see org.opencps.payment.api.PaymentRequest#initialize()
     */
    @Override
    public PaymentRequest initialize(Map<String, String> parameters) throws RuntimeException {
        if (response != null) {
            throw new RuntimeException("Request cannot be modified after it has been sent!");
        }
        if (this.parameters == null) {
            this.parameters = new HashMap<String, String>();
        }
        for (Map.Entry<String, String> entry: parameters.entrySet()) {
            this.parameters.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.PaymentRequest#getParameters()
     */
    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Get a single parameter.
     */
    protected String getParameter(String key) {
        return parameters.get(key);
    }

    /**
     * Set a single parameter
     */
    protected RequestBase setParameter(String key, String value) throws RuntimeException {
        if (response != null) {
            throw new RuntimeException("Request cannot be modified after it has been sent!");
        }
        parameters.put(key, value);
        return this;
    }

    /**
     * Get connector base
     */
    public ConnectorBase getConnector() {
        return connector;
    }

    /**
     * Get test mode of the request
     */
    public Boolean getTestMode() {
        try {
            return Boolean.valueOf(getParameter("testMode"));
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Set test mode of the request
     */
    public RequestBase setTestMode(Boolean value) {
        return setParameter("testMode", value.toString());
    }

    /**
     * Get the credit card.
     */
    public CreditCard getCard() {
        return card;
    }

    /**
     * Set the credit card.
     */
    public RequestBase setCard(CreditCard card) {
        this.card = card;
        return this;
    }

    /**
     * Set the credit card with parameters.
     * @param parameters
     */
    public RequestBase setCard(Map<String, String> parameters) {
        this.card = new CreditCard(parameters);
        return this;
    }

    /**
     * Get the card token.
     */
    public String getToken() {
        return getParameter("token");
    }

    /**
     * Set the card token.
     */
    public RequestBase setToken(String value) {
        return setParameter("token", value);
    }

    /**
     * Get the card reference.
     */
    public String getCardReference() {
        return getParameter("cardReference");
    }

    /**
     * Set the card reference.
     */
    public RequestBase setCardReference(String value) {
        return setParameter("cardReference", value);
    }

    /**
     * Validates and returns the formated amount.
     */
    public String getAmount() throws InvalidRequestException {
        String amount = getParameter("amount");
        if (amount != null) {
            if (getCurrencyDecimalPlaces() > 0) {
                if (Helper.isInteger(amount)) {
                    throw new InvalidRequestException("Please specify amount as a float string, with decimal places (e.g. \'10.00\' to represent $10.00).");
                }
            }
            Float floatAmount = Helper.toFloat(amount);
            if (!negativeAmountAllowed && floatAmount < 0) {
                throw new InvalidRequestException("A negative amount is not allowed.");
            }
            
            if (!zeroAmountAllowed && floatAmount == 0.0f) {
                throw new InvalidRequestException("A zero amount is not allowed.");
            }
            
            Integer decimalCount = Helper.decimalCount(amount);
            if (decimalCount > getCurrencyDecimalPlaces()) {
                throw new InvalidRequestException("Amount precision is too high for currency.");
            }
        }
        return amount;
    }

    /**
     * Sets the payment amount.
     */
    public RequestBase setAmount(String value) {
        return setParameter("amount", value);
    }

    /**
     * Sets the payment amount.
     */
    public RequestBase setAmount(Float value) {
        return setParameter("amount", value.toString());
    }

    /**
     * Get the payment amount as an integer.
     */
    public Integer getAmountInteger() throws InvalidRequestException {
        return Math.round(Helper.toFloat(getAmount()) * getCurrencyDecimalFactor());
    }

    /**
     * Get the payment currency code.
     */
    public String getCurrency() {
        return getParameter("currency");
    }

    /**
     * Set the payment currency code.
     */
    public RequestBase setCurrency(String value) {
        return setParameter("currency", value.toUpperCase());
    }

    /**
     * Get the payment currency number.
     */
    public String getCurrencyNumeric() {
        Currency currency = Currency.find(getCurrency());
        if (currency != null) {
            return currency.getNumeric();
        }
        return null;
    }

    /**
     * Get the number of decimal places in the payment currency.
     */
    public Integer getCurrencyDecimalPlaces() {
        String sCurrency = getCurrency();
        if (sCurrency != null && sCurrency.length() > 0) {
            Currency currency = Currency.find(sCurrency);
            if (currency != null) {
                return currency.getDecimals();
            }
        }
        return 2;
    }

    private Integer getCurrencyDecimalFactor() {
        return (int) Math.pow(10, getCurrencyDecimalPlaces());
    }

    /**
     * Get the request description.
     */
    public String getDescription() {
        return getParameter("description");
    }

    /**
     * Set the request description.
     */
    public RequestBase setDescription(String value) {
        return setParameter("description", value);
    }

    /**
     * Get the transaction ID.
     * 
     * The transaction ID is the identifier generated by the merchant website.
     */
    public String getTransactionId() {
        return getParameter("transactionId");
    }

    /**
     * Sets the transaction ID.
     */
    public RequestBase setTransactionId(String value) {
        return setParameter("transactionId", value);
    }

    /**
     * Get the transaction reference.
     * 
     * The transaction reference is the identifier generated by the remote payment gateway.
     */
    public String getTransactionReference() {
        return getParameter("transactionReference");
    }

    /**
     * Sets the transaction reference.
     */
    public RequestBase setTransactionReference(String value) {
        return setParameter("transactionReference", value);
    }

    /**
     * Get request items
     */
    public List<PaymentItem> getItems () {
        return items;
    }

    /**
     * Set request items
     */
    public RequestBase setItems (List<PaymentItem> items) {
        this.items = items;
        return this;
    }

    /**
     * Get the client IP address.
     */
    public String getClientIp() {
        return getParameter("clientIp");
    }

    /**
     * Set the client IP address.
     */
    public RequestBase setClientIp(String value) {
        return setParameter("clientIp", value);
    }

    /**
     * Get the request return URL.
     */
    public String getReturnUrl() {
        return getParameter("returnUrl");
    }

    /**
     * Set the request return URL.
     */
    public RequestBase setReturnUrl(String value) {
        return setParameter("returnUrl", value);
    }

    /**
     * Get the request cancel URL.
     */
    public String getCancelUrl() {
        return getParameter("cancelUrl");
    }

    /**
     * Set the request cancel URL.
     */
    public RequestBase setCancelUrl(String value) {
        return setParameter("cancelUrl", value);
    }

    /**
     * Get the request notify URL.
     */
    public String getNotifyUrl() {
        return getParameter("notifyUrl");
    }

    /**
     * Set the request notify URL.
     */
    public RequestBase setNotifyUrl(String value) {
        return setParameter("notifyUrl", value);
    }

    /**
     * Get the payment issuer.
     */
    public String getIssuer() {
        return getParameter("issuer");
    }

    /**
     * Set the payment issuer.
     */
    public RequestBase setIssuer(String value) {
        return setParameter("issuer", value);
    }

    /**
     * Get the payment method.
     */
    public String getPaymentMethod() {
        return getParameter("paymentMethod");
    }

    /**
     * Set the payment method.
     */
    public RequestBase setPaymentMethod(String value) {
        return setParameter("paymentMethod", value);
    }

    /**
     * Send the request
     */
    public PaymentResponse send() {
    	Map<String, String> data = getData();
        return send(data);
    }

    /**
     * Get the associated response.
     */
    public PaymentResponse getResponse () {
        if (response == null) {
            throw new RuntimeException("You must call send() before accessing the Response!");
        }
        return response;
    }
}
