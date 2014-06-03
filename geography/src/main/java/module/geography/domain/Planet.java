/*
 * @(#)Planet.java
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
import java.util.Collection;
import java.util.Locale;

import module.organization.domain.Unit;

import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

/**
 * Planets. Some have intelligent life in them, most of which would like it
 * widespread.
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public class Planet extends Planet_Base {
    public Planet(Galaxy parent, LocalizedString name, String acronym) {
        super();
        setUnit(Unit.create(parent.getUnit(), name, acronym, getPartyType("Planeta", PLANET_PARTYTYPE_NAME),
                getOrCreateAccountabilityType(), new LocalDate(), null));

    }

    @Override
    public LocalizedString getType() {
        return new LocalizedString().with(new Locale("pt"), "Planeta").with(Locale.ENGLISH, PLANET_PARTYTYPE_NAME);
    }

    public Galaxy getParent() {
        return (Galaxy) getParentLocation();
    }

    public Collection<Country> getChildren() {
        Collection<Unit> units = getChildUnits();
        Collection<Country> children = new ArrayList<Country>();
        for (Unit unit : units) {
            children.add((Country) unit.getGeographicLocation());
        }
        return children;
    }

    public Country getChildByAcronym(String acronym) {
        for (Unit unit : getChildUnits()) {
            if (unit.getAcronym().equals(acronym)) {
                return (Country) unit.getGeographicLocation();
            }
        }
        return null;
    }

    public static Planet getEarth() {
        return Galaxy.getMilkyWay().getChildByAcronym(EARTH_UNIT_ACRONYM);
    }
}
