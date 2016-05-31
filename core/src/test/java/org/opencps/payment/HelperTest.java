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
 * Unit test for Helper.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class HelperTest extends TestCase {

    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public HelperTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(HelperTest.class);
    }
    
    public void testIsInteger() {
        assertTrue(Helper.isInteger("100"));
        assertFalse(Helper.isInteger("10.0"));
        assertFalse(Helper.isInteger("abc"));
    }
    
    public void testIsNumeric() {
        assertTrue(Helper.isNumeric("100"));
        assertTrue(Helper.isNumeric("10.0"));
        assertFalse(Helper.isInteger("abc"));
    }
    
    public void testToFloat() {
        assertEquals(123f, Helper.toFloat("123"));
        assertEquals(1.23f, Helper.toFloat("1.23"));
    }
    
    public void testDecimalCount() {
        assertEquals(new Integer(1), Helper.decimalCount("1.2"));
        assertEquals(new Integer(3), Helper.decimalCount("1.234"));
        assertEquals(new Integer(0), Helper.decimalCount("123"));
        assertEquals(new Integer(0), Helper.decimalCount("abc"));
    }
    
    public void testFormatNumber() {
        assertEquals("1.23", Helper.formatNumber(1.23f, 2));
        assertEquals("1.2345", Helper.formatNumber(1.2345f, 4));
        assertEquals("1.23", Helper.formatNumber(1.2345f, 2));
    }
}
