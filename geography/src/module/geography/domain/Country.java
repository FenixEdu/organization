/*
 * @(#)Country.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import module.organization.domain.Unit;
import myorg.domain.MyOrg;

import org.joda.time.LocalDate;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * A country. Holds information about its subdivisions as districts, states, or
 * whatever, since it varies from one country to another.
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class Country extends Country_Base implements GeographicConstants {

    public Country(Planet parent, String iso3166alpha2Code, String iso3166alpha3Code, Integer iso3166numericCode,
	    MultiLanguageString name, MultiLanguageString nationality) {
	super();
	setMyOrg(MyOrg.getInstance());
	setUnit(Unit.create(parent.getUnit(), name, iso3166alpha3Code, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
		getOrCreateAccountabilityType(), new LocalDate(), null));
	setIso3166alpha2Code(iso3166alpha2Code);
	setIso3166alpha3Code(iso3166alpha3Code);
	setIso3166numericCode(iso3166numericCode);
	setNationality(nationality);
    }

    public Country(Planet parent, MultiLanguageString name, String acronym, CountrySubdivisionLevelName... subdivisionNames) {
	super();
	setMyOrg(MyOrg.getInstance());
	setUnit(Unit.create(parent.getUnit(), name, acronym, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
		getOrCreateAccountabilityType(), new LocalDate(), null));
	for (CountrySubdivisionLevelName subdivisionName : subdivisionNames) {
	    addLevelName(subdivisionName);
	}
    }

    @Override
    public MultiLanguageString getType() {
	return makeName("País", COUNTRY_PARTYTYPE_NAME);
    }

    /**
     * How many levels of sub-administrative divisions this country has
     * 
     * @return the number.
     */
    public int getSubdivisionDepth() {
	return getLevelNameCount();
    }

    MultiLanguageString getSubdivisionLevelName(Integer level) {
	for (CountrySubdivisionLevelName levelName : getLevelNameSet()) {
	    if (levelName.getLevel().equals(level))
		return levelName.getName();
	}
	return null;
    }

    public Planet getParent() {
	return (Planet) getParentLocation();
    }

    public Collection<CountrySubdivision> getChildren() {
	Collection<Unit> units = getChildUnits();
	Collection<CountrySubdivision> children = new ArrayList<CountrySubdivision>();
	for (Unit unit : units) {
	    children.add((CountrySubdivision) unit.getGeographicLocation());
	}
	return children;
    }

    public CountrySubdivision getChildByAcronym(String acronym) {
	for (Unit unit : getChildUnits()) {
	    if (unit.getAcronym().equals(acronym)) {
		return (CountrySubdivision) unit.getGeographicLocation();
	    }
	}
	return null;
    }

    public CountrySubdivision getChildByCode(String... codes) {
	String code = codes[0];
	for (Unit subdivision : getChildUnits()) {
	    CountrySubdivision geographicLocation = (CountrySubdivision) subdivision.getGeographicLocation();
	    if (geographicLocation.getCode().equals(code)) {
		if (codes.length > 1) {
		    return geographicLocation
			    .getChildByCode(Arrays.asList(codes).subList(1, codes.length).toArray(new String[0]));
		} else {
		    return geographicLocation;
		}
	    }
	}
	return null;
    }

    public static Country getPortugal() {
	return findByAcronym(PORTUGAL_UNIT_ACRONYM);
    }

    public static Country findByAcronym(String acronym) {
	for (Country country : MyOrg.getInstance().getCountriesSet()) {
	    if (country.getAcronym().equals(acronym))
		return country;
	}
	return null;
    }
}
