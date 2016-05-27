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
 * Unit test for simple Issuer.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 *
 */
public class IssuerTest extends TestCase {

    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public IssuerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(Issuer.class);
    }
    
    public void testIssuer() {
    	Issuer issuer = new Issuer("OpenCPS Id","OpenCPS Issuer");
        assertEquals("OpenCPS Id", issuer.getId());
        assertEquals("OpenCPS Issuer", issuer.getName());
    }
    
    public void testIssuerWithPayment() {
    	Issuer issuer = new Issuer("OpenCPS Id","OpenCPS Issuer", "OpenCPS Payment Method");
        assertEquals("OpenCPS Id", issuer.getId());
        assertEquals("OpenCPS Issuer", issuer.getName());
        assertEquals("OpenCPS Payment Method", issuer.getPaymentMethod());
    }

}
