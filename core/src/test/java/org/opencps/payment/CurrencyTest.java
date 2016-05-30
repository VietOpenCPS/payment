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
 * Unit test for Currency.
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class CurrencyTest extends TestCase {

    /**
     * Create the test case
     */
    public CurrencyTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(CurrencyTest.class);
    }

    public void testFind() {
        Currency currency = Currency.find("USD");
        assertEquals("USD", currency.getCode());
        assertEquals("840", currency.getNumeric());
        assertEquals(new Integer(2), currency.getDecimals());
    }

    public void testFindLowercase() {
        Currency currency = Currency.find("vnd");
        assertEquals("VND", currency.getCode());
        assertEquals("704", currency.getNumeric());
        assertEquals(new Integer(0), currency.getDecimals());
    }

    public void testFindReturnsNull() {
        Currency currency = Currency.find("ABC");
        assertNull(currency);
    }

}
