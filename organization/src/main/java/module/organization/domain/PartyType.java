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
import java.text.Collator;
import java.util.Comparator;

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
public class PartyType extends PartyType_Base implements Comparable<PartyType> {

    public static final Comparator<PartyType> COMPARATORY_BY_NAME = (o1, o2) -> {
        final int c = Collator.getInstance().compare(o1.getName().getContent(), o2.getName().getContent());
        return c == 0 ? o1.hashCode() - o2.hashCode() : c;
    };

    private PartyType() {
        super();
        setMyOrg(Bennu.getInstance());
    }

    public PartyType(final String type) {
        this(type, null);
    }

    public PartyType(final String type, final LocalizedString name) {
        this();
        check(type);
        setType(type);
        setName(name);
    }

    private void check(final String type) {
        if (type == null || type.isEmpty()) {
            throw new OrganizationDomainException("error.PartyType.invalid.type");
        }
        final PartyType partyType = readBy(type);
        if (partyType != null && partyType != this) {
            throw new OrganizationDomainException("error.PartyType.duplicated.type", type);
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
        setMyOrg(null);
        deleteDomainObject();
    }

    private void canDelete() {
        if (!getPartiesSet().isEmpty()) {
            throw new OrganizationDomainException("error.PartyType.has.parties.cannot.delete");
        }
        if (!getParentConnectionRulesSet().isEmpty() || !getChildConnectionRulesSet().isEmpty()) {
            throw new OrganizationDomainException("error.PartyType.has.connection.rules.cannot.delete");
        }
    }

    @Override
    public int compareTo(PartyType other) {
        int res = getType().compareTo(other.getType());
        return res != 0 ? res : getExternalId().compareTo(other.getExternalId());
    }

    @Atomic
    public static PartyType create(final String type, final LocalizedString name) {
        return new PartyType(type, name);
    }

    static public PartyType readBy(final String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        for (final PartyType element : Bennu.getInstance().getPartyTypesSet()) {
            if (element.hasType(type)) {
                return element;
            }
        }
        return null;
    }

}
