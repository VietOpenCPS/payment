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

import org.opencps.payment.api.PaymentItem;
import org.opencps.payment.api.PaymentResponse;
import org.opencps.payment.exception.InvalidRequestException;
import org.opencps.payment.api.PaymentRequest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for Request Base.
 *
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class RequestBaseTest extends TestCase {

    private RequestBase request;
    
    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public RequestBaseTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        request = mock(RequestBase.class, CALLS_REAL_METHODS);
        Map<String, String> params = new HashMap<String, String>();
        request.initialize(params);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(RequestBaseTest.class);
    }

    public void testInitializeWithParams() {
    	Map<String, String> params = new HashMap<String, String>();
        params.put("amount", "1.23");
        request.initialize(params);
        assertEquals("1.23", request.getParameters().get("amount"));
        try {
            assertEquals("1.23", request.getAmount());
        } catch (InvalidRequestException e) {
            fail(e.getMessage());
        }
    }
    
    public void testInitializeAfterRequestSent() {
        RequestBase request = mock(MockAbstractRequest.class, CALLS_REAL_METHODS);
        request.send();
        Map<String, String> params = new HashMap<String, String>();
        try {
            request.initialize(params);
            fail("Missing exception");
        }
        catch (RuntimeException ex) {
            assertEquals("Request cannot be modified after it has been sent!", ex.getMessage());
        }
    }
    
    public void testCard() {
        CreditCard card = new CreditCard();
        assertEquals(request, request.setCard(card));
        assertEquals(card, request.getCard());
    }
    
    public void testSetCardWithParams() {
    	Map<String, String> params = new HashMap<String, String>();
        params.put("number", "1234");
        assertEquals(request, request.setCard(params));
        CreditCard card = request.getCard();
        assertEquals("1234", card.getNumber());
    }
    
    public void testToken() {
        assertEquals(request, request.setToken("1234"));
        assertEquals("1234", request.getToken());
    }
    
    public void testCardReference() {
        assertEquals(request, request.setCardReference("1234"));
        assertEquals("1234", request.getCardReference());
    }
    
    public void testAmount() throws InvalidRequestException {
        assertEquals(request, request.setAmount("1.23"));
        assertEquals("1.23", request.getAmount());

        assertEquals(request, request.setAmount(1.23f));
        assertEquals("1.23", request.getAmount());
    }
    
    public void testAmountWithEmpty() throws InvalidRequestException {
        String amount = null;
        assertEquals(request, request.setAmount(amount));
        assertNull(request.getAmount());
    }
    
    public void testAmountZeroFloat() throws InvalidRequestException {
        assertEquals(request, request.setAmount(0.00f));
        assertEquals("0.0", request.getAmount());
    }
    
    public void testAmountZeroString() throws InvalidRequestException {
        assertEquals(request, request.setAmount("0.00"));
        assertEquals("0.00", request.getAmount());
    }
    
    public void testAmountZeroNotAllowed() {
        MockAbstractRequest request = new MockAbstractRequest();
        request.zeroAmountNotAllowed().setAmount("0.00");
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("A zero amount is not allowed.", e.getMessage());
        }
    }
    
    public void testAmountPrecisionTooHigh() {
        assertEquals(request, request.setAmount("123.005"));
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("Amount precision is too high for currency.", e.getMessage());
        }
    }
    
    public void testGetAmountNoDecimals() throws InvalidRequestException {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals(request, request.setAmount("1234"));
        assertEquals("1234", request.getAmount());
    }
    
    public void testGetAmountNoDecimalsRounding() {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals(request, request.setAmount("123.4"));
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("Amount precision is too high for currency.", e.getMessage());
        }
    }
    
    public void testAmountWithIntThrowsException() {
        assertEquals(request, request.setCurrency("USD"));
        assertEquals(request, request.setAmount("123"));
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("Please specify amount as a float string, with decimal places (e.g. \'10.00\' to represent $10.00).", e.getMessage());
        }
    }
    
    public void testGetAmountInteger() throws InvalidRequestException {
        assertEquals(request, request.setAmount("12.34"));
        assertEquals(new Integer(1234), request.getAmountInteger());
    }
    
    public void testGetAmountIntegerNoDecimals() throws InvalidRequestException {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals(request, request.setAmount("1234"));
        assertEquals(new Integer(1234), request.getAmountInteger());
    }

    public void testAmountNegativeStringThrowsException() {
        assertEquals(request, request.setAmount("-123.00"));
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("A negative amount is not allowed.", e.getMessage());
        }
    }
    
    public void testAmountNegativeFloatThrowsException() {
        assertEquals(request, request.setAmount(-123.00f));
        try {
            request.getAmount();
            fail("Missing exception");
        } catch (InvalidRequestException e) {
            assertEquals("A negative amount is not allowed.", e.getMessage());
        }
    }
    
    public void testCurrency() {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals("VND", request.getCurrency());
    }
    
    public void testCurrencyLowercase() {
        assertEquals(request, request.setCurrency("vnd"));
        assertEquals("VND", request.getCurrency());
    }
    
    public void testCurrencyNumeric() {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals("704", request.getCurrencyNumeric());
    }
    
    public void testCurrencyDecimals() {
        assertEquals(request, request.setCurrency("VND"));
        assertEquals(new Integer(0), request.getCurrencyDecimalPlaces());
    }
    
    public void testDescription() {
        assertEquals(request, request.setDescription("OpenCps Payment Connector"));
        assertEquals("OpenCps Payment Connector", request.getDescription());
    }
    
    public void testTransactionId() {
        assertEquals(request, request.setTransactionId("1234"));
        assertEquals("1234", request.getTransactionId());
    }
    
    public void testTransactionReference() {
        assertEquals(request, request.setTransactionReference("abc"));
        assertEquals("abc", request.getTransactionReference());
    }
    
    public void testItems() {
        List<PaymentItem> items = new ArrayList<PaymentItem>();
        Item item = new Item();
        item.setName("Floppy Disk");
        items.add(item);
        assertEquals(request, request.setItems(items));
        assertEquals("Floppy Disk", request.getItems().get(0).getName());
    }
    
    public void testClientIp() {
        assertEquals(request, request.setClientIp("127.0.0.1"));
        assertEquals("127.0.0.1", request.getClientIp());
    }
    
    public void testReturnUrl() {
        assertEquals(request, request.setReturnUrl("https://www.example.com/return"));
        assertEquals("https://www.example.com/return", request.getReturnUrl());
    }
    
    public void testCancelUrl() {
        assertEquals(request, request.setCancelUrl("https://www.example.com/cancel"));
        assertEquals("https://www.example.com/cancel", request.getCancelUrl());
    }
    
    public void testNotifyUrl() {
        assertEquals(request, request.setNotifyUrl("https://www.example.com/notify"));
        assertEquals("https://www.example.com/notify", request.getNotifyUrl());
    }
    
    public void testIssuer() {
        assertEquals(request, request.setIssuer("OpenCps Bank"));
        assertEquals("OpenCps Bank", request.getIssuer());
    }
    
    public void testPaymentMethod() {
        assertEquals(request, request.setPaymentMethod("credit"));
        assertEquals("credit", request.getPaymentMethod());
    }
    
    public void testTestMode() {
        assertEquals(request, request.setTestMode(true));
        assertTrue(request.getTestMode());
    }
    
    public void testSetParameterAfterRequestSent() {
        RequestBase request = new MockAbstractRequest();
        request.send();
        try {
            request.setCurrency("VND");
            fail("Missing exception");
        }
        catch (RuntimeException e) {
            assertEquals("Request cannot be modified after it has been sent!", e.getMessage());
        }
    }
    
    public void testSend() {
        Map<String, String> data = new HashMap<String, String>();
        PaymentResponse response = mock(PaymentResponse.class);
        PaymentRequest request = mock(RequestBase.class, CALLS_REAL_METHODS);
        when(request.getData()).thenReturn(data);
        when(request.send(request.getData())).thenReturn(response);
        assertNotNull(request.send());
        assertSame(response, request.send());
    }
    
    public void testGetResponseBeforeRequestSent() {
        RequestBase request = new MockAbstractRequest();
        try {
            request.getResponse();
            fail("Missing exception");
        }
        catch (RuntimeException e) {
            assertEquals("You must call send() before accessing the Response!", e.getMessage());
        }
    }
    
    public void testGetResponseAfterRequestSent() {
        RequestBase request = new MockAbstractRequest();
        request.send();
        assertTrue(request.getResponse() instanceof PaymentResponse);
    }
    
    public void testGetConnector() {
        RequestBase request = new MockAbstractRequest();
        assertTrue(request.getConnector() instanceof ConnectorBase);
    }

    class MockAbstractRequest extends RequestBase {

        public MockAbstractRequest() {
            super(mock(ConnectorBase.class));
        }

        public MockAbstractRequest(ConnectorBase connector) {
            super(connector);
        }
        
        public MockAbstractRequest zeroAmountNotAllowed() {
            zeroAmountAllowed = false;
            return this;
        }

        @Override
        public PaymentResponse send(String data) {
            response = mock(PaymentResponse.class);
            return response;
        }

        @Override
        public Map<String, String> getData() {
            return null;
        }

        @Override
        public PaymentResponse send(Map<String, String> data) {
            response = mock(PaymentResponse.class);
            return response;
        }
        
    }
}

