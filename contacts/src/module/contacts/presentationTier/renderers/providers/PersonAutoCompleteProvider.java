/**
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
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
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
