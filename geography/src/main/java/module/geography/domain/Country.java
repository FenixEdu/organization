/*
 * @(#)Country.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: Pedro Santos
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module.
 *
 *   The Geography Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
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
import java.util.Locale;

import module.geography.domain.exception.GeographyDomainException;
import module.geography.util.AddressPrinter;
import module.organization.domain.Accountability;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 * A country. Holds information about its subdivisions as districts, states, or
 * whatever, since it varies from one country to another.
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * @author Luis Cruz
 * @author Pedro Amaral
 * 
 */
public class Country extends Country_Base {

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
            LocalizedString name, LocalizedString nationality, Class<AddressPrinter> iAddressPrinter) {
        super();
        setBennu(Bennu.getInstance());
        setUnit(Unit.create(parent.getUnit(), name, iso3166alpha3Code, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
                getOrCreateAccountabilityType(), new LocalDate(), null));
        setIso3166alpha2Code(iso3166alpha2Code);
        setIAddressPrinter(iAddressPrinter);
        setIso3166alpha3Code(iso3166alpha3Code);
        setIso3166numericCode(iso3166numericCode);
        setNationality(nationality);
    }

    public Country(Planet parent, LocalizedString name, String acronym, Class iAddressPrinter,
            CountrySubdivisionLevelName... subdivisionNames) {
        super();
        setBennu(Bennu.getInstance());

        setIAddressPrinter(iAddressPrinter);
        setUnit(Unit.create(parent.getUnit(), name, acronym, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
                getOrCreateAccountabilityType(), new LocalDate(), null));
        for (CountrySubdivisionLevelName subdivisionName : subdivisionNames) {
            addLevelName(subdivisionName);
        }
    }

    @Override
    public void setIAddressPrinter(Class iAddressPrinter) {
        if (!AddressPrinter.class.isAssignableFrom(iAddressPrinter)) {
            throw new GeographyDomainException("error.invalid.iaddressprinter");
        }
        super.setIAddressPrinter(iAddressPrinter);
    }

    @Override
    public LocalizedString getType() {
        return new LocalizedString().with(new Locale("pt"), "País").with(Locale.ENGLISH, COUNTRY_PARTYTYPE_NAME);
    }

    @Atomic
    public AddressPrinter getAddressPrinter() {
        if (super.getIAddressPrinter() == null) {
            setIAddressPrinter(AddressPrinter.class);
        }

        AddressPrinter ap = null;
        try {
            ap = (AddressPrinter) super.getIAddressPrinter().getConstructor().newInstance();
        } catch (Exception e) {
            throw new GeographyDomainException("error.instance.iaddressprinter", e);
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
        return getLevelName().size();
    }

    // TODO check to see if the part of removing the PhysicalAddress and
    // everything which this module doesn't depend is required
    @Deprecated
    public void delete() {
        Unit unit = this.getUnit();
        setUnit(null);
        setBennu(null);
        unit.delete();
        deleteDomainObject();
    }

    public LocalizedString getSubdivisionLevelName(Integer level) {
        for (CountrySubdivisionLevelName levelName : getLevelNameSet()) {
            if (levelName.getLevel().equals(level)) {
                return levelName.getName();
            }
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
     * @param level
     *            the level to get
     * @return returns the {@link CountrySubdivisionLevelName} associated with
     *         the given level or null if it doesn't exist
     */
    public CountrySubdivisionLevelName getCountrySubdivisionLevel(int level) {
        for (CountrySubdivisionLevelName countrySubdivisionLevelName : getLevelName()) {
            if (countrySubdivisionLevelName.getLevel() == level) {
                return countrySubdivisionLevelName;
            }
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
     * @param date
     *            the date which they should have been active
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
                }
                return geographicLocation;
            }
        }
        return null;
    }

    public static Country getPortugal() {
        return findByAcronym(PORTUGAL_UNIT_ACRONYM);
    }

    public static Country findByAcronym(String acronym) {
        for (Country country : Bennu.getInstance().getCountriesSet()) {
            if (country.getAcronym().equalsIgnoreCase(acronym)) {
                return country;
            }
        }
        return null;
    }

    public static Country findByIso3166alpha2Code(String code) {
        for (Country country : Bennu.getInstance().getCountriesSet()) {
            if (country.getIso3166alpha2Code().equalsIgnoreCase(code)) {
                return country;
            }
        }
        return null;
    }

    public static Country findByIso3166alpha3Code(String code) {
        for (Country country : Bennu.getInstance().getCountriesSet()) {
            if (country.getIso3166alpha3Code().equalsIgnoreCase(code)) {
                return country;
            }
        }
        return null;
    }

    public static Country findByName(String name) {
        for (Country country : Bennu.getInstance().getCountriesSet()) {
            for (Locale locale : country.getName().getLocales()) {
                if (country.getName().getContent(locale).equalsIgnoreCase(name)) {
                    return country;
                }
            }
        }
        return null;
    }

    public void setSubdivisionLevelName(Integer level, LocalizedString levelName, Boolean isLabel) {
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

    public void update(Planet parent, String iso3166alpha2Code, String iso3166alpha3Code, Integer iso3166numericCode,
            LocalizedString name, LocalizedString nationality, Class<AddressPrinter> iAddressPrinter) {
        if (!same(getIso3166alpha2Code(), iso3166alpha2Code) || !same(getIso3166alpha3Code(), iso3166alpha3Code)
                || !same(getIso3166numericCode(), iso3166numericCode) || !same(getName(), name)
                || !same(getNationality(), nationality) || !same(getIAddressPrinter(), iAddressPrinter)) {
            for (Accountability accountability : getUnit().getParentAccountabilities(getOrCreateAccountabilityType())) {
                accountability.editDates(new LocalDate(), null);
            }
            setUnit(Unit.create(parent.getUnit(), name, iso3166alpha3Code, getPartyType("País", COUNTRY_PARTYTYPE_NAME),
                    getOrCreateAccountabilityType(), new LocalDate(), null));
            setIso3166alpha2Code(iso3166alpha2Code);
            setIAddressPrinter(iAddressPrinter);
            setIso3166alpha3Code(iso3166alpha3Code);
            setIso3166numericCode(iso3166numericCode);
            setNationality(nationality);
        }
    }

    private static boolean same(Object one, Object two) {
        if (one == null) {
            return two == null;
        }
        return one.equals(two);
    }

    @Deprecated
    public java.util.Set<module.geography.domain.CountrySubdivisionLevelName> getLevelName() {
        return getLevelNameSet();
    }

}
