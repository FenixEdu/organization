/*
 * @(#)PhysicalAddressBean.java
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
package module.contacts.presentationTier.bean;

import java.io.Serializable;

import module.contacts.domain.PhysicalAddress;
import module.contacts.presentationTier.renderers.PhysicalAddressBeanInputRendererWithPostBack;
import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;

/**
 * Bean for the PhysicalAddress
 * 
 * This bean has an associated render
 * 
 * @see PhysicalAddress
 * @see PhysicalAddressBeanInputRendererWithPostBack
 * 
 * @author João Antunes
 * 
 */
public class PhysicalAddressBean implements Serializable {

    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    private Country country;

    private GeographicLocation physicalAddressLocation;

    private String complementarAddress;

    /**
     * @return the country
     */
    public Country getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(Country country) {
	this.country = country;
    }

    /**
     * @return the physicalAddressLocation
     */
    public GeographicLocation getPhysicalAddressLocation() {
	return physicalAddressLocation;
    }

    /**
     * @param physicalAddressLocation
     *            the physicalAddressLocation to set
     */
    public void setPhysicalAddressLocation(GeographicLocation physicalAddressLocation) {
	this.physicalAddressLocation = physicalAddressLocation;
    }

    /**
     * @return the complementarAddress
     */
    public String getComplementarAddress() {
	return complementarAddress;
    }

    /**
     * @param complementarAddress
     *            the complementarAddress to set
     */
    public void setComplementarAddress(String complementarAddress) {
	this.complementarAddress = complementarAddress;
    }

}
