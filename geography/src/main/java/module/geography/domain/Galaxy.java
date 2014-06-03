/*
 * @(#)Galaxy.java
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
 * Galaxies. Some are far far away.
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public class Galaxy extends Galaxy_Base {
    public Galaxy(Universe parent, LocalizedString name, String acronym) {
        super();
        setUnit(Unit.create(parent.getUnit(), name, acronym, getPartyType("Galáxia", GALAXY_PARTYTYPE_NAME),
                getOrCreateAccountabilityType(), new LocalDate(), null));
    }

    @Override
    public LocalizedString getType() {
        return new LocalizedString().with(new Locale("pt"), "Galáxia").with(Locale.ENGLISH, GALAXY_PARTYTYPE_NAME);
    }

    public Universe getParent() {
        return (Universe) getParentLocation();
    }

    public Collection<Planet> getChildren() {
        Collection<Unit> units = getChildUnits();
        Collection<Planet> children = new ArrayList<Planet>();
        for (Unit unit : units) {
            children.add((Planet) unit.getGeographicLocation());
        }
        return children;
    }

    public Planet getChildByAcronym(String acronym) {
        for (Unit unit : getChildUnits()) {
            if (unit.getAcronym().equals(acronym)) {
                return (Planet) unit.getGeographicLocation();
            }
        }
        return null;
    }

    public static Galaxy getMilkyWay() {
        return Universe.getMultiverseZero().getChildByAcronym(MILKY_WAY_UNIT_ACRONYM);
    }
}
