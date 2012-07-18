/*
 * @(#)CommonAddressBean.java
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
package module.contacts.presentationTier.action.bean.addressbean;

import module.contacts.presentationTier.action.bean.AddressBean;
import module.geography.domain.GeographicLocation;

/**
 * Refactor of the PortugalAddressBean and ForeignAddressBean that share some
 * common fields
 * 
 * @author João Antunes
 * 
 */
public abstract class CommonAddressBean extends AddressBean {
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private GeographicLocation geographicLocation;

    /**
     * @return the addressLineOne
     */
    public String getAddressLineOne() {
	return addressLineOne;
    }

    /**
     * @param addressLineOne
     *            the addressLineOne to set
     */
    public void setAddressLineOne(String addressLineOne) {
	this.addressLineOne = addressLineOne;
    }

    /**
     * @return the addressLineTwo
     */
    public String getAddressLineTwo() {
	return addressLineTwo;
    }

    /**
     * @param addressLineTwo
     *            the addressLineTwo to set
     */
    public void setAddressLineTwo(String addressLineTwo) {
	this.addressLineTwo = addressLineTwo;
    }

    /**
     * @return the city
     */
    public String getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    @Override
    public GeographicLocation getGeographicLocation() {
	return geographicLocation;
    }

    @Override
    public void setGeographicLocation(GeographicLocation geographicLocation) {
	this.geographicLocation = geographicLocation;
    }

}
