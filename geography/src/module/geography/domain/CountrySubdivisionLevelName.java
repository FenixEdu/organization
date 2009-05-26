/*
 * @(#)CountrySubdivisionLevelName.java
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

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * Each {@link CountrySubdivision} has a matching level name. Each country has
 * it's own names no a {@link Country} object should have a list of these.
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class CountrySubdivisionLevelName extends CountrySubdivisionLevelName_Base {

    public CountrySubdivisionLevelName(Integer level, MultiLanguageString name) {
	super();
	setLevel(level);
	setName(name);
    }

}
