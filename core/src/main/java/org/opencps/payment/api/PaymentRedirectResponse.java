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

import java.io.IOException;
import java.util.Map;

/**
 * This interface class defines the functionality of a response
 * that is a redirect response.  It extends the PaymentResponse
 * interface class with some extra functions relating to the
 * specifics of a redirect response from the gateway.
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public interface PaymentRedirectResponse extends PaymentResponse {

    /**
     * Gets the redirect target url.
     */
    public String getRedirectUrl();

    /**
     * Get the required redirect method (either GET or POST).
     */
    public String getRedirectMethod();

    /**
     * Gets the redirect form data array, if the redirect method is POST.
     */
    public Map<String, String> getRedirectData();

    /**
     * Perform the required redirect.
     */
    public void redirect() throws IOException;
}
