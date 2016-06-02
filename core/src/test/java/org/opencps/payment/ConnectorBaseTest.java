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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.opencps.payment.api.PaymentResponse;
import com.google.api.client.http.HttpTransport;

/**
 * Unit test for ConnectorBase.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class ConnectorBaseTest {

    private ConnectorBase connector;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        connector = new MockAbstractConnector();
    }

    @Test
    public void testConstruct() {
        ConnectorBase connector = new MockAbstractConnector();
        assertTrue(connector.getHttpTransport() instanceof HttpTransport);
        assertTrue(connector.getServletRequest() instanceof HttpServletRequest);
        assertTrue(connector.getServletResponse() instanceof HttpServletResponse);
    }
    
    @Test
    public void testInitializeParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("currency", "VND");
        params.put("unknown", "ABC");
        connector.initialize(params);
        assertEquals(params, connector.getParameters());
        assertEquals("ABC", connector.getParameter("unknown"));
        assertEquals("VND", connector.getCurrency());
    }
    
    @Test
    public void testTestMode() {
        assertEquals(connector, connector.setTestMode(true));
        assertTrue(connector.getTestMode());
    }
    
    @Test
    public void testCurrency() {
        assertEquals(connector, connector.setCurrency("VND"));
        assertEquals("VND", connector.getCurrency());
    }

    @Test
    public void testSupportsAuthorize() {
        assertFalse(connector.supportsAuthorize());
    }

    @Test
    public void testSupportsCompleteAuthorize() {
        assertFalse(connector.supportsCompleteAuthorize());
    }

    @Test
    public void testSupportsCapture() {
        assertFalse(connector.supportsCapture());
    }

    @Test
    public void testSupportsPurchase() {
        assertFalse(connector.supportsPurchase());
    }

    @Test
    public void testSupportsCompletePurchase() {
        assertFalse(connector.supportsCompletePurchase());
    }

    @Test
    public void testSupportsRefund() {
        assertFalse(connector.supportsRefund());
    }

    @Test
    public void testSupportsVoid() {
        assertFalse(connector.supportsVoid());
    }

    @Test
    public void testSupportsCreateCard() {
        assertFalse(connector.supportsCreateCard());
    }

    @Test
    public void testSupportsDeleteCard() {
        assertFalse(connector.supportsDeleteCard());
    }

    @Test
    public void testSupportsUpdateCard() {
        assertFalse(connector.supportsUpdateCard());
    }

    @Test
    public void testSupportsAcceptNotification() {
        assertFalse(connector.supportsAcceptNotification());
    }

    public void testCreateRequest() {
    	MockAbstractConnector connector = new MockAbstractConnector();
    	Map<String, String> params = new HashMap<String, String>();
        params.put("currency", "VND");
        MockAbstractRequest request = connector.callCreateRequest(MockAbstractRequest.class, params);
        assertEquals("VND", request.getCurrency());
    }

    class MockAbstractConnector extends ConnectorBase {

        public MockAbstractConnector() {
            super(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        }
        
        public MockAbstractConnector(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
            super(servletRequest, servletResponse);
        }

        @Override
        public String getName() {
            return "Mock Connector Implementation";
        }

        @Override
        public String getShortName() {
            return "MockConnector";
        }

        @Override
        public Boolean supportsAuthorize() {
            return false;
        }

        @Override
        public Boolean supportsCompleteAuthorize() {
            return false;
        }

        @Override
        public Boolean supportsCapture() {
            return false;
        }

        @Override
        public Boolean supportsPurchase() {
            return false;
        }

        @Override
        public Boolean supportsCompletePurchase() {
            return false;
        }

        @Override
        public Boolean supportsRefund() {
            return false;
        }

        @Override
        public Boolean supportsVoid() {
            return false;
        }

        @Override
        public Boolean supportsAcceptNotification() {
            return false;
        }

        @Override
        public Boolean supportsCreateCard() {
            return false;
        }

        @Override
        public Boolean supportsDeleteCard() {
            return false;
        }

        @Override
        public Boolean supportsUpdateCard() {
            return false;
        }
        
        public <T extends RequestBase> T  callCreateRequest(Class<T> type, Map<String, String> parameters) {
            return createRequest(type, parameters);
        }
    }
    
    class MockAbstractRequest extends RequestBase {

        public MockAbstractRequest(ConnectorBase connector) {
            super(connector);
        }

        @Override
        public PaymentResponse send(Map<String, String> data) {
            return null;
        }

        @Override
        public PaymentResponse send(String data) {
            return null;
        }

        @Override
        public Map<String, String> getData() {
            return null;
        }
    }

}
