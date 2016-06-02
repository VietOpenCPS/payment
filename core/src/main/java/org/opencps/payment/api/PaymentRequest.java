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
package org.opencps.payment.api;

import java.util.Map;

/**
 * This interface class defines the standard functions that any OpenCPS Payment request
 * interface needs to be able to provide.  It is an extension of MessageInterface.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public interface PaymentRequest extends PaymentMessage {

    /**
     * Initialize request with parameters
     * 
     * @param Map The parameters to send
     */
    public PaymentRequest initialize(Map<String, String> parameters) throws RuntimeException;

    /**
     * Get all request parameters
     * 
     * @return Map
     */
    public Map<String, String> getParameters();

    /**
     * Get the response to this request (if the request has been sent)
     * 
     * @return ResponseInterface
     */
    public PaymentResponse getResponse();

    /**
     * Send the request
     * 
     * @return ResponseInterface
     */
    public PaymentResponse send();

    /**
     * Send the request with specified data
     * 
     * @return ResponseInterface
     */
    public PaymentResponse send(Map<String, String> data);

    /**
     * Send the request with specified data
     * 
     * @return ResponseInterface
     */
    public PaymentResponse send(String data);
}
