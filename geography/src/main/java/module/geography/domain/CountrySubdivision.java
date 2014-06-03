/*
 * @(#)CountrySubdivision.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;

import module.geography.domain.exception.GeographyDomainException;
import module.organization.domain.Accountability;
import module.organization.domain.Unit;

import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 * A {@link Country} subdivision of some level of jurisdiction. Subdivisions are
 * assumed to be Trees, each division has only one parent division, or country
 * (if it's the top level).
 * 
 * @author João Antunes
 * @author Pedro Santos
 * @author Bruno Santos
 * 
 */
public class CountrySubdivision extends CountrySubdivision_Base {
    protected CountrySubdivision() {
        super();
    }

    public static final Comparator<CountrySubdivision> COMPARATOR_BY_LEVEL = new Comparator<CountrySubdivision>() {
        @Override
        public int compare(final CountrySubdivision location1, CountrySubdivision location2) {
            return location1.getLevel().compareTo(location2.getLevel());
        }
    };

    public CountrySubdivision(Country parent, String name, String acronym, String code) {
        this(parent.getUnit(), 1, name, acronym, code);
    }

    public CountrySubdivision(CountrySubdivision parent, String name, String acronym, String code) {
        this(parent.getUnit(), parent.getLevel() + 1, name, acronym, code);
    }

    private CountrySubdivision(Unit parent, Integer level, String name, String acronym, String code) {
        this();
        setUnit(Unit.create(parent, new LocalizedString().with(new Locale("pt"), name).with(Locale.ENGLISH, name), acronym,
                getPartyType("Subdivisão de País", COUNTRY_SUBDIVISION_PARTYTYPE_NAME), getOrCreateAccountabilityType(),
                new LocalDate(), null));
        setLevel(level);
        setCode(code);
    }

    @Override
    public LocalizedString getType() {
        return getLevelName();
    }

    public LocalizedString getLevelName() {
        return getCountry().getSubdivisionLevelName(getLevel());
    }

    public void setLevelName(LocalizedString levelName, Boolean isLabel) {
        getCountry().setSubdivisionLevelName(getLevel(), levelName, isLabel);
    }

    @Override
    public Country getCountry() {
        if (getLevel() == 1) {
            return (Country) getParentLocation();
        }
        return getParentSubdivision().getCountry();
    }

    public CountrySubdivision getParentSubdivision() {
        if (getLevel() == 1) {
            throw new GeographyDomainException("error.geography.requesting-parent-subdivision-at-level-one");
        }
        return (CountrySubdivision) getParentLocation();
    }

    /**
     * 
     * @return the CountrySubdivision {@link CountrySubdivision} objects that
     *         are on the same level and that are currently active
     */
    public Collection<CountrySubdivision> getCurrentSiblings() {
        if (getLevel() > 1) {
            return getParentSubdivision().getCurrentChildren();
        } else {
            return getCountry().getCurrentChildren();
        }

    }

    /**
     * @return the children which are currently active
     */
    public Collection<CountrySubdivision> getCurrentChildren() {
        return getChildren(new LocalDate());

    }

    /**
     * Gets the children that are valid at the given time
     * 
     * @param date
     *            the date where they should be vaild to be returned
     * @return a collection with the active children at the given time {@link Accountability}
     */
    public Collection<CountrySubdivision> getChildren(LocalDate date) {
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
        for (CountrySubdivision subdivision : getCurrentChildren()) {
            if (subdivision.getCode().equals(code)) {
                if (codes.length > 1) {
                    return subdivision.getChildByCode(Arrays.asList(codes).subList(1, codes.length).toArray(new String[0]));
                } else {
                    return subdivision;
                }
            }
        }
        return null;
    }

    /**
     * Deletes this element implementing the domain rules
     */
    @Atomic
    public void delete() {
        Unit unit = this.getUnit();
        setUnit(null);
        for (Accountability accountability : unit.getChildAccountabilities()) {
            unit.removeChildAccountabilities(accountability);
        }
        unit.delete();
        this.deleteDomainObject();

    }

    public LocalizedString getFullName() {
        if (getLevel() == 1) {
            return getName();
        }
        return getName().append(", ").append(getParentSubdivision().getFullName());
    }

    protected String getExtendedName() {
        return "[" + getCode() + "-" + getName().getContent() + "]";
    }

    @Override
    public String toString() {
        if (getLevel() == 1) {
            return getExtendedName();
        }
        return getParentSubdivision().toString() + " " + getExtendedName();
    }
}
