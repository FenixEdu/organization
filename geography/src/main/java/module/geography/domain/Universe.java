/*
 * @(#)Universe.java
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

import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.commons.i18n.LocalizedString;

import module.organization.domain.Unit;

/**
 * Universes. Some claim that these is more than one Universe, and each decision
 * you make creates a whole new one. There are also people that claim to be
 * Napoleon Bonaparte, we throw those in mental hospitals.
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public class Universe extends Universe_Base implements GeographicConstants {

    public Universe(LocalizedString name, String acronym) {
        super();
        setUnit(Unit.createRoot(name, acronym, getPartyType("Universo", UNIVERSE_PARTYTYPE_NAME), null));
    }

    @Override
    public LocalizedString getType() {
        return new LocalizedString().with(new Locale("pt"), "Universo").with(Locale.ENGLISH, UNIVERSE_PARTYTYPE_NAME);
    }

    public Collection<Galaxy> getChildren() {
        Stream<Unit> units = getChildUnits();
        return units.map(u -> (Galaxy) u.getGeographicLocation()).collect(Collectors.toList());
    }

    public Galaxy getChildByAcronym(String acronym) {
        return getChildUnits().filter(u -> u.getAcronym().equals(acronym)).map(u -> (Galaxy) u.getGeographicLocation()).findAny()
                .orElse(null);
    }

    public static Universe getMultiverseZero() {
        Set<Unit> tops = Bennu.getInstance().getTopUnitsSet();
        for (Unit unit : tops) {
            if (unit.getAcronym().equals(MULTIVERSE_UNIT_ACRONYM)) {
                return (Universe) unit.getGeographicLocation();
            }
        }
        return null;
    }
}
