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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.HashedMap;
import org.opencps.payment.exception.InvalidCreditCardException;

/**
 * This class defines and abstracts all of the credit card types used
 * throughout the OpenCPS Payment system.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class CreditCard {

    public static final String BRAND_VISA = "visa";
    public static final String BRAND_MASTERCARD = "mastercard";
    public static final String BRAND_DISCOVER = "discover";
    public static final String BRAND_AMEX = "amex";
    public static final String BRAND_DINERS_CLUB = "diners_club";
    public static final String BRAND_JCB = "jcb";
    public static final String BRAND_SWITCH = "switch";
    public static final String BRAND_SOLO = "solo";
    public static final String BRAND_DANKORT = "dankort";
    public static final String BRAND_MAESTRO = "maestro";
    public static final String BRAND_FORBRUGSFORENINGEN = "forbrugsforeningen";
    public static final String BRAND_LASER = "laser";

    /**
     * All known/supported card brands, and a regular expression to match them.
     */
    protected static IterableMap<String, String> supportedCards = new HashedMap<String, String>();
    static {
        supportedCards.put(BRAND_VISA, "^4\\d{12}(\\d{3})?$");
        supportedCards.put(BRAND_MASTERCARD, "^(5[1-5]\\d{4}|677189)\\d{10}$|^(222[1-9]|2[3-6]\\d{2}|27[0-1]\\d|2720)\\d{12}$");
        supportedCards.put(BRAND_DISCOVER, "^(6011|65\\d{2}|64[4-9]\\d)\\d{12}|(62\\d{14})$");
        supportedCards.put(BRAND_AMEX, "^3[47]\\d{13}$");
        supportedCards.put(BRAND_DINERS_CLUB, "^3(0[0-5]|[68]\\d)\\d{11}$");
        supportedCards.put(BRAND_JCB, "^35(28|29|[3-8]\\d)\\d{12}$");
        supportedCards.put(BRAND_SWITCH, "^6759\\d{12}(\\d{2,3})?$");
        supportedCards.put(BRAND_SOLO, "^6767\\d{12}(\\d{2,3})?$");
        supportedCards.put(BRAND_DANKORT, "^5019\\d{12}$");
        supportedCards.put(BRAND_MAESTRO, "^(5[06-8]|6\\d)\\d{10,17}$");
        supportedCards.put(BRAND_FORBRUGSFORENINGEN, "^600722\\d{10}$");
        supportedCards.put(BRAND_LASER, "^(6304|6706|6709|6771(?!89))\\d{8}(\\d{4}|\\d{6,7})?$");
    }
    
    /**
     * Internal storage of all of the card parameters.
     */
    protected IterableMap<String, String> parameters;
    
    /**
     * Create a new CreditCard
     */
    public CreditCard() {
        parameters = new HashedMap<String, String>();
    }
    
    /**
     * Create a new CreditCard object using the specified parameters
     */
    public CreditCard(IterableMap<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * All known/supported card brands, and a regular expression to match them.
     */
    public static IterableMap<String, String> getSupportedBrands() {
        return supportedCards;
    }
    
    /*
     * Set a custom supported card brand with a regular expression to match it.
     */
    public static Boolean addSupportedBrand(String name, String expression) {
        if (supportedCards.get(name) != null) {
            return false;
        }
        else {
            supportedCards.put(name, expression);
            return true;
        }
    }
    
    /**
     * Validate a card number according to the Luhn algorithm.
     */
    public static Boolean luhnCheck(String number) {
        int sum = 0;
        int length = number.length();
        boolean alternate = false;
        for (int i = length - 1; i >= 0; i--) {
            int n = 0;
            try {
                n = Integer.parseInt(number.substring(i, i + 1));
            }
            catch(NumberFormatException e) {
                return false;
            }
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    /**
     * Get all parameters.
     */
    public IterableMap<String, String> getParameters() {
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
    protected CreditCard setParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    /**
     * Validate this credit card. If the card is invalid, InvalidCreditCardException is thrown.
     */
    public void validate() throws InvalidCreditCardException {
        String number = getNumber();
        if (number == null || number.length() == 0) {
            throw new InvalidCreditCardException("Card number is required");
        }
        Integer expiryMonth = getExpiryMonth();
        if (expiryMonth == null || expiryMonth == 0) {
            throw new InvalidCreditCardException("Expiry month is required");
        }
        Integer expiryYear = getExpiryYear();
        if (expiryYear == null || expiryYear == 0) {
            throw new InvalidCreditCardException("Expiry year is required");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, getExpiryMonth() - 1);
        calendar.set(Calendar.YEAR, getExpiryYear());
        Date expiryDate = calendar.getTime();
        int dateMargin = expiryDate.compareTo(new Date());
        if (dateMargin < 0) {
            throw new InvalidCreditCardException("Card has expired");
        }

        if (!luhnCheck(number)) {
            throw new InvalidCreditCardException("Card number is invalid");
        }
        if (number.length() < 9 || number.length() > 19) {
            throw new InvalidCreditCardException("Card number should have 12 to 19 digits");
        }
    }
    
    /**
     * Get card title.
     */
    public String getTitle() {
        return getBillingTitle();
    }

    /**
     * Set card title.
     */
    public CreditCard setTitle(String value) {
        setBillingTitle(value).setShippingTitle(value);
        return this;
    }

    /**
     * Get card first name.
     */
    public String getFirstName() {
        return getBillingFirstName();
    }

    /**
     * Set card first name.
     */
    public CreditCard setFirstName(String value) {
        return setBillingFirstName(value).setShippingFirstName(value);
    }

    /**
     * Get card last name.
     */
    public String getLastName() {
        return getBillingLastName();
    }

    /**
     * Set card last name.
     */
    public CreditCard setLastName(String value) {
        return setBillingLastName(value).setShippingLastName(value);
    }

    /**
     * Get card name.
     */
    public String getName() {
        return getBillingName();
    }

    /**
     * Set card name.
     */
    public CreditCard setName(String value) {
        return setBillingName(value).setShippingName(value);
    }

    /**
     * Get card number.
     */
    public String getNumber() {
        return getParameter("number");
    }

    /**
     * Set card number.
     */
    public CreditCard setNumber(String value) {
        if (value != null && value.length() > 0) {
            return setParameter("number", value.replaceAll("\\D", ""));
        }
        return setParameter("number", null);
    }
    
    /**
     * Get the last 4 digits of the card number.
     * @return String
     */
    public String getNumberLastFour() {
        String number = getNumber();
        if (number != null && number.length() > 3) {
            return number.substring(number.length() - 4);
        }
        return null;
    }
    
    /**
     * Returns a masked credit card number with only the last 4 chars visible
     */
    public String getNumberMasked(char mask) {
        StringBuilder out = new StringBuilder();
        String number = getNumber();
        int maskLength = number.length() - 4;
        for (int i = 0; i < maskLength; i ++) {
            out.append(mask);
        }
        out.append(getNumberLastFour());
        return out.toString();
    }
    
    /**
     * Get credit card brand
     */
    public String getBrand() {
        String number = getNumber();
        if (number != null && number.length() > 0) {
            for (Map.Entry<String, String> entry: supportedCards.entrySet()) {
                if (number.matches(entry.getValue())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Get the card expiry month.
     */
    public Integer getExpiryMonth() {
        try {
            return Integer.valueOf(getParameter("expiryMonth"));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Sets the card expiry month.
     */
    public CreditCard setExpiryMonth(Integer value) {
        return setParameter("expiryMonth", value.toString());
    }

    /**
     * Get the card expiry year.
     */
    public Integer getExpiryYear() {
        try {
            return Integer.valueOf(getParameter("expiryYear"));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Sets the card expiry year.
     */
    public CreditCard setExpiryYear(Integer value) {
        return setParameter("expiryYear", value.toString());
    }
    
    /**
     * Get the card expiry date
     * @return String
     */
    public String getExpiryDate(String format) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, getExpiryYear());
        calendar.set(Calendar.MONTH, getExpiryMonth() - 1);
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * Get the card start month.
     */
    public Integer getStartMonth() {
        try {
            return Integer.valueOf(getParameter("startMonth"));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Sets the card start month.
     */
    public CreditCard setStartMonth(Integer value) {
        return setParameter("startMonth", value.toString());
    }

    /**
     * Get the card start year.
     */
    public Integer getStartYear() {
        try {
            return Integer.valueOf(getParameter("startYear"));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Sets the card start year.
     */
    public CreditCard setStartYear(Integer value) {
        return setParameter("startYear", value.toString());
    }
    
    /**
     * Get the card start date
     * @return String
     */
    public String getStartDate(String format) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, getStartYear());
        calendar.set(Calendar.MONTH, getStartMonth() - 1);
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * Get the card CVV.
     */
    public String getCvv() {
        return getParameter("cvv");
    }

    /**
     * Sets the card CVV.
     */
    public CreditCard setCvv(String value) {
        return setParameter("cvv", value);
    }

    /**
     * Get raw data for all tracks on the credit card magnetic strip.
     */
    public String getTracks() {
        return getParameter("tracks");
    }
    
    /**
     * Get raw data for track 1 on the credit card magnetic strip.
     */
    public String getTrack1() {
        String tracks = getTracks();
        Pattern pattern = Pattern.compile("(%?([A-Z])([0-9]{1,19})\\^([^\\^]{2,26})\\^([0-9]{4}|\\^)([0-9]{3}|\\^)?([^\\?]+)?\\??)[\t\n\r ]{0,2}.*");
        Matcher matcher = pattern.matcher(tracks);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Get raw data for track 2 on the credit card magnetic strip.
     */
    public String getTrack2() {
        String tracks = getTracks();
        Pattern pattern = Pattern.compile(".*[\\t\\n\\r ]?(;([0-9]{1,19})=([0-9]{4})([0-9]{3})(.*)\\?).*");
        Matcher matcher = pattern.matcher(tracks);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Sets raw data from all tracks on the credit card magnetic strip.
     * Used by gateways that support card-present transactions.
     */
    public CreditCard setTracks(String value) {
        return setParameter("tracks", value);
    }

    /**
     * Get the card issue number.
     */
    public String getIssueNumber() {
        return getParameter("issueNumber");
    }

    /**
     * Sets the card issue number.
     */
    public CreditCard setIssueNumber(String value) {
        return setParameter("issueNumber", value);
    }

    /**
     * Get the title of the card billing name.
     */
    public String getBillingTitle() {
        return getParameter("billingTitle");
    }

    /**
     * Sets the title of the card billing name.
     */
    public CreditCard setBillingTitle(String value) {
        return setParameter("billingTitle", value);
    }

    /**
     * Get the card billing name.
     */
    public String getBillingName() {
        return getBillingFirstName() + " " + getBillingLastName();
    }
    
    /**
     * Sets the card billing name.
     */
    public CreditCard setBillingName(String value) {
        if (value.contains(" ")) {
            setBillingFirstName(value.substring(0, value.indexOf(" ")));
            setBillingLastName(value.substring(value.indexOf(" ") + 1, value.length()));
        }
        else {
            setBillingFirstName(value);
        }
        return this;
    }

    /**
     * Get the first part of the card billing name.
     */
    public String getBillingFirstName() {
        return getParameter("billingFirstName");
    }

    /**
     * Sets the first part of the card billing name.
     */
    public CreditCard setBillingFirstName(String value) {
        return setParameter("billingFirstName", value);
    }

    /**
     * Get the last part of the card billing name.
     */
    public String getBillingLastName() {
        return getParameter("billingLastName");
    }

    /**
     * Sets the last part of the card billing name.
     */
    public CreditCard setBillingLastName(String value) {
        return setParameter("billingLastName", value);
    }

    /**
     * Get the billing company.
     */
    public String getBillingCompany() {
        return getParameter("billingCompany");
    }

    /**
     * Sets the billing company.
     */
    public CreditCard setBillingCompany(String value) {
        return setParameter("billingCompany", value);
    }

    /**
     * Get the billing address, line 1.
     */
    public String getBillingAddress1() {
        return getParameter("billingAddress1");
    }

    /**
     * Sets the billing address, line 1.
     */
    public CreditCard setBillingAddress1(String value) {
        return setParameter("billingAddress1", value);
    }

    /**
     * Get the billing address, line 2.
     */
    public String getBillingAddress2() {
        return getParameter("billingAddress2");
    }

    /**
     * Sets the billing address, line 2.
     */
    public CreditCard setBillingAddress2(String value) {
        return setParameter("billingAddress2", value);
    }

    /**
     * Get the billing city.
     */
    public String getBillingCity() {
        return getParameter("billingCity");
    }

    /**
     * Sets the billing city.
     */
    public CreditCard setBillingCity(String value) {
        return setParameter("billingCity", value);
    }

    /**
     * Get the billing postcode.
     */
    public String getBillingPostcode() {
        return getParameter("billingPostcode");
    }

    /**
     * Sets the billing postcode.
     */
    public CreditCard setBillingPostcode(String value) {
        return setParameter("billingPostcode", value);
    }

    /**
     * Get the billing state.
     */
    public String getBillingState() {
        return getParameter("billingState");
    }

    /**
     * Sets the billing state.
     */
    public CreditCard setBillingState(String value) {
        return setParameter("billingState", value);
    }

    /**
     * Get the billing country.
     */
    public String getBillingCountry() {
        return getParameter("billingCountry");
    }

    /**
     * Sets the billing country.
     */
    public CreditCard setBillingCountry(String value) {
        return setParameter("billingCountry", value);
    }

    /**
     * Get the billing phone number.
     */
    public String getBillingPhone() {
        return getParameter("billingPhone");
    }

    /**
     * Sets the billing phone number.
     */
    public CreditCard setBillingPhone(String value) {
        return setParameter("billingPhone", value);
    }

    /**
     * Get the billing phone number extension.
     */
    public String getBillingPhoneExtension() {
        return getParameter("billingPhoneExtension");
    }

    /**
     * Sets the billing phone number extension.
     */
    public CreditCard setBillingPhoneExtension(String value) {
        return setParameter("billingPhoneExtension", value);
    }

    /**
     * Get the billing fax number.
     */
    public String getBillingFax() {
        return getParameter("billingFax");
    }

    /**
     * Sets the billing fax number.
     */
    public CreditCard setBillingFax(String value) {
        return setParameter("billingFax", value);
    }

    /**
     * Get the title of the card shipping name.
     */
    public String getShippingTitle() {
        return getParameter("shippingTitle");
    }

    /**
     * Sets the title of the card shipping name.
     */
    public CreditCard setShippingTitle(String value) {
        return setParameter("shippingTitle", value);
    }

    /**
     * Get the card shipping name.
     */
    public String getShippingName() {
        return getShippingFirstName() + " " + getShippingLastName();
    }
    
    /**
     * Sets the card shipping name.
     */
    public CreditCard setShippingName(String value) {
        if (value.contains(" ")) {
            setShippingFirstName(value.substring(0, value.indexOf(" ")));
            setShippingLastName(value.substring(value.indexOf(" ") + 1, value.length()));
        }
        else {
            setShippingFirstName(value);
        }
        return this;
    }
    
    /**
     * Get the first part of the card shipping name.
     */
    public String getShippingFirstName() {
        return getParameter("shippingFirstName");
    }

    /**
     * Sets the first part of the card shipping name.
     */
    public CreditCard setShippingFirstName(String value) {
        return setParameter("shippingFirstName", value);
    }

    /**
     * Get the last part of the card shipping name.
     */
    public String getShippingLastName() {
        return getParameter("shippingLastName");
    }

    /**
     * Sets the last part of the card shipping name.
     */
    public CreditCard setShippingLastName(String value) {
        return setParameter("shippingLastName", value);
    }

    /**
     * Get the shipping company.
     */
    public String getShippingCompany() {
        return getParameter("shippingCompany");
    }

    /**
     * Sets the shipping company.
     */
    public CreditCard setShippingCompany(String value) {
        return setParameter("shippingCompany", value);
    }

    /**
     * Get the shipping address, line 1.
     */
    public String getShippingAddress1() {
        return getParameter("shippingAddress1");
    }

    /**
     * Sets the shipping address, line 1.
     */
    public CreditCard setShippingAddress1(String value) {
        return setParameter("shippingAddress1", value);
    }

    /**
     * Get the shipping address, line 2.
     */
    public String getShippingAddress2() {
        return getParameter("shippingAddress2");
    }

    /**
     * Sets the shipping address, line 2.
     */
    public CreditCard setShippingAddress2(String value) {
        return setParameter("shippingAddress2", value);
    }

    /**
     * Get the shipping city.
     */
    public String getShippingCity() {
        return getParameter("shippingCity");
    }

    /**
     * Sets the shipping city.
     */
    public CreditCard setShippingCity(String value) {
        return setParameter("shippingCity", value);
    }

    /**
     * Get the shipping postcode.
     */
    public String getShippingPostcode() {
        return getParameter("shippingPostcode");
    }

    /**
     * Sets the shipping postcode.
     */
    public CreditCard setShippingPostcode(String value) {
        return setParameter("shippingPostcode", value);
    }

    /**
     * Get the shipping state.
     */
    public String getShippingState() {
        return getParameter("shippingState");
    }

    /*
     * Sets the shipping state.
     */
    public CreditCard setShippingState(String value) {
        return setParameter("shippingState", value);
    }

    /**
     * Get the shipping country.
     */
    public String getShippingCountry() {
        return getParameter("shippingCountry");
    }

    /**
     * Sets the shipping country.
     */
    public CreditCard setShippingCountry(String value) {
        return setParameter("shippingCountry", value);
    }

    /**
     * Get the shipping phone number.
     */
    public String getShippingPhone() {
        return getParameter("shippingPhone");
    }

    /**
     * Sets the shipping phone number.
     */
    public CreditCard setShippingPhone(String value) {
        return setParameter("shippingPhone", value);
    }

    /**
     * Get the shipping phone number extension.
     */
    public String getShippingPhoneExtension() {
        return getParameter("shippingPhoneExtension");
    }

    /**
     * Sets the shipping phone number extension.
     */
    public CreditCard setShippingPhoneExtension(String value) {
        return setParameter("shippingPhoneExtension", value);
    }

    /**
     * Get the shipping fax number.
     */
    public String getShippingFax() {
        return getParameter("shippingFax");
    }

    /**
     * Sets the shipping fax number.
     */
    public CreditCard setShippingFax(String value) {
        return setParameter("shippingFax", value);
    }

    /**
     * Get the billing address, line 1.
     */
    public String getAddress1() {
        return getParameter("billingAddress1");
    }

    /**
     * Sets the billing and shipping address, line 1.
     */
    public CreditCard setAddress1(String value) {
        return setParameter("billingAddress1", value).setParameter("shippingAddress1", value);
    }

    /**
     * Get the billing address, line 2.
     */
    public String getAddress2() {
        return getParameter("billingAddress2");
    }

    /**
     * Sets the billing and shipping address, line 2.
     */
    public CreditCard setAddress2(String value) {
        return setParameter("billingAddress2", value).setParameter("shippingAddress2", value);
    }

    /**
     * Get the billing city.
     */
    public String getCity() {
        return getParameter("billingCity");
    }

    /**
     * Sets the billing and shipping city.
     */
    public CreditCard setCity(String value) {
        return setParameter("billingCity", value).setParameter("shippingCity", value);
    }

    /**
     * Get the billing postcode.
     */
    public String getPostcode() {
        return getParameter("billingPostcode");
    }

    /**
     * Sets the billing and shipping postcode.
     */
    public CreditCard setPostcode(String value) {
        return setParameter("billingPostcode", value).setParameter("shippingPostcode", value);
    }

    /**
     * Get the billing state.
     */
    public String getState() {
        return getParameter("billingState");
    }

    /**
     * Sets the billing and shipping state.
     */
    public CreditCard setState(String value) {
        return setParameter("billingState", value).setParameter("shippingState", value);
    }
    
    /**
     * Get the billing country.
     */
    public String getCountry() {
        return getParameter("billingCountry");
    }

    /**
     * Sets the billing and shipping country.
     */
    public CreditCard setCountry(String value) {
        return setParameter("billingCountry", value).setParameter("shippingCountry", value);
    }
    
    /**
     * Get the billing phone number.
     */
    public String getPhone() {
        return getParameter("billingPhone");
    }

    /**
     * Sets the billing and shipping phone number.
     */
    public CreditCard setPhone(String value) {
        return setParameter("billingPhone", value).setParameter("shippingPhone", value);
    }
    
    /**
     * Get the billing phone number extension.
     */
    public String getPhoneExtension() {
        return getParameter("billingPhoneExtension");
    }

    /**
     * Sets the billing and shipping phone number extension.
     */
    public CreditCard setPhoneExtension(String value) {
        return setParameter("billingPhoneExtension", value).setParameter("shippingPhoneExtension", value);
    }
    
    /**
     * Get the billing fax number.
     */
    public String getFax() {
        return getParameter("billingFax");
    }

    /**
     * Sets the billing and shipping fax number.
     */
    public CreditCard setFax(String value) {
        return setParameter("billingFax", value).setParameter("shippingFax", value);
    }
    
    /**
     * Get the card billing company name.
     */
    public String getCompany() {
        return getParameter("billingCompany");
    }

    /**
     * Sets the billing and shipping company name.
     */
    public CreditCard setCompany(String value) {
        return setParameter("billingCompany", value).setParameter("shippingCompany", value);
    }
    
    /**
     * Get the cardholder's email.
     */
    public String getEmail() {
        return getParameter("email");
    }

    /**
     * Sets the cardholder's email.
     */
    public CreditCard setEmail(String value) {
        return setParameter("email", value);
    }
    
    /**
     * Get the cardholder's birthday.
     */
    public String getBirthday() {
        return getParameter("birthday");
    }

    /**
     * Sets the cardholder's birthday.
     */
    public CreditCard setBirthday(String value) {
        return setParameter("birthday", value);
    }

    /**
     * Get the cardholder's gender.
     */
    public String getGender() {
        return getParameter("gender");
    }
    
    /**
     * Sets the cardholder's gender.
     */
    public CreditCard setGender(String value) {
        return setParameter("gender", value);
    }

}
