package module.contacts.domain;

import java.util.List;

import module.organization.domain.Party;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class Phone extends Phone_Base {

    public Phone(PhoneType phoneType, String number, Party party, Boolean defaultContact, PartyContactType partyContactType,
	    List<PersistentGroup> visibilityGroups) {
	super();

	super.setVisibleTo(visibilityGroups);

	super.setPhoneType(phoneType);
	super.setNumber(number);
	super.setParty(party);
	super.setDefaultContact(defaultContact);
	super.setType(partyContactType);
	super.setLastModifiedDate(new DateTime());
    }

    /**
     * Creates, returns and associates a Phone with the given party
     * 
     * @param phoneType
     *            the phoneType {@link PhoneType}
     * @param number
     *            the telephone number
     * @param party
     *            the party to which this phone belongs
     * @param defaultContact
     *            if it is the default contact for this party
     * @param partyContactType
     *            the type of contact {@link PartyContactType}
     * @param visibilityGroups
     * @return a Phone with the given parameters
     */
    @Service
    public static Phone createNewPhone(PhoneType phoneType, String number, Party party, Boolean defaultContact,
	    PartyContactType partyContactType, User userCreatingTheContact, List<PersistentGroup> visibilityGroups) {

	// validate that the user can actually create this contact
	validateUser(userCreatingTheContact, party, partyContactType);

	// making sure the list of visibility groups is a valid one
	validateVisibilityGroups(visibilityGroups);

	// make sure that this isn't a duplicate contact for this party
	for (PartyContact partyContact : party.getPartyContacts()) {
	    if ((partyContact instanceof Phone) && partyContact.getValue() == number
		    && partyContactType.equals(partyContact.getType()) && phoneType.equals(((Phone) partyContact).getPhoneType())) {
		throw new DomainException("error.duplicate.partyContact");
	    }
	}
	return new Phone(phoneType, number, party, defaultContact, partyContactType, visibilityGroups);

    }

    @Override
    public String getFieldName() {
	return getPhoneType().getFieldName();
    };

    @Service
    public void changePhoneType(PhoneType phoneType) {
	setPhoneType(phoneType);
    }

    @Override
    public String getDescription() {
	switch (getPhoneType()) {
	case VOIP_SIP:
	    return "sip:" + getNumber();
	case CELLPHONE:
	case REGULAR_PHONE:
	    return getNumber();
	case EXTENSION:
	    return "(Ext)" + getNumber();
	}
	return null;
    }

    @Override
    public String getValue() {
	return getNumber();
    }

    @Override
    public void setValue(String value) {
	setNumber(value);
    }
}
