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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.HashedMap;
import org.opencps.payment.api.Connector;

/**
 * This abstract class should be extended by all payment connectors
 * throughout the OpenCPS Payment system.  It enforces implementation of
 * the Connector interface and defines various common attibutes
 * and methods that all connectors should have.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public abstract class ConnectorBase implements Connector {

    protected IterableMap<String, String> parameters;
    
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
        initialize(new HashedMap<String, String>());
    }

    /**
     * Create a new connector instance
     */
    public ConnectorBase(HttpServletRequest servletRequest, HttpServletResponse servletResponse, HttpTransport transport) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.transport = transport;
        initialize(new HashedMap<String, String>());
    }
    
    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.Connector#initialize()
     */
    @Override
    public Connector initialize(IterableMap<String, String> parameters) {
        for (Map.Entry<String, String> entry: parameters.entrySet()) {
            this.parameters.put(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.Connector#getParameters()
     */
    @Override
    public IterableMap<String, String> getParameters() {
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
    protected abstract RequestBase createRequest(IterableMap<String, String> parameters);
}
