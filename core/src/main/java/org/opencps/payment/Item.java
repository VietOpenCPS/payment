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

import java.util.HashMap;
import java.util.Map;
import org.opencps.payment.api.PaymentItem;

/**
 * This class defines a single cart item in the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class Item implements PaymentItem {

    protected Map<String, String> parameters;

    /**
     * Create a new item
     */
    public Item() {
    	parameters = new HashMap<String, String>();
    }
    
    /**
     * Create a new item with the specified parameters
     */
    public Item(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Get all parameters.
     */
    public Map<String, String> getParameters() {
        return parameters;
    }
    
    /**
     * Get one parameter.
     */
    protected String getParameter(String key) {
        return parameters.get(key);
    }

    /**
     * Set one parameter.
     */
    protected PaymentItem setParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.ItemInterface#getName()
     */
    @Override
    public String getName() {
        return getParameter("name");
    }

    /**
     * Set the item name
     */
    public PaymentItem setName(String value) {
        return setParameter("name", value);
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.ItemInterface#getDescription()
     */
    @Override
    public String getDescription() {
        return getParameter("description");
    }

    /**
     * Set the item description
     */
    public PaymentItem setDescription(String value) {
        return setParameter("description", value);
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.ItemInterface#getQuantity()
     */
    @Override
    public Integer getQuantity() {
        try {
            return Integer.valueOf(getParameter("quantity"));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Set the item quantity
     */
    public PaymentItem setQuantity(Integer value) {
        return setParameter("quantity", value.toString());
    }

    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.ItemInterface#getPrice()
     */
    @Override
    public Float getPrice() {
        try {
            return Float.valueOf(getParameter("price"));
        }
        catch (NumberFormatException ex) 
        {
            return null;
        }
    }

    /**
     * Set the item price
     */
    public PaymentItem setPrice(Float value) {
        return setParameter("price", value.toString());
    }
}
