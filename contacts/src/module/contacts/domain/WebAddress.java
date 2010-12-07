package module.contacts.domain;

import module.organization.domain.Party;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class WebAddress extends WebAddress_Base {

    public WebAddress(String url, Party party, Boolean defaultContact, PartyContactType type) {
	super();

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
     * @param url
     *            the URL of the web address
     * @param party
     *            the party to which the contact belongs
     * @param defaultContact
     *            if it is the default contact for this party
     * @param type
     *            the type of contact {@link PartyContactType}
     * @return an WebAddress with the given parameters
     */
    @Service
    public static WebAddress createNewWebAddress(String url, Party party, Boolean defaultContact, PartyContactType type) {
	return new WebAddress(url, party, defaultContact, type);
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
