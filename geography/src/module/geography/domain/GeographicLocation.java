/*
 * @(#)GeographicLocation.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module for the MyOrg web application.
 *
 *   The Geography Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
 *
 *   The Geography Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Geography Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.geography.domain;

import java.util.Collection;

import module.organization.domain.AccountabilityType;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * Abstract location concept. If you can put it in a "where" sentence it should
 * derive from this.
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public abstract class GeographicLocation extends GeographicLocation_Base implements GeographicConstants {

    public GeographicLocation() {
	super();
    }

    public MultiLanguageString getName() {
	return getUnit().getPartyName();
    }

    public String getAcronym() {
	return getUnit().getAcronym();
    }

    public abstract MultiLanguageString getType();

    protected GeographicLocation getParentLocation() {
	Collection<Unit> parents = getUnit().getParentUnits(AccountabilityType.readBy(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME));
	if (parents.size() != 1)
	    throw new DomainException("error.geography.invalid-organizational-structure");
	return parents.iterator().next().getGeographicLocation();
    }

    protected Collection<Unit> getChildUnits() {
	return getUnit().getChildUnits(AccountabilityType.readBy(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME));
    }

    public static AccountabilityType getOrCreateAccountabilityType() {
	AccountabilityType geographic = null;
	for (AccountabilityType accountability : MyOrg.getInstance().getAccountabilityTypesSet()) {
	    if (accountability.getType().equals(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME)) {
		geographic = accountability;
	    }
	}
	if (geographic == null) {
	    geographic = AccountabilityType.create(new AccountabilityTypeBean(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME, makeName(
		    "Geográfico", GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME)));
	}
	return geographic;
    }

    protected static MultiLanguageString makeName(String pt, String en) {
	MultiLanguageString name = new MultiLanguageString();
	name.setContent(Language.pt, pt);
	name.setContent(Language.en, en);
	return name;
    }

    protected static PartyType getPartyType(String pt, String en) {
	for (PartyType partyType : MyOrg.getInstance().getPartyTypesSet()) {
	    if (partyType.getType().equals(en))
		return partyType;
	}
	return new PartyType(en, makeName(pt, en));
    }
}
