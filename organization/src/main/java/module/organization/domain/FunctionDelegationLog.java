/*
 * @(#)FunctionDelegationLog.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Organization Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Organization Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.organization.domain;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.security.Authenticate;
import org.joda.time.DateTime;

/**
 * 
 * @author João Neves
 * @author Luis Cruz
 * 
 */
public class FunctionDelegationLog extends FunctionDelegationLog_Base {

    public FunctionDelegationLog(final FunctionDelegation functionDelegation, String operation) {
        super();
        setMyOrg(functionDelegation.getMyOrg());
        setFunctionDelegation(functionDelegation);
        final User user = Authenticate.getUser();
        setExecutor(user == null ? null : user.getUsername());
        setOperationInstant(new DateTime());
        setOperation(operation);
    }

}
