package module.contacts.domain;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import module.organization.domain.Party;

public class EmailAddress extends EmailAddress_Base {
    
	public EmailAddress(String emailAddress, Party party,
			Boolean defaultContact, PartyContactType partyContactType) {
        super();

		super.setValue(emailAddress);
		super.setParty(party);
		super.setDefaultContact(defaultContact);
		super.setType(partyContactType);

		super.setLastModifiedDate(new DateTime());
    }

	/**
	 * Creates, returns and associates an EmailAddress with the given party
	 * 
	 * @param emailAddress
	 *            the string with the email value e.g. johndoe@nonexisting.com
	 * @param party
	 *            the party to which the contact is associated
	 * @param defaultContact
	 *            if this is the default contact of the given party
	 * @param partyContactType
	 *            the partytype, see {@link PartyContactType}
	 * @return an EmailAddress with the given parameters
	 */
	@Service
	public static EmailAddress createNewEmailAddress(String emailAddress,
			Party party, Boolean defaultContact,
			PartyContactType partyContactType) {
		return new EmailAddress(emailAddress, party, defaultContact,
				partyContactType);

	}

    @Override
    public String getDescription() {
	return getValue();
    }
    
}
