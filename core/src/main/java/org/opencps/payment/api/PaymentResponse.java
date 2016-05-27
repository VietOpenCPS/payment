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

/**
 * This interface class defines the standard functions that any OpenCPS Payment response
 * interface needs to be able to provide.  It is an extension of MessageInterface.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public interface PaymentResponse extends PaymentMessage {

    /**
     * Get the original request which generated this response
     * 
     * @return RequestInterface
     */
    public PaymentRequest getRequest();

    /**
     * Is the response successful?
     *
     * @return Boolean
     */
    public Boolean isSuccessful();

    /**
     * Does the response require a redirect?
     * 
     * @return Boolean
     */
    public Boolean isRedirect();

    /**
     * Is the transaction cancelled by the user?
     * 
     * @return Boolean
     */
    public Boolean isCancelled();

    /**
     * Get response message from the payment gateway
     * 
     * @return String
     */
    public String getMessage();

    /**
     * Get response code from the payment gateway
     * 
     * @return String
     */
    public String getCode();

    /**
     * Get reference provided by the gateway to represent this transaction
     * 
     * @return String
     */
    public String getTransactionReference();
}
