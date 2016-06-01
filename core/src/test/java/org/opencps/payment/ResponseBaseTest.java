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

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.HashedMap;
import org.opencps.payment.api.PaymentRequest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Response Base.
 *
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class ResponseBaseTest extends TestCase {
    
    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public ResponseBaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(ResponseBaseTest.class);
    }
    
    public void testConstruct() {
    	ResponseBase response = new MockAbstractResponse();
        assertTrue(response.getRequest() instanceof PaymentRequest);
        assertTrue(response.getData() instanceof IterableMap);
    }
    
    public void testDefaultMethods() {
    	ResponseBase response = mock(ResponseBase.class, CALLS_REAL_METHODS);
    	assertFalse(response.isPending());
    	assertFalse(response.isRedirect());
    	assertFalse(response.isTransparentRedirect());
    	assertFalse(response.isCancelled());
    	assertNull(response.getData());
    	assertNull(response.getTransactionReference());
    	assertNull(response.getMessage());
    	assertNull(response.getCode());
    }
    
    class MockAbstractResponse extends ResponseBase {

    	public MockAbstractResponse() {
    		super(mock(RequestBase.class), new HashedMap<String, String>());
    	}
    	
		public MockAbstractResponse(RequestBase request, IterableMap<String, String> data) {
			super(request, data);
		}

		@Override
		public Boolean isSuccessful() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getTransactionReference() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }

}
