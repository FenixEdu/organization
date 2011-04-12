package module.contacts.domain;

import java.util.ArrayList;

import module.organization.domain.Party;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class WebAddress extends WebAddress_Base {

    public WebAddress(String url, Party party, Boolean defaultContact, PartyContactType type,
	    ArrayList<PersistentGroup> visibilityGroups) {
	super();

	super.setVisibleTo(visibilityGroups);
	super.setUrl(url);
	super.setParty(party);
	super.setDefaultContact(defaultContact);
	super.setType(type);

	super.setLastModifiedDate(new DateTime());
    }

    /**
     * 
     * Creates, returns and associates a WebAddress with the given party
     * 
     * @param url the URL of the web address
     * @param party the party to which the contact belongs
     * @param defaultContact if it is the default contact for this party
     * @param type the type of contact {@link PartyContactType}
     * @param visibilityGroups the visibility groups that the user defined to
     *            make the contact visible to
     * @return an WebAddress with the given parameters
     */
    @Service
    public static WebAddress createNewWebAddress(String url, Party party, Boolean defaultContact, PartyContactType type,
	    User userCreatingTheContact, ArrayList<PersistentGroup> visibilityGroups) {
	// validate that the user can actually create this contact
	validateUser(userCreatingTheContact, party, type);

	// making sure the list of visibility groups is a valid one
	validateVisibilityGroups(visibilityGroups);

	// make sure that this isn't a duplicate contact for this party
	for (PartyContact partyContact : party.getPartyContacts()) {
	    if (partyContact instanceof WebAddress && partyContact.getValue() == url && type.equals(partyContact.getType())) {
		throw new DomainException("error.duplicate.partyContact");
	    }
	}

	return new WebAddress(url, party, defaultContact, type, visibilityGroups);
    }

    @Override
    public String getDescription() {
	return getUrl();
    }

    public String getLink() {
	// TODO FIXME probably not a good idea to do this!
	return "<a href=" + "\"" + getUrl() + "\">" + getUrl() + "</a>";

    }

    @Override
    public void setValue(String value) {
	setUrl(value);
    }
}
