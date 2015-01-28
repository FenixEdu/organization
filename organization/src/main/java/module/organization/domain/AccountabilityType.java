/*
 * @(#)AccountabilityType.java
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
import java.util.Comparator;
import java.util.List;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.commons.i18n.LocalizedString;

import pt.ist.fenixframework.Atomic;

/**
 *
 * @author Pedro Santos
 * @author Paulo Abrantes
 * @author Luis Cruz
 *
 */
public class AccountabilityType extends AccountabilityType_Base implements Comparable<AccountabilityType> {

    public static final Comparator<AccountabilityType> COMPARATORY_BY_NAME = (o1, o2) -> {
        int c = o1.getName().compareTo(o2.getName());
        return c == 0 ? o2.hashCode() - o1.hashCode() : c;
    };

    static public class AccountabilityTypeBean implements Serializable {

        private static final long serialVersionUID = -1189746935274309327L;
        private String type;
        private LocalizedString name;
        private AccountabilityType accountabilityType;

        public AccountabilityTypeBean() {
        }

        public AccountabilityTypeBean(final AccountabilityType accountabilityType) {
            setType(accountabilityType.getType());
            setName(accountabilityType.getName());
            setAccountabilityType(accountabilityType);
        }

        public AccountabilityTypeBean(String type, LocalizedString name) {
            setType(type);
            setName(name);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public LocalizedString getName() {
            return name;
        }

        public void setName(LocalizedString name) {
            this.name = name;
        }

        public AccountabilityType getAccountabilityType() {
            return accountabilityType;
        }

        public void setAccountabilityType(final AccountabilityType accountabilityType) {
            this.accountabilityType = accountabilityType;
        }

        public AccountabilityType create() {
            return AccountabilityType.create(this);
        }

        public void edit() {
            getAccountabilityType().edit(getType(), getName());
        }
    }

    protected AccountabilityType() {
        super();
        setMyOrg(Bennu.getInstance());
    }

    protected AccountabilityType(final String type) {
        this(type, null);
    }

    protected AccountabilityType(final String type, final LocalizedString name) {
        this();
        check(type);
        setType(type);
        setName(name);
    }

    protected void check(final String type) {
        if (type == null || type.isEmpty()) {
            throw new OrganizationDomainException("error.AccountabilityType.invalid.type");
        }
        final AccountabilityType accountabilityType = readBy(type);
        if (accountabilityType != null && accountabilityType != this) {
            throw new OrganizationDomainException("error.AccountabilityType.duplicated.type", type);
        }
    }

    public boolean hasType(final String type) {
        return getType() != null && getType().equalsIgnoreCase(type);
    }

    @Atomic
    public void edit(final String type, final LocalizedString name) {
        check(type);
        setType(type);
        setName(name);
    }

    @Atomic
    public void delete() {
        canDelete();
        disconnect();
        deleteDomainObject();
    }

    protected void canDelete() {
        if (!getAccountabilitiesSet().isEmpty()) {
            throw new OrganizationDomainException("error.AccountabilityType.has.accountabilities.cannot.delete");
        }
    }

    private void disconnect() {
        getConnectionRules().clear();
        setMyOrg(null);
    }

    @Override
    public int compareTo(AccountabilityType other) {
        int res = getType().compareTo(other.getType());
        return res != 0 ? res : getExternalId().compareTo(other.getExternalId());
    }

    public boolean isValid(final Party parent, final Party child) {
        for (final ConnectionRule rule : getConnectionRules()) {
            if (!rule.isValid(this, parent, child)) {
                return false;
            }
        }
        return true;
    }

    @Atomic
    static public AccountabilityType create(final AccountabilityTypeBean bean) {
        return new AccountabilityType(bean.getType(), bean.getName());
    }

    static public AccountabilityType readBy(final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        for (final AccountabilityType element : Bennu.getInstance().getAccountabilityTypesSet()) {
            if (element.hasType(type)) {
                return element;
            }
        }
        return null;
    }

    @Atomic
    public void associateConnectionRules(final List<ConnectionRule> connectionRules) {
        getConnectionRules().retainAll(connectionRules);
        getConnectionRules().addAll(connectionRules);
    }

    @Deprecated
    public java.util.Set<module.organization.domain.groups.PersistentUnitGroup> getUnitGroupFromChildUnitAccountabilityType() {
        return getUnitGroupFromChildUnitAccountabilityTypeSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.ConnectionRule> getConnectionRules() {
        return getConnectionRulesSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.OrganizationalModel> getOrganizationalModels() {
        return getOrganizationalModelsSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.groups.PersistentUnitGroup> getUnitGroupFromMemberAccountabilityType() {
        return getUnitGroupFromMemberAccountabilityTypeSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.UnconfirmedAccountability> getUnconfirmedAccountabilities() {
        return getUnconfirmedAccountabilitiesSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.Accountability> getAccountabilities() {
        return getAccountabilitiesSet();
    }

}
