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

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencps.payment.mock.MockBaseConnector;
import org.opencps.payment.mock.MockBaseRequest;

import com.google.api.client.http.HttpTransport;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ConnectorBase.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class ConnectorBaseTest extends TestCase {

    private ConnectorBase connector;

    /**
     * Create the test case
     */
    public ConnectorBaseTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() {
        connector = mock(ConnectorBase.class, CALLS_REAL_METHODS);
        connector.initialize(new HashMap<String, String>());
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(ConnectorBaseTest.class);
    }

    public void testCoConnectorBasenstruct() {
        ConnectorBase connector = new MockBaseConnector();
        assertTrue(connector.getHttpTransport() instanceof HttpTransport);
        assertTrue(connector.getServletRequest() instanceof HttpServletRequest);
        assertTrue(connector.getServletResponse() instanceof HttpServletResponse);
    }
    
    public void testInitializeParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("currency", "VND");
        params.put("unknown", "ABC");
        connector.initialize(params);
        assertEquals(params, connector.getParameters());
        assertEquals("ABC", connector.getParameter("unknown"));
        assertEquals("VND", connector.getCurrency());
    }
    
    public void testTestMode() {
        assertEquals(connector, connector.setTestMode(true));
        assertTrue(connector.getTestMode());
    }
    
    public void testCurrency() {
        assertEquals(connector, connector.setCurrency("VND"));
        assertEquals("VND", connector.getCurrency());
    }

    public void testSupportsAuthorize() {
        assertFalse(connector.supportsAuthorize());
    }

    public void testAuthorize() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doAuthorize(params)).thenReturn(request);
        assertTrue(connector.authorize(params) instanceof RequestBase);
    }

    public void testSupportsCompleteAuthorize() {
        assertFalse(connector.supportsCompleteAuthorize());
    }

    public void testCompleteAuthorize() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doCompleteAuthorize(params)).thenReturn(request);
        assertTrue(connector.completeAuthorize(params) instanceof RequestBase);
    }

    public void testSupportsCapture() {
        assertFalse(connector.supportsCapture());
    }

    public void testCapture() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doCapture(params)).thenReturn(request);
        assertTrue(connector.capture(params) instanceof RequestBase);
    }

    public void testSupportsPurchase() {
        assertFalse(connector.supportsPurchase());
    }

    public void testPurchase() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doPurchase(params)).thenReturn(request);
        assertTrue(connector.purchase(params) instanceof RequestBase);
    }

    public void testSupportsCompletePurchase() {
        assertFalse(connector.supportsCompletePurchase());
    }

    public void testCompletePurchase() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doCompletePurchase(params)).thenReturn(request);
        assertTrue(connector.completePurchase(params) instanceof RequestBase);
    }

    public void testSupportsRefund() {
        assertFalse(connector.supportsRefund());
    }

    public void testRefund() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doRefund(params)).thenReturn(request);
        assertTrue(connector.refund(params) instanceof RequestBase);
    }

    public void testSupportsRevert() {
        assertFalse(connector.supportsRevert());
    }

    public void testRevert() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doRevert(params)).thenReturn(request);
        assertTrue(connector.revert(params) instanceof RequestBase);
    }

    public void testSupportsCreateCard() {
        assertFalse(connector.supportsCreateCard());
    }

    public void testCreateCard() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doCreateCard(params)).thenReturn(request);
        assertTrue(connector.createCard(params) instanceof RequestBase);
    }

    public void testSupportsUpdateCard() {
        assertFalse(connector.supportsUpdateCard());
    }

    public void testUpdateCard() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doUpdateCard(params)).thenReturn(request);
        assertTrue(connector.updateCard(params) instanceof RequestBase);
    }

    public void testSupportsDeleteCard() {
        assertFalse(connector.supportsDeleteCard());
    }

    public void testDeleteCard() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doDeleteCard(params)).thenReturn(request);
        assertTrue(connector.deleteCard(params) instanceof RequestBase);
    }

    public void testSupportsAcceptNotification() {
        assertFalse(connector.supportsAcceptNotification());
    }

    public void testAcceptNotification() {
        RequestBase request = mock(RequestBase.class);
        Map<String, String> params = new HashMap<String, String>();
        when(connector.doAcceptNotification(params)).thenReturn(request);
        assertTrue(connector.acceptNotification(params) instanceof RequestBase);
    }

    public void testCreateRequest() {
        MockBaseConnector connector = new MockBaseConnector();
        Map<String, String> params = new HashMap<String, String>();
        params.put("currency", "VND");
        MockBaseRequest request = connector.callCreateRequest(MockBaseRequest.class, params);
        assertEquals("VND", request.getCurrency());
    }
    
}
