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
package org.opencps.payment.exception;

import java.io.IOException;

/**
 * @author Nguyen Van Nguyen <nguyennv@iwayvietnam.com>
 */
public class InvalidRequestException extends IOException {

    private static final long serialVersionUID = -4035363288860693988L;

    public InvalidRequestException(String string) {
        super(string);
    }
}
