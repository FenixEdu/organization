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
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import module.contacts.domain.PhysicalAddress;
import module.contacts.presentationTier.renderers.PhysicalAddressBeanInputRendererWithPostBack;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;

/**
 * Bean for the PhysicalAddress. Check the createContact.jsp for an example on
 * how to use this class
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

    private HashMap<String, CountrySubdivision> geographicLevels;

    private AddressBean addressBean;

    private PhysicalAddress physicalAddress;

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

    public void setPhysicalAddress(PhysicalAddress physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public PhysicalAddress getPhysicalAddress() {
        return physicalAddress;
    }

    public void setGeographicLevels(HashMap<String, CountrySubdivision> geographicLevels) {
        this.geographicLevels = geographicLevels;
    }

    public HashMap<String, CountrySubdivision> getGeographicLevels() {
        return geographicLevels;
    }

    /**
     * 
     * @param originalCollection
     *            the collection to disconnect and sort
     * @return a 'disconnected', i.e. changes to it won't be propagated, ordered
     *         by {@link CountrySubdivision#getLevel()} ArrayList without null
     *         objects
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<CountrySubdivision> getSubdivisionsOrderedArrayList(Collection<CountrySubdivision> originalCollection) {
        ArrayList<CountrySubdivision> geographicLevelsOrdered = new ArrayList<CountrySubdivision>();
        for (CountrySubdivision countrySubdivision : originalCollection) {
            if (countrySubdivision != null) {
                geographicLevelsOrdered.add(countrySubdivision);
            }
        }
        Collections.sort(geographicLevelsOrdered, CountrySubdivision.COMPARATOR_BY_LEVEL);
        return geographicLevelsOrdered;

    }

    public void setAddressBean(AddressBean addressBean) {
        this.addressBean = addressBean;
    }

    public AddressBean getAddressBean() {
        return addressBean;
    }

}
