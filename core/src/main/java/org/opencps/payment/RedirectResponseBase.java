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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.IterableMap;
import org.opencps.payment.api.PaymentRedirectResponse;

/**
 * This abstract class implements PaymentRedirectResponse
 * 
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public abstract class RedirectResponseBase extends ResponseBase implements PaymentRedirectResponse {

    /**
     * Constructor
     * 
     * @param HttpServletResponse
     * @param PaymentRequest
     * @param String
     */
    public RedirectResponseBase(RequestBase request, IterableMap<String, String> data) {
        super(request, data);
    }
    
    /**
     * (non-Javadoc)
     * @see org.opencps.payment.api.PaymentRedirectResponse#redirect()
     */
    @Override
    public void redirect() throws IOException {
        if (!isRedirect()) {
            throw new IOException("This response does not support redirection.");
        }
        HttpServletResponse response = request.getConnector().getServletResponse();
        if (getRedirectMethod().toUpperCase() == "GET") {
            response.sendRedirect(getRedirectUrl());
        }
        else if(getRedirectMethod().toUpperCase() == "POST") {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(getRedirectForm());
        }
        else {
            throw new IOException("Invalid redirect method " + getRedirectMethod() + ".");
        }
    }
    
    /**
     * Get html code of redirect form
     */
    public String getRedirectForm() {
        StringBuilder hiddenFields = new StringBuilder();
        IterableMap<String, String> data = getRedirectData();

        for (Map.Entry<String, String> entry: data.entrySet()) {
            hiddenFields.append("<input type=\"hidden\" name=\"")
                .append(entry.getKey())
                .append("\"")
                .append(" value=\"")
                .append(entry.getValue())
                .append("\" />");
        }

        StringBuilder redirectForm = new StringBuilder();
        redirectForm.append("<!DOCTYPE html><html><head>")
            .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
            .append("<title>Redirecting...</title>")
            .append("<body onload=\"document.forms[0].submit();\">")
            .append("<form action=\"" + getRedirectUrl() + "\" method=\"post\">")
            .append("<p>Redirecting to payment page...</p>")
            .append("<p><input type=\"submit\" value=\"Continue\" /></p>")
            .append(hiddenFields)
            .append("</form></body></html>");

        return redirectForm.toString();
    }
}
