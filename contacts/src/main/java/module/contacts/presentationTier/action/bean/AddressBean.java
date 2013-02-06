/*
 * @(#)AddressBean.java
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

import module.geography.domain.GeographicLocation;

/**
 * Class used by the AddressBeanFactory
 * 
 * @see #getType()
 * @see AddressBeanFactory
 * 
 * @author João Antunes
 * 
 */
public abstract class AddressBean implements Serializable {

    /**
     * Default serializable {@link Serializable} version
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return the getClass().getName() {@link Class#getName()} of the concrete
     *         address bean that should extend this class. This method is used
     *         to retrieve the schema associated with the concrete
     *         implementation of the bean bean
     */
    public final String getSchema() {
        return this.getClass().getName();
//	return this.getClass().getPackage() + "." + nameCountry + this.getClass().getCanonicalName();
    }

    public abstract String getComplementarAddress();

    /**
     * 
     * @return the closest GeographicLocation to the given address which is
     *         known by the system
     */
    public abstract GeographicLocation getGeographicLocation();

    public abstract void setGeographicLocation(GeographicLocation geographicLocation);

    /**
     * 
     * @return true if the address is valid, that is, if the GeographicLocation
     *         and the given fields do all match, false otherwise;
     */
    public abstract boolean isValid();

}
