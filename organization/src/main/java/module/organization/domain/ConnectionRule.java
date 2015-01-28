/*
 * @(#)ConnectionRule.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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

import java.io.Serializable;

import org.fenixedu.bennu.core.domain.Bennu;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
abstract public class ConnectionRule extends ConnectionRule_Base {

    static abstract public class ConnectionRuleBean implements Serializable {

        private static final long serialVersionUID = -5549142750086201478L;
        private ConnectionRule connectionRule;

        protected ConnectionRuleBean() {
        }

        protected ConnectionRuleBean(final ConnectionRule connectionRule) {
            setConnectionRule(connectionRule);
        }

        public ConnectionRule getConnectionRule() {
            return connectionRule;
        }

        public void setConnectionRule(final ConnectionRule connectionRule) {
            this.connectionRule = connectionRule;
        }

        abstract public ConnectionRule create();
    }

    protected ConnectionRule() {
        super();
        setMyOrg(Bennu.getInstance());
    }

    @Atomic
    public void delete() {
        disconnect();
        deleteDomainObject();
    }

    protected void disconnect() {
        getAccountabilityTypes().clear();
        setMyOrg(null);
    }

    abstract public ConnectionRuleBean buildBean();

    abstract public boolean isValid(final AccountabilityType accountabilityType, final Party parent, final Party child);

    abstract public String getDescription();

    @Deprecated
    public java.util.Set<module.organization.domain.AccountabilityType> getAccountabilityTypes() {
        return getAccountabilityTypesSet();
    }

}
