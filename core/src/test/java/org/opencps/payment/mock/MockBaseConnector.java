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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencps.payment.ConnectorBase;
import org.opencps.payment.RequestBase;

import com.google.api.client.http.HttpTransport;

/**
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class MockBaseConnector extends ConnectorBase {

    public MockBaseConnector() {
        super(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
    }
    
    /**
     * @param servletRequest
     * @param servletResponse
     */
    public MockBaseConnector(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        super(servletRequest, servletResponse);
    }

    /**
     * @param servletRequest
     * @param servletResponse
     * @param transport
     */
    public MockBaseConnector(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
            HttpTransport transport) {
        super(servletRequest, servletResponse, transport);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#getName()
     */
    @Override
    public String getName() {
        return "Mock Connector Implementation";
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#getShortName()
     */
    @Override
    public String getShortName() {
        return "MockConnector";
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsAuthorize()
     */
    @Override
    public Boolean supportsAuthorize() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsCompleteAuthorize()
     */
    @Override
    public Boolean supportsCompleteAuthorize() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsCapture()
     */
    @Override
    public Boolean supportsCapture() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsPurchase()
     */
    @Override
    public Boolean supportsPurchase() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsCompletePurchase()
     */
    @Override
    public Boolean supportsCompletePurchase() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsRefund()
     */
    @Override
    public Boolean supportsRefund() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsRevert()
     */
    @Override
    public Boolean supportsRevert() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsAcceptNotification()
     */
    @Override
    public Boolean supportsAcceptNotification() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsCreateCard()
     */
    @Override
    public Boolean supportsCreateCard() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsDeleteCard()
     */
    @Override
    public Boolean supportsDeleteCard() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.api.PaymentConnector#supportsUpdateCard()
     */
    @Override
    public Boolean supportsUpdateCard() {
        return false;
    }
    
    public <T extends RequestBase> T callCreateRequest(Class<T> type, Map<String, String> parameters) {
        return createRequest(type, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doAuthorize(java.util.Map)
     */
    @Override
    protected RequestBase doAuthorize(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doCompleteAuthorize(java.util.Map)
     */
    @Override
    protected RequestBase doCompleteAuthorize(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doCapture(java.util.Map)
     */
    @Override
    protected RequestBase doCapture(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doPurchase(java.util.Map)
     */
    @Override
    protected RequestBase doPurchase(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doCompletePurchase(java.util.Map)
     */
    @Override
    protected RequestBase doCompletePurchase(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doRefund(java.util.Map)
     */
    @Override
    protected RequestBase doRefund(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doRevert(java.util.Map)
     */
    @Override
    protected RequestBase doRevert(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doAcceptNotification(java.util.Map)
     */
    @Override
    protected RequestBase doAcceptNotification(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doCreateCard(java.util.Map)
     */
    @Override
    protected RequestBase doCreateCard(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doUpdateCard(java.util.Map)
     */
    @Override
    protected RequestBase doUpdateCard(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

    /* (non-Javadoc)
     * @see org.opencps.payment.ConnectorBase#doDeleteCard(java.util.Map)
     */
    @Override
    protected RequestBase doDeleteCard(Map<String, String> parameters) {
        return createRequest(MockBaseRequest.class, parameters);
    }

}
