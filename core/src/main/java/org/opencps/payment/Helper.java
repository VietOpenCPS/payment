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
 * This class defines various static utility functions that are in use
 * throughout the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class Helper {

    /**
     * Check a string is an integer number
     */
    public static Boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }
        catch(NumberFormatException e) { 
            return false;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    
    /**
     * Check a string is a number
     */
    public static Boolean isNumeric(String number) {
        try {
            Double.parseDouble(number);
            return true;
        }
        catch(NumberFormatException e) { 
            return false;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    
    /**
     * Convert string to float number
     */
    public static Float toFloat(String number) {
        try {
            return Float.valueOf(number);
        }
        catch (NumberFormatException ex) 
        {
            return 0.0f;
        }
    }
    
    /**
     * Cont decimal number in a numeric string
     */
    public static Integer decimalCount(String number) {
        if (Helper.isNumeric(number)) {
            int integerPlace = number.indexOf('.');
            return number.length() - integerPlace - 1;
        }
        return 0;
    }
    
    /**
     * Format a number to string
     */
    public static String formatNumber(Float number, Integer decimals) {
        if (decimals < 1) {
            return number.toString();
        }
        else {
            String format = "%." + decimals.toString() + "f";
            return String.format(format, number);
        }
    }
}
