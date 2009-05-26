/*
 * @(#)Magrathea.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the TODO Module for the MyOrg web application.
 *
 *   The TODO Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
 *
 *   The TODO Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the TODO Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package module.geography.domain.task;

import module.geography.domain.Galaxy;
import module.geography.domain.GeographicConstants;
import module.geography.domain.Planet;
import module.geography.domain.Universe;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * Planet Factory.
 * 
 * http://en.wikipedia.org/wiki/Places_in_The_Hitchhiker%27s_Guide_to_the_Galaxy
 * #Magrathea
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class Magrathea implements GeographicConstants {
    @Service
    public static Planet buildEarth() {
	Universe universe = Universe.getMultiverseZero();
	if (universe == null) {
	    universe = new Universe(makeName("Multiverso Zero", "Multiverse Zero"), MULTIVERSE_UNIT_ACRONYM);
	}

	Galaxy galaxy = universe.getChildByAcronym(MILKY_WAY_UNIT_ACRONYM);
	if (galaxy == null) {
	    galaxy = new Galaxy(universe, makeName("Via Láctea", "Milky Way"), MILKY_WAY_UNIT_ACRONYM);
	}

	Planet planet = galaxy.getChildByAcronym(EARTH_UNIT_ACRONYM);
	if (planet == null) {
	    planet = new Planet(galaxy, makeName("Terra", "Earth"), EARTH_UNIT_ACRONYM);
	}

	return planet;
    }

    private static MultiLanguageString makeName(String pt, String en) {
	MultiLanguageString name = new MultiLanguageString();
	name.setContent(Language.pt, pt);
	name.setContent(Language.en, en);
	return name;
    }
}
