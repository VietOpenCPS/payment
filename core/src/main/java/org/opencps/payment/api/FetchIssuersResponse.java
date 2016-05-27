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
package org.opencps.payment.api;

import java.util.List;
import org.opencps.payment.Issuer;

/**
 * This interface class defines the functionality of a response
 * that is a "fetch issuers" response.  It extends the ResponseInterface
 * interface class with some extra functions relating to the
 * specifics of a response to fetch the issuers from the gateway.
 * This happens when the gateway needs the customer to choose a card issuer.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public interface FetchIssuersResponse extends PaymentResponse {

    /**
     * Get the returned list of issuers.
     * 
     * @return List<Issuer>
     */
    public List<Issuer> getIssuers();
}
