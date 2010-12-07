/**
 * 
 */
package module.contacts.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import module.contacts.presentationTier.KindOfPartyContact;
import module.organization.domain.Person;
import myorg.domain.User;
import pt.ist.fenixframework.plugins.luceneIndexing.DomainIndexer;
import pt.ist.fenixframework.plugins.luceneIndexing.IndexableField;

/**
 * Class created to assist the {@link ContactsConfigurator} one due to the nasty
 * injector bug
 * 
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class ContactsConfiguratorAux {

    static protected String constructQuery(IndexableField fieldName, String searchName, String currentQuery) {
	String stringToReturn = fieldName.getFieldName() + ": (" + searchName + ")";
	if (currentQuery.isEmpty())
	    return stringToReturn;
	return currentQuery.concat(" AND " + stringToReturn);
    }

    static protected List<Person> getPersonsByDetailsV2(User userSearching, String searchName, String searchUsername,
	    String searchPhone,
	    PhoneType searchPhoneType, String searchAddress, String searchWebAddress, String searchEmailAddress) {
	String luceneQuery = new String();
	HashMap<String, List<Person>> listResultsBySearchField = new HashMap<String, List<Person>>();
	DomainIndexer domainIndexer = DomainIndexer.getInstance();
	HashSet<Person> auxPersonsToAdd;

	if (searchName != null && !searchName.isEmpty()) {
	    luceneQuery = ContactsConfiguratorAux.constructQuery(Person.IndexableFields.PERSON_NAME, searchName, luceneQuery);
	}
	if (searchUsername != null && !searchUsername.isEmpty()) {

	    luceneQuery = ContactsConfiguratorAux.constructQuery(Person.IndexableFields.PERSON_USERNAME, searchUsername,
		    luceneQuery);
	}
	if (searchPhone != null && !searchPhone.isEmpty()) {
	    auxPersonsToAdd = getPersonsToAddBasedOnPartyContactValue(domainIndexer, searchPhoneType, searchPhone, userSearching);
	    listResultsBySearchField.put(KindOfPartyContact.PHONE_SEARCH, new ArrayList<Person>(auxPersonsToAdd));
	}

	if (searchAddress != null && !searchAddress.isEmpty()) {
	    auxPersonsToAdd = getPersonsToAddBasedOnPartyContactValue(domainIndexer, KindOfPartyContact.PHYSICAL_ADDRESS,
		    searchAddress, userSearching);
	    listResultsBySearchField.put(KindOfPartyContact.PHYSICAL_SEARCH, new ArrayList<Person>(auxPersonsToAdd));

	}
	if (searchWebAddress != null && !searchWebAddress.isEmpty()) {
	    auxPersonsToAdd = getPersonsToAddBasedOnPartyContactValue(domainIndexer, KindOfPartyContact.WEB_ADDRESS,
		    searchAddress, userSearching);
	    listResultsBySearchField.put(KindOfPartyContact.WEB_SEARCH, new ArrayList<Person>(auxPersonsToAdd));

	}
	if (searchEmailAddress != null && !searchEmailAddress.isEmpty()) {
	    auxPersonsToAdd = getPersonsToAddBasedOnPartyContactValue(domainIndexer, KindOfPartyContact.EMAIL_ADDRESS,
		    searchAddress, userSearching);
	    listResultsBySearchField.put(KindOfPartyContact.EMAIL_SEARCH, new ArrayList<Person>(auxPersonsToAdd));
	}

	if (!luceneQuery.isEmpty())
	    listResultsBySearchField.put(KindOfPartyContact.PERSON_SEARCH,
		    domainIndexer.expertSearch(Person.class, luceneQuery, DomainIndexer.DEFAULT_MAX_SIZE));

	auxPersonsToAdd = new HashSet<Person>();
	for (String keyString : listResultsBySearchField.keySet()) {
	    if (auxPersonsToAdd.isEmpty())
		auxPersonsToAdd.addAll(listResultsBySearchField.get(keyString));
	    else
		auxPersonsToAdd.retainAll(listResultsBySearchField.get(keyString));
	}
	return new ArrayList<Person>(auxPersonsToAdd);

    }

    /**
     * 
     * @param domainIndexer
     * @param indexableField
     * @param value
     * @param userSearching
     * @return A set of persons that own the contacts found by lucene's search
     *         system using the given value and the given IndexableField
     */
    static private HashSet<Person> getPersonsToAddBasedOnPartyContactValue(DomainIndexer domainIndexer,
	    IndexableField indexableField,
	    String value, User userSearching) {
	HashSet<Person> auxPersonsToAdd = new HashSet<Person>();
	for (PartyContact partyContact : domainIndexer.search(PartyContact.class, indexableField, value)) {
	    if (partyContact.isVisibleTo(userSearching))
		auxPersonsToAdd.add(partyContact.getOwner());
	}
	return auxPersonsToAdd;

    }

}
