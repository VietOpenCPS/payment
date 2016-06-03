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

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencps.payment.api.PaymentConnector;

/**
 * This abstract class should be extended by all payment connectors
 * throughout the OpenCPS Payment system.  It enforces implementation of
 * the Connector interface and defines various common attibutes
 * and methods that all connectors should have.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public abstract class ConnectorBase implements PaymentConnector {

    protected Map<String, String> parameters;
    
    /**
     * The HTTP transport object.
     */
    protected HttpTransport transport;

    /**
     * The HTTP servlet request object.
     */
    protected HttpServletRequest servletRequest;
    
    /**
     * The HTTP servlet response object.
     */
    protected HttpServletResponse servletResponse;

    /**
     * Create a new connector instance
     */
    public ConnectorBase(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        transport = new ApacheHttpTransport();
        initialize(new HashMap<String, String>());
    }

    /**
     * Create a new connector instance
     */
    public ConnectorBase(HttpServletRequest servletRequest, HttpServletResponse servletResponse, HttpTransport transport) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.transport = transport;
        initialize(new HashMap<String, String>());
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#initialize()
     */
    @Override
    public PaymentConnector initialize(Map<String, String> parameters) {
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
     * @see org.opencps.payment.api.PaymentConnector#getParameters()
     */
    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Get a parameter
     * 
     * @param String key
     */
    public String getParameter(String key) {
        return parameters.get(key);
    }

    /**
     * Set a parameter
     * 
     * @param String key
     * @param String value
     */
    public ConnectorBase setParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    /**
     * Get test mode of the connector
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
     * Set test mode of the connector
     */
    public ConnectorBase setTestMode(Boolean value) {
        setParameter("testMode", value.toString());
        return this;
    }

    /**
     * Get currency of the connector
     * @return String
     */
    public String getCurrency() {
        return getParameter("currency").toUpperCase();
    }

    /**
     * Set currency of the connector
     * @param value
     * @return String
     */
    public ConnectorBase setCurrency(String value) {
        setParameter("currency", value);
        return this;
    }

    /**
     * Get http servlet request
     * @return HttpServletRequest
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * Get http servlet response
     * @return HttpServletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * Get http transport
     * @return HttpTransport
     */
    public HttpTransport getHttpTransport() {
        return transport;
    }

    /**
     * Create and initialize a request object
     * 
     * @param parameters
     * @return RequestBase
     */
    protected <T extends RequestBase> T createRequest(Class<T> type, Map<String, String> parameters) {
        try {
            T request = type.getDeclaredConstructor(type).newInstance(this);
            request.initialize(parameters);
            return request;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an authorize request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase authorize(Map<String, String> parameters) {
        return doAuthorize(parameters);
    }

    protected abstract RequestBase doAuthorize(Map<String, String> parameters);

    /**
     * Create a complete authorize request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase completeAuthorize(Map<String, String> parameter) {
        return doCompleteAuthorize(parameter);
    }

    protected abstract RequestBase doCompleteAuthorize(Map<String, String> parameters);

    /**
     * Create a capture request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase capture(Map<String, String> parameters) {
        return doCapture(parameters);
    }

    protected abstract RequestBase doCapture(Map<String, String> parameters);

    /**
     * Create a authorize & purchase request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase purchase(Map<String, String> parameters) {
        return doPurchase(parameters);
    }

    protected abstract RequestBase doPurchase(Map<String, String> parameters);

    /**
     * Create a complete capture request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase completePurchase(Map<String, String> parameters) {
        return doCompletePurchase(parameters);
    }

    protected abstract RequestBase doCompletePurchase(Map<String, String> parameters);

    /**
     * Create a refund request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase refund(Map<String, String> parameters) {
        return doRefund(parameters);
    }

    protected abstract RequestBase doRefund(Map<String, String> parameters);

    /**
     * Create a revert(void) request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase revert(Map<String, String> parameters) {
        return doRevert(parameters);
    }

    protected abstract RequestBase doRevert(Map<String, String> parameters);

    /**
     * Create an accept notification request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase acceptNotification(Map<String, String> parameters) {
        return doAcceptNotification(parameters);
    }

    protected abstract RequestBase doAcceptNotification(Map<String, String> parameters);

    /**
     * Create a create card request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase createCard(Map<String, String> parameters) {
        return doCreateCard(parameters);
    }

    protected abstract RequestBase doCreateCard(Map<String, String> parameters);

    /**
     * Create a update card request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase updateCard(Map<String, String> parameters) {
        return doCreateCard(parameters);
    }

    protected abstract RequestBase doUpdateCard(Map<String, String> parameters);

    /**
     * Create a delete card request
     * @param parameters gateway-specific data, that will be sent to the Gateway service.
     * @return The request from the connector.
     */
    public RequestBase deleteCard(Map<String, String> parameters) {
        return doCreateCard(parameters);
    }

    protected abstract RequestBase doDeleteCard(Map<String, String> parameters);
}
