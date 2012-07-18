/*
 * @(#)AddressPrinter.java
 *
 * Copyright 2010 Instituto Superior Tecnico
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
package module.geography.util;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;

/**
 * Interface that has the methods to be implemented by the several
 * AddressPrinters per country that should exist and be assigned to the
 * modules.geography.domain.Country field
 * 
 * 
 * @author João Antunes
 * 
 */
public class AddressPrinter {

    public AddressPrinter() {

    }

    public AddressPrinter(Country country) {
	this();

    }

    public static String getFormatedAddress(String complementarAddress, Country country,
 GeographicLocation geographicLocation) {
	if (country.equals(Country.getPortugal())) {
	    return complementarAddress;
	} else
	    return complementarAddress;
    }

}
