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
 * This class abstracts some functionality around card issuers used in the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class Issuer {

    /**
     * The identifier of the issuer.
     */
    protected String id;

    /**
     * The full name of the issuer.
     */
    protected String name;

    /**
     * The ID of a payment method that the issuer belongs to.
     */
    protected String paymentMethod;

    /**
     * Create a new Issuer
     * 
     * @param String id
     * @param String name
     */
    public Issuer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Create a new Issuer
     * 
     * @param String id
     * @param String name
     * @param String paymentMethod
     */
    public Issuer(String id, String name, String paymentMethod) {
        this.id = id;
        this.name = name;
        this.paymentMethod = paymentMethod;
    }

    /**
     * The identifier of this issuer
     * 
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * The name of this issuer
     * 
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * The ID of a payment method this issuer belongs to
     * 
     * @return String
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
}
