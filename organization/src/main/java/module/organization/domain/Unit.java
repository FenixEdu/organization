/*
 * @(#)Unit.java
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

import java.text.Collator;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 *
 * @author João Antunes
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Anil Kassamali
 * @author Sérgio Silva
 * @author Luis Cruz
 *
 */
public class Unit extends Unit_Base {

    public static final Comparator<Unit> COMPARATOR_BY_PRESENTATION_NAME = (unit1, unit2) -> {
        final int depth1 = unit1.depth();
        final int depth2 = unit2.depth();
        if (depth1 != depth2) {
            return depth1 - depth2;
        }

        final String name1 = unit1.getPresentationName();
        final String name2 = unit2.getPresentationName();
        final int c = Collator.getInstance().compare(name1, name2);
        if (c == 0) {
            final String acronym1 = unit1.getAcronym();
            final String acronym2 = unit2.getAcronym();
            if (acronym1 == null || acronym2 == null) {
                return unit2.hashCode() - unit1.hashCode();
            }
            final int a = Collator.getInstance().compare(acronym1, acronym2);
            return a == 0 ? unit2.hashCode() - unit1.hashCode() : a;
        }
        return c;
    };

    protected Unit() {
        super();
    }

    protected Unit(final Party parent, final LocalizedString name, final String acronym, final PartyType partyType,
            final AccountabilityType accountabilityType, final LocalDate begin, final LocalDate end,
            final OrganizationalModel organizationalModel, String accJustification) {
        this();

        if (partyType == null) {
            throw OrganizationDomainException.invalidPartyType();
        }
        check(name, acronym);

        addPartyTypes(partyType);
        setPartyName(name);
        setAcronym(acronym);

        if (parent != null) {
            if (accountabilityType == null) {
                throw OrganizationDomainException.invalidAccountabilityType();
            }
            Accountability.create(parent, this, accountabilityType, begin, end, accJustification);
        } else {
            setMyOrgFromTopUnit(Bennu.getInstance());
        }

        if (organizationalModel != null) {
            addOrganizationalModels(organizationalModel);
        }
    }

    private void check(final LocalizedString name, final String acronym) {
        if (name == null || name.isEmpty()) {
            throw OrganizationDomainException.emptyUnitName();
        }

        if (acronym == null /* || acronym.isEmpty() */) {
            throw OrganizationDomainException.emptyUnitAcronym();
        }
    }

    @Override
    final public boolean isUnit() {
        return true;
    }

    @Override
    public boolean isTop() {
        return getParentAccountabilitiesSet().isEmpty() && getMyOrgFromTopUnit() != null;
    }

    @Atomic
    public Unit edit(final LocalizedString name, final String acronym) {
        check(name, acronym);
        setPartyName(name);
        setAcronym(acronym);

        if (!accountabilitiesStillValid()) {
            throw OrganizationDomainException.cannotEditInvalidUnit();
        }

        return this;
    }

    @Override
    protected void disconnect() {
        setMyOrgFromTopUnit(null);
        super.disconnect();
    }

    @Atomic
    static public Unit create(final UnitBean bean) {
        return create(bean.getParent(), bean.getName(), bean.getAcronym(), bean.getPartyType(), bean.getAccountabilityType(),
                bean.getBegin(), bean.getEnd(), bean.getOrganizationalModel());
    }

    @Deprecated
    public static Unit create(Party parent, LocalizedString name, String acronym, PartyType partyType,
            AccountabilityType accountabilityType, LocalDate begin, LocalDate end) {
        return create(parent, name, acronym, partyType, accountabilityType, begin, end, null, null);
    }

    public static Unit create(Party parent, LocalizedString name, String acronym, PartyType partyType,
            AccountabilityType accountabilityType, LocalDate begin, LocalDate end, String accJustification) {
        return create(parent, name, acronym, partyType, accountabilityType, begin, end, null, accJustification);

    }

    /**
     *
     * @param parent the parent unit, whose relation will be established using the provided accountabilityType
     * @param name
     * @param acronym
     * @param partyType
     * @param accountabilityType the accountabilityType to connect to the parent unit
     * @param begin the begin date of the accountability
     * @param end the end date, or null, if the accountability end date remains to be closed
     * @param organizationalModel if provided, the newly created unit will be added as a top unit to the given
     *            organizationalModel.
     * @deprecated use
     *             {@link #create(Party, LocalizedString, String, PartyType, AccountabilityType, LocalDate, LocalDate, OrganizationalModel, String)}
     *             instead
     * @return
     */
    @Deprecated
    public static Unit create(Party parent, LocalizedString name, String acronym, PartyType partyType,
            AccountabilityType accountabilityType, LocalDate begin, LocalDate end, OrganizationalModel organizationalModel) {
        return Unit.create(parent, name, acronym, partyType, accountabilityType, begin, end, organizationalModel, null);
    }

