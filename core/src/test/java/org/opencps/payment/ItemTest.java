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
import org.apache.commons.collections4.map.HashedMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Item.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class ItemTest extends TestCase {

    private Item item;

    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public ItemTest(String testName) {
        super(testName);
        item = new Item(new HashedMap<String, String>());
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(ItemTest.class);
    }

    public void testConstructWithParams() {
        IterableMap<String, String> params = new HashedMap<String, String>();
        params.put("name", "Item Name");
        Item item = new Item(params);

        assertEquals("Item Name", item.getName());
        assertEquals(params, item.getParameters());
    }

    public void testName(){
        item.setName("Item Name");
        assertEquals("Item Name", item.getName());
    }

    public void testDescription() {
        item.setDescription("Item Description");
        assertEquals("Item Description", item.getDescription());
    }

    public void testQuantity() {
        item.setQuantity(10);
        assertSame(10, item.getQuantity());
    }

    public void testPrice() {
        item.setPrice(10.00f);
        assertEquals(10.00f, item.getPrice());
    }
}
