/*
 * @(#)PartyType.java
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

import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixframework.Atomic;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * 
 * @author Pedro Santos
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
public class PartyType extends PartyType_Base implements Comparable<PartyType> {

    static public class PartyTypeBean implements Serializable {

        private static final long serialVersionUID = -3867902288197067597L;
        private String type;
        private MultiLanguageString name;
        private PartyType partyType;

        public PartyTypeBean() {
        }

        public PartyTypeBean(final PartyType partyType) {
            setType(partyType.getType());
            setName(partyType.getName());
            setPartyType(partyType);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public MultiLanguageString getName() {
            return name;
        }

        public void setName(MultiLanguageString name) {
            this.name = name;
        }

        public PartyType getPartyType() {
            return partyType;
        }

        public void setPartyType(final PartyType partyType) {
            this.partyType = partyType;
        }

        public void edit() {
            getPartyType().edit(this);
        }
    }

    private PartyType() {
        super();
        setMyOrg(MyOrg.getInstance());
    }

    public PartyType(final String type) {
        this(type, null);
    }

    public PartyType(final String type, final MultiLanguageString name) {
        this();
        check(type);
        setType(type);
        setName(name);
    }

    private void check(final String type) {
        if (type == null || type.isEmpty()) {
            throw new DomainException("error.PartyType.invalid.type");
        }
        final PartyType partyType = readBy(type);
        if (partyType != null && partyType != this) {
            throw new DomainException("error.PartyType.duplicated.type", type);
        }
    }

    public boolean hasType(final String type) {
        return getType() != null && getType().equalsIgnoreCase(type);
    }

    @Atomic
    public void edit(final PartyTypeBean bean) {
        check(bean.getType());
        setType(bean.getType());
        setName(bean.getName());
    }

    @Atomic
    public void delete() {
        canDelete();
        setMyOrg(null);
        deleteDomainObject();
    }

    private void canDelete() {
        if (!getPartiesSet().isEmpty()) {
            throw new DomainException("error.PartyType.has.parties.cannot.delete");
        }
        if (!getParentConnectionRulesSet().isEmpty() || !getChildConnectionRulesSet().isEmpty()) {
            throw new DomainException("error.PartyType.has.connection.rules.cannot.delete");
        }
    }

    @Override
    public int compareTo(PartyType other) {
        int res = getType().compareTo(other.getType());
        return res != 0 ? res : getExternalId().compareTo(other.getExternalId());
    }

    @Atomic
    static public PartyType create(final PartyTypeBean bean) {
        return new PartyType(bean.getType(), bean.getName());
    }

    static public PartyType readBy(final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        for (final PartyType element : MyOrg.getInstance().getPartyTypesSet()) {
            if (element.hasType(type)) {
                return element;
            }
        }
        return null;
    }

    @Deprecated
    public java.util.Set<module.organization.domain.connectionRules.PartyTypeConnectionRule> getParentConnectionRules() {
        return getParentConnectionRulesSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.connectionRules.PartyTypeConnectionRule> getChildConnectionRules() {
        return getChildConnectionRulesSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.Party> getParties() {
        return getPartiesSet();
    }

}
