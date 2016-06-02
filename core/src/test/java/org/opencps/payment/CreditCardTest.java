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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.opencps.payment.exception.InvalidCreditCardException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for CreditCard.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class CreditCardTest extends TestCase {

    private CreditCard card;

    /**
     * Create the test case
     */
    public CreditCardTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() {
        card = new CreditCard();
        card.setNumber("4111111111111111");
        card.setFirstName("Example");
        card.setLastName("Customer");
        card.setCvv("123");

        Calendar calendar = Calendar.getInstance();
        card.setExpiryMonth(calendar.get(Calendar.MONTH) + 4);
        card.setExpiryYear(calendar.get(Calendar.YEAR) + 1);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(CreditCardTest.class);
    }

    public void testConstructWithParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("billingFirstName", "Test");
        params.put("billingLastName", "Card");
        CreditCard card = new CreditCard(params);
        assertEquals("Test Card", card.getName());
    }

    public void testValidateFixture() {
        try {
            card.validate();
        }
        catch (InvalidCreditCardException ex) {
            fail(ex.getMessage());
        }
    }

    public void testValidateNumberRequired() {
        try {
            String number = null;
            card.setNumber(number);
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Card number is required", ex.getMessage());
        }
    }

    public void testValidateExpiryMonthRequired() {
        try {
            card.setExpiryMonth(0);
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Expiry month is required", ex.getMessage());
        }
    }

    public void testValidateExpiryYearRequired() {
        try {
            card.setExpiryYear(0);
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Expiry year is required", ex.getMessage());
        }
    }

    public void testValidateExpiryDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            card.setExpiryYear(calendar.get(Calendar.YEAR) - 1);
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Card has expired", ex.getMessage());
        }
    }

    public void testValidateNumber() {
        try {
            card.setNumber("4111111111111110");
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Card number is invalid", ex.getMessage());
        }
    }

    public void testGetSupportedBrands() {
        Map<String, String> brands = CreditCard.getSupportedBrands();
        String expression = brands.get(CreditCard.BRAND_VISA);
        assertNotNull(expression);
    }

    public void testCustomSupportedBrand() {
        CreditCard.addSupportedBrand("opencps_express", "^9\\d{12}(\\d{3})?$");
        Map<String, String> brands = CreditCard.getSupportedBrands();
        assertEquals("^9\\d{12}(\\d{3})?$", brands.get("opencps_express"));
    }

    public void testCustomBrandWorks() {
        try {
            CreditCard.addSupportedBrand("opencps_express", "^9\\d{12}(\\d{3})?$");
            card.setNumber("9111111111111110");
            card.validate();
            assertEquals("opencps_express", card.getBrand());
        }
        catch (InvalidCreditCardException ex) {
            fail(ex.getMessage());
        }
    }

    public void testTitle() {
        card.setTitle("Mrs");
        assertEquals("Mrs", card.getTitle());
    }

    public void testFirstName() {
        card.setFirstName("Open");
        assertEquals("Open", card.getFirstName());
    }

    public void testLastName() {
        card.setLastName("Cps");
        assertEquals("Cps", card.getLastName());
    }

    public void testSetName() {
        card.setName("Open Cps");
        assertEquals("Open", card.getFirstName());
        assertEquals("Cps", card.getLastName());
    }

    public void testSetNameWithMultipleNames() {
        card.setName("Viet Open Cps");
        assertEquals("Viet", card.getFirstName());
        assertEquals("Open Cps", card.getLastName());
    }

    public void testNumber() {
        card.setNumber("4000000000000000");
        assertEquals("4000000000000000", card.getNumber());
    }

    public void testSetNumberStripsNonDigits() {
        card.setNumber("4000 0000 00b00 0000");
        assertEquals("4000000000000000", card.getNumber());
    }

    public void testGetNumberLastFourNull() {
        String number = null;
        card.setNumber(number);
        assertNull(card.getNumberLastFour());
    }

    public void testGetNumberLastFour() {
        card.setNumber("4000000000001234");
        assertEquals("1234", card.getNumberLastFour());
    }

    public void testGetNumberLastFourNonDigits() {
        card.setNumber("4000 0000 0000 12x34");
        assertEquals("1234", card.getNumberLastFour());
    }

    public void testGetNumberMasked() {
        card.setNumber("4000000000001234");
        assertEquals("XXXXXXXXXXXX1234", card.getNumberMasked('X'));
    }

    public void testGetNumberMaskedNonDigits() {
        card.setNumber("4000 0000 0000 12x34");
        assertEquals("XXXXXXXXXXXX1234", card.getNumberMasked('X'));
    }

    public void testGetBrandDefault() {
        CreditCard card = new CreditCard();
        assertNull(card.getBrand());
    }
    
    public void testGetBrandVisa() {
        CreditCard card = new CreditCard();
        card.setNumber("4242424242424242");
        assertEquals(CreditCard.BRAND_VISA, card.getBrand());
    }
    
    public void testGetBrandMasterCard() {
        CreditCard card = new CreditCard();
        card.setNumber("5555555555554444");
        assertEquals(CreditCard.BRAND_MASTERCARD, card.getBrand());
    }
    
    public void testGetBrandAmex() {
        CreditCard card = new CreditCard();
        card.setNumber("378282246310005");
        assertEquals(CreditCard.BRAND_AMEX, card.getBrand());
    }
    
    public void testGetBrandDiscover() {
        CreditCard card = new CreditCard();
        card.setNumber("6011111111111117");
        assertEquals(CreditCard.BRAND_DISCOVER, card.getBrand());
    }
    
    public void testGetBrandDinersClub() {
        CreditCard card = new CreditCard();
        card.setNumber("30569309025904");
        assertEquals(CreditCard.BRAND_DINERS_CLUB, card.getBrand());
    }
    
    public void testGetBrandJcb() {
        CreditCard card = new CreditCard();
        card.setNumber("3530111333300000");
        assertEquals(CreditCard.BRAND_JCB, card.getBrand());
    }
    
    public void testExpiryMonth() {
        card.setExpiryMonth(9);
        assertEquals(new Integer(9), card.getExpiryMonth());
    }
    
    public void testExpiryYear() {
        card.setExpiryYear(2012);
        assertEquals(new Integer(2012), card.getExpiryYear());
    }
    
    public void testExpiryDate() {
        card.setExpiryMonth(7);
        card.setExpiryYear(2016);
        assertEquals("072016", card.getExpiryDate("MMyyyy"));
        assertEquals("07-2016", card.getExpiryDate("MM-yyyy"));
    }
    
    public void testStartMonth() {
        card.setStartMonth(9);
        assertEquals(new Integer(9), card.getStartMonth());
    }
    
    public void testStartYear() {
        card.setStartYear(2012);
        assertEquals(new Integer(2012), card.getStartYear());
    }
    
    public void testStartDate() {
        card.setStartMonth(7);
        card.setStartYear(2016);
        assertEquals("072016", card.getStartDate("MMyyyy"));
        assertEquals("07-2016", card.getStartDate("MM-yyyy"));
    }
    
    public void testCvv() {
        card.setCvv("456");
        assertEquals("456", card.getCvv());
    }
    
    public void testTracks() {
        card.setTracks("%B4242424242424242^SMITH/JOHN ^1520126100000000000000444000000?;4242424242424242=15201269999944401?");
        assertEquals("%B4242424242424242^SMITH/JOHN ^1520126100000000000000444000000?;4242424242424242=15201269999944401?", card.getTracks());
    }
    
    public void testShouldReturnTrack1() {
        card.setTracks("%B4242424242424242^SMITH/JOHN ^1520126100000000000000444000000?;4242424242424242=15201269999944401?");
        assertEquals("%B4242424242424242^SMITH/JOHN ^1520126100000000000000444000000?", card.getTrack1());
    }
    
    public void testShouldReturnTrack2() {
        card.setTracks("%B4242424242424242^SMITH/JOHN ^1520126100000000000000444000000?;4242424242424242=15201269999944401?");
        assertEquals(";4242424242424242=15201269999944401?", card.getTrack2());
    }
    
    public void testIssueNumber() {
        card.setIssueNumber("123");
        assertEquals("123", card.getIssueNumber());
    }
    
    public void testBillingTitle() {
        card.setBillingTitle("Miss");
        assertEquals("Miss", card.getBillingTitle());
        assertEquals("Miss", card.getTitle());
    }
    
    public void testBillingFirstName() {
        card.setBillingFirstName("Nguyen");
        assertEquals("Nguyen", card.getBillingFirstName());
        assertEquals("Nguyen", card.getFirstName());
    }
    
    public void testBillingLastName() {
        card.setBillingLastName("Van A");
        assertEquals("Van A", card.getBillingLastName());
        assertEquals("Van A", card.getLastName());
    }
    
    public void testBillingName() {
        card.setBillingFirstName("Nguyen");
        card.setBillingLastName("Van A");
        assertEquals("Nguyen Van A", card.getBillingName());

        card.setBillingName("Nguyen Van B");
        assertEquals("Nguyen", card.getBillingFirstName());
        assertEquals("Van B", card.getBillingLastName());
    }
    
    public void testBillingCompany() {
        card.setBillingCompany("OpenCps");
        assertEquals("OpenCps", card.getBillingCompany());
        assertEquals("OpenCps", card.getCompany());
    }
    
    public void testBillingAddress1() {
        card.setBillingAddress1("31 Hai Ba Trung");
        assertEquals("31 Hai Ba Trung", card.getBillingAddress1());
        assertEquals("31 Hai Ba Trung", card.getAddress1());
    }
    
    public void testBillingAddress2() {
        card.setBillingAddress2("Hanoi - Vietnam");
        assertEquals("Hanoi - Vietnam", card.getBillingAddress2());
        assertEquals("Hanoi - Vietnam", card.getAddress2());
    }
    
    public void testBillingCity() {
        card.setBillingCity("Hanoi");
        assertEquals("Hanoi", card.getBillingCity());
        assertEquals("Hanoi", card.getCity());
    }
    
    public void testBillingPostcode() {
        card.setBillingPostcode("123456");
        assertEquals("123456", card.getBillingPostcode());
        assertEquals("123456", card.getPostcode());
    }
    
    public void testBillingState() {
        card.setBillingState("HN");
        assertEquals("HN", card.getBillingState());
        assertEquals("HN", card.getState());
    }
    
    public void testBillingCountry() {
        card.setBillingCountry("VN");
        assertEquals("VN", card.getBillingCountry());
        assertEquals("VN", card.getCountry());
    }
    
    public void testBillingPhone() {
        card.setBillingPhone("54321");
        assertEquals("54321", card.getBillingPhone());
        assertEquals("54321", card.getPhone());
    }
    
    public void testBillingPhoneExtension() {
        card.setBillingPhoneExtension("001");
        assertEquals("001", card.getBillingPhoneExtension());
        assertEquals("001", card.getPhoneExtension());
    }
    
    public void testBillingFax() {
        card.setBillingFax("12345");
        assertEquals("12345", card.getBillingFax());
        assertEquals("12345", card.getFax());
    }
    
    public void testShippingTitle() {
        card.setShippingTitle("Dr.");
        assertEquals("Dr.", card.getShippingTitle());
    }
    
    public void testShippingFirstName() {
        card.setShippingFirstName("Nguyen");
        assertEquals("Nguyen", card.getShippingFirstName());
    }
    
    public void testShippingLastName() {
        card.setShippingLastName("Van A");
        assertEquals("Van A", card.getShippingLastName());
    }
    
    public void testShippingName() {
        card.setShippingFirstName("Nguyen");
        card.setShippingLastName("Van A");
        assertEquals("Nguyen Van A", card.getShippingName());

        card.setShippingName("Tran Van B");
        assertEquals("Tran", card.getShippingFirstName());
        assertEquals("Van B", card.getShippingLastName());
    }
    
    public void testShippingCompany() {
        card.setShippingCompany("Viet OpenCps");
        assertEquals("Viet OpenCps", card.getShippingCompany());
    }
    
    public void testShippingAddress1() {
        card.setShippingAddress1("123 Ba Trieu");
        assertEquals("123 Ba Trieu", card.getShippingAddress1());
    }
    
    public void testShippingAddress2() {
        card.setShippingAddress2("Hai Ba Trung - Hanoi");
        assertEquals("Hai Ba Trung - Hanoi", card.getShippingAddress2());
    }
    
    public void testShippingCity() {
        card.setShippingCity("Saigon");
        assertEquals("Saigon", card.getShippingCity());
    }
    
    public void testShippingPostcode() {
        card.setShippingPostcode("10000");
        assertEquals("10000", card.getShippingPostcode());
    }
    
    public void testShippingState() {
        card.setShippingState("SG");
        assertEquals("SG", card.getShippingState());
    }
    
    public void testShippingCountry() {
        card.setShippingCountry("VN");
        assertEquals("VN", card.getShippingCountry());
    }
    
    public void testShippingPhone() {
        card.setShippingPhone("12345");
        assertEquals("12345", card.getShippingPhone());
    }
    
    public void testShippingPhoneExtension() {
        card.setShippingPhoneExtension("010");
        assertEquals("010", card.getShippingPhoneExtension());
    }
    
    public void testShippingFax() {
        card.setShippingFax("54321");
        assertEquals("54321", card.getShippingFax());
    }
    
    public void testCompany() {
        card.setCompany("OpenCps");
        assertEquals("OpenCps", card.getCompany());
        assertEquals("OpenCps", card.getBillingCompany());
        assertEquals("OpenCps", card.getShippingCompany());
    }
    
    public void testAddress1() {
        card.setAddress1("123 Tran Hung Dao");
        assertEquals("123 Tran Hung Dao", card.getAddress1());
        assertEquals("123 Tran Hung Dao", card.getBillingAddress1());
        assertEquals("123 Tran Hung Dao", card.getShippingAddress1());
    }
    
    public void testAddress2() {
        card.setAddress2("Hoan Kiem");
        assertEquals("Hoan Kiem", card.getAddress2());
        assertEquals("Hoan Kiem", card.getBillingAddress2());
        assertEquals("Hoan Kiem", card.getShippingAddress2());
    }
    
    public void testCity() {
        card.setCity("Hanoi");
        assertEquals("Hanoi", card.getCity());
        assertEquals("Hanoi", card.getBillingCity());
        assertEquals("Hanoi", card.getShippingCity());
    }
    
    public void testPostcode() {
        card.setPostcode("9000");
        assertEquals("9000", card.getPostcode());
        assertEquals("9000", card.getBillingPostcode());
        assertEquals("9000", card.getShippingPostcode());
    }
    
    public void testState() {
        card.setState("HN");
        assertEquals("HN", card.getState());
        assertEquals("HN", card.getBillingState());
        assertEquals("HN", card.getShippingState());
    }
    
    public void testCountry() {
        card.setCountry("Vietnam");
        assertEquals("Vietnam", card.getCountry());
        assertEquals("Vietnam", card.getBillingCountry());
        assertEquals("Vietnam", card.getShippingCountry());
    }
    
    public void testPhone() {
        card.setPhone("123456");
        assertEquals("123456", card.getPhone());
        assertEquals("123456", card.getBillingPhone());
        assertEquals("123456", card.getShippingPhone());
    }
    
    public void testPhoneExtension() {
        card.setPhoneExtension("100");
        assertEquals("100", card.getPhoneExtension());
        assertEquals("100", card.getBillingPhoneExtension());
        assertEquals("100", card.getShippingPhoneExtension());
    }
    
    public void testFax() {
        card.setFax("98765");
        assertEquals("98765", card.getFax());
        assertEquals("98765", card.getBillingFax());
        assertEquals("98765", card.getShippingFax());
    }
    
    public void testEmail() {
        card.setEmail("vana@example.com");
        assertEquals("vana@example.com", card.getEmail());
    }
    
    public void testBirthday() {
        card.setBirthday("01-02-2000");
        assertEquals("01-02-2000", card.getBirthday());
    }
    
    public void testGender() {
        card.setGender("female");
        assertEquals("female", card.getGender());
    }
    
    public void testInvalidLuhn() {
        try {
            card.setNumber("43");
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Card number is invalid", ex.getMessage());
        }
    }
    
    public void testInvalidShortCard() {
        try {
            card.setNumber("4440");
            card.validate();
            fail("Missing exception");
        }
        catch (InvalidCreditCardException ex) {
            assertEquals("Card number should have 12 to 19 digits", ex.getMessage());
        }
    }
}
