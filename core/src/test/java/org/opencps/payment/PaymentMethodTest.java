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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple PaymentMethod.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class PaymentMethodTest extends TestCase {

    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public PaymentMethodTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(PaymentMethodTest.class);
    }

    public void testPaymentMethod(){
        PaymentMethod paymentMethod = new PaymentMethod("Payment Method Id", "Payment Method Name");

        assertEquals("Payment Method Id", paymentMethod.getId());
        assertEquals("Payment Method Name", paymentMethod.getName());
    }
}
