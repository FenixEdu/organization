/*
 * @(#)Unit.java
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

import java.text.Collator;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import myorg.domain.MyOrg;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Unit extends Unit_Base {

    public static final Comparator<Unit> COMPARATOR_BY_PRESENTATION_NAME = new Comparator<Unit>() {
	@Override
	public int compare(final Unit unit1, Unit unit2) {
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
	}
    };

    protected Unit() {
	super();
    }

    protected Unit(final Party parent, final MultiLanguageString name, final String acronym, final PartyType partyType,
	    final AccountabilityType accountabilityType, final LocalDate begin, final LocalDate end,
	    final OrganizationalModel organizationalModel) {
	this();

	check(partyType, "error.Unit.invalid.party.type");
	check(name, acronym);

	addPartyTypes(partyType);
	setPartyName(name);
	setAcronym(acronym);

	if (parent != null) {
	    check(accountabilityType, "error.Unit.invalid.accountability.type");
	    Accountability.create(parent, this, accountabilityType, begin, end);
	} else {
	    setMyOrgFromTopUnit(MyOrg.getInstance());
	}

	if (organizationalModel != null) {
	    addOrganizationalModels(organizationalModel);
	}
    }

    private void check(final MultiLanguageString name, final String acronym) {
	if (name == null || name.isEmpty()) {
	    throw new DomainException("error.Unit.invalid.name");
	}

	if (acronym == null /* || acronym.isEmpty() */ ) {
	    throw new DomainException("error.Unit.invalid.acronym");
	}
    }

    @Override
    final public boolean isUnit() {
	return true;
    }

    @Override
    public boolean isTop() {
	return !hasAnyParentAccountabilities() && hasMyOrgFromTopUnit();
    }

    @Service
    public Unit edit(final MultiLanguageString name, final String acronym) {
	check(name, acronym);
	setPartyName(name);
	setAcronym(acronym);

	if (!accountabilitiesStillValid()) {
	    throw new DomainException("error.Unit.invalid.accountabilities.cannot.edit.information");
	}

	return this;
    }

    @Override
    protected void disconnect() {
	removeMyOrgFromTopUnit();
	super.disconnect();
    }

    @Service
    static public Unit create(final UnitBean bean) {
	return create(bean.getParent(), bean.getName(), bean.getAcronym(), bean.getPartyType(), bean.getAccountabilityType(),
		bean.getBegin(), bean.getEnd(), bean.getOrganizationalModel());
    }

    public static Unit create(Party parent, MultiLanguageString name, String acronym, PartyType partyType,
	    AccountabilityType accountabilityType, LocalDate begin, LocalDate end) {
	return create(parent, name, acronym, partyType, accountabilityType, begin, end, null);
    }

    @Service
    public static Unit create(Party parent, MultiLanguageString name, String acronym, PartyType partyType,
	    AccountabilityType accountabilityType, LocalDate begin, LocalDate end, OrganizationalModel organizationalModel) {
	return new Unit(parent, name, acronym, partyType, accountabilityType, begin, end, organizationalModel);
    }

    @Service
    static public Unit createRoot(final UnitBean bean) {
	return createRoot(bean.getName(), bean.getAcronym(), bean.getPartyType());
    }

    @Service
    static public Unit createRoot(final MultiLanguageString name, final String acronym, final PartyType partyType) {
	return new Unit(null, name, acronym, partyType, null, new LocalDate(), null, null);
    }

    @Override
    public String getPresentationName() {
	return getAcronym() == null || getAcronym().isEmpty() ? super.getPresentationName() : super.getPresentationName() + " (" + getAcronym() + ')';
    }

    public void closeAllParentAccountabilitiesByType(final AccountabilityType accountabilityType) {
	final LocalDate now = new LocalDate();
	for (final Accountability accountability : getParentAccountabilitiesSet()) {
	    if (accountability.getEndDate() == null || accountability.getEndDate().isAfter(now)) {
		accountability.editDates(accountability.getBeginDate(), now);
	    }
	}
    }

    @Override
    protected Party findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(final AccountabilityType accountabilityType,
	    final PartyType partyType, final String acronym) {
	if (hasPartyTypes(partyType) && acronym.equals(getAcronym())) {
	    return this;
	}
	for (final Accountability accountability : getChildAccountabilitiesSet()) {
	    if (accountability.getAccountabilityType() == accountabilityType) {
		final Party party = accountability.getChild().findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(accountabilityType, partyType, acronym);
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
	if (hasAnyOrganizationalModels()) {
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
		    if (person.hasUser()) {
			result.add(person.getUser());
		    }
		} else if (child.isUnit()) {
		    final Unit unit = (Unit) child;
		    unit.getMembers(result, accountabilityTypes);
		} else {
		    throw new DomainException("unknown.party.type");
		}
	    }
	}
    }

}
