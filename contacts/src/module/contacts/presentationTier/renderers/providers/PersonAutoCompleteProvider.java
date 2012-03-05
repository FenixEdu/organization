/*
 * @(#)PersonAutoCompleteProvider.java
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pt.utl.ist.fenix.tools.util.StringNormalizer;

import module.organization.domain.Party;
import module.organization.domain.Person;
import myorg.domain.MyOrg;
import myorg.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;

/**
 * 
 * @author João Antunes
 * 
 */
public class PersonAutoCompleteProvider implements AutoCompleteProvider {

    /* (non-Javadoc)
     * @see myorg.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider#getSearchResults(java.util.Map, java.lang.String, int)
     */
    @Override
    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
	final List<Person> persons = new ArrayList<Person>();

	final String trimmedValue = value.trim();
	final String[] input = trimmedValue.split(" ");
	StringNormalizer.normalize(input);

	// TODO: refactor to use MyOrg.getInstance().getPersonsSet() ?
	for (final Person person : MyOrg.getInstance().getPersonsSet()) {
	    if (hasMatch(input, person.getName())) {
		persons.add(person);
	    }
	}

	Collections.sort(persons, Person.COMPARATOR_BY_NAME);

	return persons;
    }

    private boolean hasMatch(final String[] input, final String unitNameParts) {
	for (final String namePart : input) {
	    if (unitNameParts.indexOf(namePart) == -1) {
		return false;
	    }
	}
	return true;
    }

}
