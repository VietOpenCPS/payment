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
package org.opencps.payment.mock;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.opencps.payment.ConnectorBase;
import org.opencps.payment.RequestBase;
import org.opencps.payment.api.PaymentResponse;

/**
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class MockBaseRequest extends RequestBase {

    public MockBaseRequest() {
        super(new MockBaseConnector());
    }
    
    /**
     * @param connector
     */
    public MockBaseRequest(ConnectorBase connector) {
        super(connector);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentRequest#send(java.util.Map)
     */
    @Override
    public PaymentResponse send(Map<String, String> data) {
        response = mock(PaymentResponse.class);
        return response;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentRequest#send(java.lang.String)
     */
    @Override
    public PaymentResponse send(String data) {
        response = mock(PaymentResponse.class);
        return response;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentMessage#getData()
     */
    @Override
    public Map<String, String> getData() {
        return new HashMap<String, String>();
    }
    
    public MockBaseRequest zeroAmountNotAllowed() {
        zeroAmountAllowed = false;
        return this;
    }

}
