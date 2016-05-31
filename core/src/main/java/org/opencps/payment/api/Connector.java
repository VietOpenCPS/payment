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

import org.apache.commons.collections4.IterableMap;

/**
 * Payment connector interface
 * 
 * This interface class defines the standard functions that any
 * payment connector needs to define.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public interface Connector {

    /**
     * Get connector display name
     * This can be used by carts to get the display name for each connector.
     */
    public String getName();

    /**
     * Get connector short name
     * This name can be used with ConnectorFactory as an alias of the connector class,
     * to create new instances of this connector.
     */
    public String getShortName();

    /**
     * Initialize this connector with default parameters
     * 
     * @param capacity the initial capacity
     * @return Connector instance
     */
    public Connector initialize(IterableMap<String, String> parameters);

    /**
     * Get all connector parameters
     * 
     * @return IterableMap
     */
    public IterableMap<String, String> getParameters();

    /**
     * Supports authorize
     * @return Boolean
     */
    public Boolean supportsAuthorize();

    /**
     * Supports complete authorize
     * @return Boolean
     */
    public Boolean supportsCompleteAuthorize();

    /**
     * Supports capture
     * @return Boolean
     */
    public Boolean supportsCapture();

    /**
     * Supports purchase
     * @return Boolean
     */
    public Boolean supportsPurchase();

    /**
     * Supports complete purchase
     * @return Boolean
     */
    public Boolean supportsCompletePurchase();

    /**
     * Supports refund
     * @return Boolean
     */
    public Boolean supportsRefund();

    /**
     * Supports void
     * @return Boolean
     */
    public Boolean supportsVoid();

    /**
     * Supports accept notification
     * @return Boolean
     */
    public Boolean supportsAcceptNotification();

    /**
     * Supports create card
     * @return Boolean
     */
    public Boolean supportsCreateCard();

    /**
     * Supports delete card
     * @return Boolean
     */
    public Boolean supportsDeleteCard();

    /**
     * Supports update card
     * @return Boolean
     */
    public Boolean supportsUpdateCard();
}
