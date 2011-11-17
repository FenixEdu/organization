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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import module.geography.util.AddressPrinter;
import module.geography.util.StringsUtil;
import module.organization.domain.Accountability;
import module.organization.domain.Unit;
import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * A country. Holds information about its subdivisions as districts, states, or
 * whatever, since it varies from one country to another.
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class Country extends Country_Base implements GeographicConstants {

    public static final Comparator<Country> COMPARATOR_BY_NAME = new Comparator<Country>() {
	@Override
	public int compare(final Country country1, Country country2) {
	    final String name1 = country1.getName().getContent();
	    final String name2 = country2.getName().getContent();
	    final int c = Collator.getInstance().compare(name1, name2);
	    if (c == 0) {
		final String acronym1 = country1.getAcronym();
		final String acronym2 = country2.getAcronym();
		if (acronym1 == null || acronym2 == null) {
		    return country2.hashCode() - country1.hashCode();
		}
		final int a = Collator.getInstance().compare(acronym1, acronym2);
		return a == 0 ? country2.hashCode() - country1.hashCode() : a;
	    }
	    return c;
	}
    };

    public Country(Planet parent, String iso3166alpha2Code, String iso3166alpha3Code, Integer iso3166numericCode,
	    MultiLanguageString name, MultiLanguageString nationality, Class iAddressPrinter) {
	super();
	setMyOrg(MyOrg.getInstance());
	setUnit(Unit.create(parent.getUnit(), name, iso3166alpha3Code, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
		getOrCreateAccountabilityType(), new LocalDate(), null));
	setIso3166alpha2Code(iso3166alpha2Code);
	setIAddressPrinter(iAddressPrinter);
	setIso3166alpha3Code(iso3166alpha3Code);
	setIso3166numericCode(iso3166numericCode);
	setNationality(nationality);
    }

    public Country(Planet parent, MultiLanguageString name, String acronym, Class iAddressPrinter,
	    CountrySubdivisionLevelName... subdivisionNames) {
	super();
	setMyOrg(MyOrg.getInstance());

	setIAddressPrinter(iAddressPrinter);
	setUnit(Unit.create(parent.getUnit(), name, acronym, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
		getOrCreateAccountabilityType(), new LocalDate(), null));
	for (CountrySubdivisionLevelName subdivisionName : subdivisionNames) {
	    addLevelName(subdivisionName);
	}
    }

    @Override
    public void setIAddressPrinter(Class iAddressPrinter) {
	if (!AddressPrinter.class.isAssignableFrom(iAddressPrinter))
	    throw new DomainException("error.invalid.iaddressprinter");
	super.setIAddressPrinter(iAddressPrinter);
    }

    @Override
    public MultiLanguageString getType() {
	return StringsUtil.makeName("País", COUNTRY_PARTYTYPE_NAME);
    }

    @Service
    public AddressPrinter getAddressPrinter() {
	if (super.getIAddressPrinter() == null) {
	    setIAddressPrinter(AddressPrinter.class);
	}

	AddressPrinter ap = null;
	try {
	    ap = (AddressPrinter) super.getIAddressPrinter().getConstructor().newInstance();
	} catch (Exception e) {
	    throw new DomainException("error.instance.iaddressprinter", e);
	}

	return ap;
	// try {
	// return (AddressPrinter)
	// super.getIAddressPrinter().getConstructor().newInstance();
	// } catch (Exception e) {
	// setIAddressPrinter(GeneralIAddressPrinter.class);
	// throw new DomainException("error.instance.iaddressprinter", e);
	// }
    }

    /**
     * How many levels of sub-administrative divisions this country has
     * 
     * @return the number.
     */
    public int getSubdivisionDepth() {
	return getLevelNameCount();
    }

    // TODO check to see if the part of removing the PhysicalAddress and
    // everything which this module doesn't depend is required
    @Deprecated
    public void delete() {
	Unit unit = this.getUnit();
	removeUnit();
	removeMyOrg();
	unit.delete();
	deleteDomainObject();
    }

    public MultiLanguageString getSubdivisionLevelName(Integer level) {
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

    /**
     * 
     * @param level the level to get
     * @return returns the {@link CountrySubdivisionLevelName} associated with
     *         the given level or null if it doesn't exist
     */
    public CountrySubdivisionLevelName getCountrySubdivisionLevel(int level) {
	for (CountrySubdivisionLevelName countrySubdivisionLevelName : getLevelName()) {
	    if (countrySubdivisionLevelName.getLevel() == level)
		return countrySubdivisionLevelName;
	}
	return null;

    }

    /**
     * This method is useful because the subdivisions can change with time and
     * these changes are tracked with the accountability
     * 
     * @return the country subdivisions which are actually valid for the current
     *         DateTime (it uses the accountability to check for this).
     * 
     */
    public Collection<CountrySubdivision> getCurrentChildren() {
	return getChildrenValidAt(new LocalDate());
    }

    /**
     * 
     * @param date the date which they should have been active
     * @return a {@link Collection} with the {@link CountrySubdivision} that
     *         were active on the given date and of the
     */
    public Collection<CountrySubdivision> getChildrenValidAt(LocalDate date) {
	Collection<Unit> units = getChildUnits();
	Collection<CountrySubdivision> children = new ArrayList<CountrySubdivision>();
	for (Unit unit : units) {
	    for (Accountability accountability : unit.getParentAccountabilities(getOrCreateAccountabilityType())) {
		if (accountability.isActive(date)) {
		    children.add((CountrySubdivision) unit.getGeographicLocation());
		}
	    }
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
	    if (country.getAcronym().equalsIgnoreCase(acronym))
		return country;
	}
	return null;
    }

    public void setSubdivisionLevelName(Integer level, MultiLanguageString levelName, Boolean isLabel) {
	CountrySubdivisionLevelName countrySubdivisionLevelNameToAlter = null;
	for (CountrySubdivisionLevelName subdivisionLevel : getLevelNameSet()) {
	    if (subdivisionLevel.getLevel() == level) {
		countrySubdivisionLevelNameToAlter = subdivisionLevel;
	    }
	}
	if (countrySubdivisionLevelNameToAlter == null) {
	    countrySubdivisionLevelNameToAlter = new CountrySubdivisionLevelName(level, levelName);
	    this.addLevelName(countrySubdivisionLevelNameToAlter);
	} else {
	    countrySubdivisionLevelNameToAlter.setName(levelName);
	}

    }
}
