/*
 * @(#)CountriesProvider.java
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
package module.contacts.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.geography.domain.Country;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * 
 * @author João Antunes
 * 
 */
public class CountriesProvider implements DataProvider {

    /*
     * (non-Javadoc)
     * 
     * @see pt.ist.fenixWebFramework.renderers.DataProvider#getConverter()
     */
    @Override
    public Converter getConverter() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * pt.ist.fenixWebFramework.renderers.DataProvider#provide(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public Object provide(Object source, Object currentValue) {
        // TODO ?? add a check to see if the source the user that is set on the
        // source bean has privileges to see the visibility information of this
        // contact information
        final ArrayList<Country> listOfCountries = new ArrayList<Country>(MyOrg.getInstance().getCountriesSet());
        return listOfCountries;
    }

    public static Object provideCountries() {
        ArrayList<Country> listOfCountries = new ArrayList<Country>(MyOrg.getInstance().getCountriesSet());
        return listOfCountries;
    }

}
