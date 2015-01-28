/*
 * @(#)Magrathea.java
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
package module.geography.domain.task;

import java.util.Locale;

import module.geography.domain.Galaxy;
import module.geography.domain.GeographicConstants;
import module.geography.domain.Planet;
import module.geography.domain.Universe;

import org.fenixedu.commons.i18n.LocalizedString;

import pt.ist.fenixframework.Atomic;

/**
 * Planet Factory.
 * 
 * http://en.wikipedia.org/wiki/Places_in_The_Hitchhiker%27s_Guide_to_the_Galaxy
 * #Magrathea
 * 
 * @author Pedro Santos
 * 
 */
public class Magrathea implements GeographicConstants {
    @Atomic
    public static Planet buildEarth() {
        Universe universe = Universe.getMultiverseZero();
        if (universe == null) {
            universe =
                    new Universe(new LocalizedString().with(new Locale("pt"), "Multiverso Zero").with(Locale.ENGLISH,
                            "Multiverse Zero"), MULTIVERSE_UNIT_ACRONYM);
        }

        Galaxy galaxy = universe.getChildByAcronym(MILKY_WAY_UNIT_ACRONYM);
        if (galaxy == null) {
            galaxy =
                    new Galaxy(universe, new LocalizedString().with(new Locale("pt"), "Via Láctea").with(Locale.ENGLISH,
                            "Milky Way"), MILKY_WAY_UNIT_ACRONYM);
        }

        Planet planet = galaxy.getChildByAcronym(EARTH_UNIT_ACRONYM);
        if (planet == null) {
            planet =
                    new Planet(galaxy, new LocalizedString().with(new Locale("pt"), "Terra").with(Locale.ENGLISH, "Earth"),
                            EARTH_UNIT_ACRONYM);
        }

        return planet;
    }
}
