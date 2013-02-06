/*
 * @(#)GeographicLocationsProvider.java
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
import java.util.HashMap;

import module.contacts.presentationTier.action.bean.PhysicalAddressBean;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * 
 * @author João Antunes
 * 
 */
public class GeographicLocationsProvider implements DataProvider {

    @Override
    public Object provide(Object source, Object currentValue) {
        PhysicalAddressBean physicalAddressBean = (PhysicalAddressBean) source;
        HashMap<String, CountrySubdivision> geographicLevels = physicalAddressBean.getGeographicLevels();
        Country country = physicalAddressBean.getCountry();
        if (currentValue instanceof CountrySubdivision) {
            CountrySubdivision countrySubdivision = (CountrySubdivision) currentValue;
            return countrySubdivision.getCurrentSiblings();
            // ArrayList<Country> countries = new ArrayList<Country>();
            // Collections.sort(countries,
            // GeographicLocation.COMPARATOR_BY_NAME);
            // return country.getCurrentChildren();
            // return CountriesProvider.provideCountries();
            // return country.getCurrentChildren();
        }
        if (currentValue == null) {
            // let's go to the source and assert what shall we print
            CountrySubdivision countrySubdivision = getLastNonNullCountrySubDivision(geographicLevels);
            if (countrySubdivision == null) {
                return country.getCurrentChildren();
            } else {
                return countrySubdivision.getCurrentChildren();
            }
        }
        return null;
    }

    /**
     * 
     * @param geographicLevels
     *            the original geographicLevels from which the
     *            CountrySubdivision is extracted
     * @return the last non null {@link CountrySubdivision} or null if it
     *         doesn't exist
     */
    private CountrySubdivision getLastNonNullCountrySubDivision(HashMap<String, CountrySubdivision> geographicLevels) {
        ArrayList<CountrySubdivision> countrySubdivisions =
                PhysicalAddressBean.getSubdivisionsOrderedArrayList(geographicLevels.values());
        if (countrySubdivisions.size() == 0) {
            return null;
        } else {
            return countrySubdivisions.get(countrySubdivisions.size() - 1);
        }
    }

    @Override
    public Converter getConverter() {
        return null;
    }

}