    /**
     * @param parent the parent unit, whose relation will be established using the provided accountabilityType
     * @param name
     * @param acronym
     * @param partyType
     * @param accountabilityType the accountabilityType to connect to the parent unit
     * @param begin the begin date of the accountability
     * @param end the end date, or null, if the accountability end date remains to be closed
     * @param organizationalModel if provided, the newly created unit will be added as a top unit to the given
     *            organizationalModel.
     * @param justification an information justification/reason for the change of accountability, or null if there is none, or
     *            none is provided
     * @return
     */
    @Atomic
    public static Unit create(Party parent, LocalizedString name, String acronym, PartyType partyType,
            AccountabilityType accountabilityType, LocalDate begin, LocalDate end, OrganizationalModel organizationalModel,
            String justification) {
        return new Unit(parent, name, acronym, partyType, accountabilityType, begin, end, organizationalModel, justification);
    }

    @Atomic
    static public Unit createRoot(final UnitBean bean) {
        return createRoot(bean.getName(), bean.getAcronym(), bean.getPartyType(), bean.getAccountabilityJustification());
    }

    /**
     *
     * @param name
     * @param acronym
     * @param partyType
     * @deprecated use {@link #createRoot(LocalizedString, String, PartyType, String)} instead
     * @return
     */
    @Deprecated
    static public Unit createRoot(final LocalizedString name, final String acronym, final PartyType partyType) {
        return new Unit(null, name, acronym, partyType, null, new LocalDate(), null, null, null);
    }

    @Atomic
    static public Unit createRoot(final LocalizedString name, final String acronym, final PartyType partyType,
            String accJustification) {
        return new Unit(null, name, acronym, partyType, null, new LocalDate(), null, null, accJustification);
    }

    @Override
    public String getPresentationName() {
        return getAcronym() == null || getAcronym().isEmpty() ? super.getPresentationName() : super.getPresentationName() + " ("
                + getAcronym() + ')';
    }

    public void closeAllParentAccountabilitiesByType(final AccountabilityType accountabilityType) {
        final LocalDate now = new LocalDate();
        for (final Accountability accountability : getParentAccountabilitiesSet()) {
            if (accountability.getEndDate() == null || accountability.getEndDate().isAfter(now)) {
                accountability.setEndDate(now);
            }
        }
    }

    @Override
    protected Party findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(final AccountabilityType accountabilityType,
            final PartyType partyType, final String acronym) {
        if (getPartyTypesSet().contains(partyType) && acronym.equals(getAcronym())) {
            return this;
        }
        for (final Accountability accountability : getChildAccountabilitiesSet()) {
            if (accountability.getAccountabilityType() == accountabilityType) {
                final Party party =
                        accountability.getChild().findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(accountabilityType,
                                partyType, acronym);
                if (party != null) {
                    return party;
                }
            }
        }
        return null;
    }

    private int depth() {
        return depth(new HashSet<Accountability>());
    }

    private int depth(final Set<Accountability> processed) {
        int depth = 0;
        if (!getOrganizationalModelsSet().isEmpty()) {
            return depth;
        }
        for (final Accountability accountability : getParentAccountabilitiesSet()) {
            if (!processed.contains(accountability)) {
                processed.add(accountability);
                final Party party = accountability.getParent();
                if (party.isUnit()) {
                    final Unit unit = (Unit) party;
                    final int parentDepth = unit.depth(processed);
                    if (parentDepth > depth) {
                        depth = parentDepth;
                    }
                }
            }
        }
        return depth + 1;
    }

    public Set<User> getMembers(final Set<AccountabilityType> accountabilityTypes) {
        final Set<User> result = new HashSet<User>();
        getMembers(result, accountabilityTypes);
        return result;
    }

    protected void getMembers(final Set<User> result, final Set<AccountabilityType> accountabilityTypes) {
        for (final Accountability accountability : getChildAccountabilitiesSet()) {
            final AccountabilityType accountabilityType = accountability.getAccountabilityType();
            if (accountabilityTypes.contains(accountabilityType) && accountability.isActiveNow()) {
                final Party child = accountability.getChild();
                if (child.isPerson()) {
                    final Person person = (Person) child;
                    if (person.getUser() != null) {
                        result.add(person.getUser());
                    }
                } else if (child.isUnit()) {
                    final Unit unit = (Unit) child;
                    unit.getMembers(result, accountabilityTypes);
                } else {
                    throw OrganizationDomainException.unknownPartyType();
                }
            }
        }
    }

    @Deprecated
    public java.util.Set<module.organization.domain.groups.PersistentUnitGroup> getUnitGroup() {
        return getUnitGroupSet();
    }

}
