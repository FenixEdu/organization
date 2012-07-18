/*
 * @(#)AddressBeanFactory.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.presentationTier.action.bean;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import module.contacts.presentationTier.action.bean.addressbean.*;

/**
 * 
 * @author João Antunes
 * 
 */
public class AddressBeanFactory {
  
    private AddressBeanFactory() {
    }

    /**
     * @param country
     *            the Country {@link Country} to base the AddressBean on
     * @return An AddressBean suited for the given country - note, the schema
     *         must be hardcoded on the contacts-schemas based on the return
     *         result of this method
     */
    public static AddressBean createAddressBean(Country country) {
	// let's return an AbstractAddressBean taking into account the country
	// of the given geographic location
	if (country.equals(Country.getPortugal())) {
	    return new PortugalAddressBean();

	} else {

	    return new ForeignAddressBean();

	}

    }

}
