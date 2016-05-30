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

import java.util.ArrayList;
import java.util.List;

/**
 * This class abstracts certain functionality around currency objects,
 * currency codes and currency numbers relating to global currencies used
 * in the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class Currency {

    private String code;
    private String numeric;
    private Integer decimals;

    /**
     * Create a new Currency object
     * 
     * @param String code
     * @param String numeric
     * @param Integer decimals
     */
    public Currency(String code, String numeric, Integer decimals) {
        this.code = code;
        this.numeric = numeric;
        this.decimals = decimals;
    }

    /**
     * Get the three letter code for the currency
     * 
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the numeric code for this currency
     * 
     * @return String
     */
    public String getNumeric() {
        return numeric;
    }

    /**
     * Get the number of decimal places for this currency
     * 
     * @return Integer
     */
    public Integer getDecimals() {
        return decimals;
    }

    /**
     * Find a specific currency
     * 
     * @param  String code The three letter currency code
     */
    public static Currency find(String code) {
        code = code.toUpperCase();
        List<Currency> alls = alls();
        for (Currency item : alls) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Get all supported currencies
     */
    public static List<Currency> alls() {
        List<Currency> list = new ArrayList<Currency>();

        list.add(new Currency("ARS", "032", 2));
        list.add(new Currency("AUD", "036", 2));
        list.add(new Currency("BOB", "068", 2));
        list.add(new Currency("BRL", "986", 2));
        list.add(new Currency("CAD", "124", 2));
        list.add(new Currency("CHF", "756", 2));
        list.add(new Currency("CLP", "152", 0));
        list.add(new Currency("CNY", "156", 2));
        list.add(new Currency("COP", "170", 2));
        list.add(new Currency("CRC", "188", 2));
        list.add(new Currency("CZK", "203", 2));
        list.add(new Currency("DKK", "208", 2));
        list.add(new Currency("DOP", "214", 2));
        list.add(new Currency("EUR", "978", 2));
        list.add(new Currency("FJD", "242", 2));
        list.add(new Currency("GBP", "826", 2));
        list.add(new Currency("GTQ", "320", 2));
        list.add(new Currency("HKD", "344", 2));
        list.add(new Currency("HUF", "348", 2));
        list.add(new Currency("ILS", "376", 2));
        list.add(new Currency("INR", "356", 2));
        list.add(new Currency("JPY", "392", 0));
        list.add(new Currency("KRW", "410", 0));
        list.add(new Currency("LAK", "418", 0));
        list.add(new Currency("MXN", "484", 2));
        list.add(new Currency("MYR", "458", 2));
        list.add(new Currency("NOK", "578", 2));
        list.add(new Currency("NZD", "554", 2));
        list.add(new Currency("OMR", "512", 2));
        list.add(new Currency("PEN", "604", 2));
        list.add(new Currency("PGK", "598", 2));
        list.add(new Currency("PHP", "608", 2));
        list.add(new Currency("PLN", "985", 2));
        list.add(new Currency("PYG", "600", 0));
        list.add(new Currency("SBD", "090", 2));
        list.add(new Currency("SEK", "752", 2));
        list.add(new Currency("SGD", "702", 2));
        list.add(new Currency("THB", "764", 2));
        list.add(new Currency("TOP", "776", 2));
        list.add(new Currency("TRY", "949", 2));
        list.add(new Currency("TWD", "901", 2));
        list.add(new Currency("USD", "840", 2));
        list.add(new Currency("UYU", "858", 2));
        list.add(new Currency("VEF", "937", 2));
        list.add(new Currency("VND", "704", 0));
        list.add(new Currency("VUV", "548", 0));
        list.add(new Currency("WST", "882", 2));
        list.add(new Currency("ZAR", "710", 2));

        return list;
    }
}
