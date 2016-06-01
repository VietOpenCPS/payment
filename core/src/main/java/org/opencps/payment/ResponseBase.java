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

import org.apache.commons.collections4.IterableMap;
import org.opencps.payment.api.PaymentRequest;
import org.opencps.payment.api.PaymentResponse;

/**
 * This abstract class implements PaymentResponse and defines a basic
 * set of functions that all OpenCPS Payment Requests are intended to include.
 * 
 * Objects of this class or a subclass are usually created in the Request
 * object (subclass of RequestBase) as the return parameters from the
 * send() method.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public abstract class ResponseBase implements PaymentResponse {

    /**
     * The embodied request object.
     */
    protected RequestBase request;
    
    protected IterableMap<String, String> data;
    
    /**
     * Constructor
     * 
     * @param PaymentRequest
     * @param String
     */
    public ResponseBase(RequestBase request, IterableMap<String, String> data) {
        this.request = request;
        this.data = data;
    }

    public PaymentRequest getRequest() {
        return request;
    }
    
    public Boolean isPending() {
        return false;
    }
    
    public Boolean isRedirect() {
        return false;
    }
    
    public Boolean isTransparentRedirect() {
        return false;
    }
    
    public Boolean isCancelled() {
        return false;
    }
    
    public IterableMap<String, String> getData() {
        return data;
    }
}
