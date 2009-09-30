/*
 * @(#)ConnectionRule.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module for the MyOrg web application.
 *
 *   The Organization Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
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

import java.io.Serializable;

import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.util.DomainReference;

abstract public class ConnectionRule extends ConnectionRule_Base {

    static abstract public class ConnectionRuleBean implements Serializable {

	private static final long serialVersionUID = -5549142750086201478L;
	private DomainReference<ConnectionRule> connectionRule;

	protected ConnectionRuleBean() {
	}

	protected ConnectionRuleBean(final ConnectionRule connectionRule) {
	    setConnectionRule(connectionRule);
	}

	public ConnectionRule getConnectionRule() {
	    return connectionRule != null ? connectionRule.getObject() : null;
	}

	public void setConnectionRule(final ConnectionRule connectionRule) {
	    this.connectionRule = (connectionRule != null ? new DomainReference<ConnectionRule>(connectionRule) : null);
	}

	abstract public ConnectionRule create();
    }

    protected ConnectionRule() {
	super();
	setMyOrg(MyOrg.getInstance());
    }

    @Service
    public void delete() {
	disconnect();
	deleteDomainObject();
    }

    protected void disconnect() {
	getAccountabilityTypes().clear();
	removeMyOrg();
    }

    abstract public ConnectionRuleBean buildBean();

    abstract public boolean isValid(final AccountabilityType accountabilityType, final Party parent, final Party child);

    abstract public String getDescription();
}
