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

/**
 * This class defines a payment method to be used in the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class PaymentMethod {

    /**
     * The identifier of this payment method
     */
    protected String id;

    /**
     * The name of this payment method
     */
    protected String name;

    /**
     * Create a new PaymentMethod
     * 
     * @param String id
     * @param String name
     */
    public PaymentMethod(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * The identifier of this payment method
     * 
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * The name of this payment method
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
}
