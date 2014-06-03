/*
 * @(#)CountryAutoCompleteProvider.java
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
package module.geography.presentationTier.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.geography.domain.Country;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import org.fenixedu.commons.StringNormalizer;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class CountryAutoCompleteProvider implements AutoCompleteProvider<Country> {

    @Override
    public Collection<Country> getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
        final List<Country> countries = new ArrayList<Country>();

        final String trimmedValue = value.trim();
        final String[] input = trimmedValue.split(" ");
        for (int i = 0; i < input.length; i++) {
            input[i] = StringNormalizer.normalize(input[i]);
        }

        for (final Country country : Bennu.getInstance().getCountriesSet()) {
            final String countryName = StringNormalizer.normalize(country.getName().getContent());
            if (hasMatch(input, countryName)) {
                countries.add(country);
            } else {
                final String countryAcronym = StringNormalizer.normalize(country.getAcronym());
                if (hasMatch(input, countryAcronym)) {
                    countries.add(country);
                }
            }
        }

        Collections.sort(countries, Country.COMPARATOR_BY_NAME);

        return countries;
    }

    private boolean hasMatch(final String[] input, final String countryNameParts) {
        for (final String namePart : input) {
            if (countryNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

}
