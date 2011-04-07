package module.contacts.domain;

import java.util.ArrayList;

import module.organization.domain.Party;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;
import net.sourceforge.fenixedu.domain.contacts.RemoteMobilePhone;
import net.sourceforge.fenixedu.domain.contacts.RemotePartyContact;
import net.sourceforge.fenixedu.domain.contacts.RemotePhone;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class Phone extends Phone_Base {

    public Phone(PhoneType phoneType, String number, Party party, Boolean defaultContact, PartyContactType partyContactType,
	    ArrayList<PersistentGroup> visibilityGroups) {
	super();

	super.setVisibleTo(visibilityGroups);

	super.setPhoneType(phoneType);
	super.setNumber(number);
	super.setParty(party);
	super.setDefaultContact(defaultContact);
	super.setType(partyContactType);
	super.setLastModifiedDate(new DateTime());
    }

    Phone(Party party, RemoteMobilePhone remote) {
	// TODO: get type and visibility from remote.
	this(PhoneType.CELLPHONE, remote.getNumber(), party, remote.getDefaultContact(), convertRemoteContactType(remote
		.getPartyContactTypeString()), null);
	setRemotePartyContact(remote);
    }

    Phone(Party party, RemotePhone remote) {
	// TODO: get type and visibility from remote.
	this(PhoneType.REGULAR_PHONE, remote.getNumber(), party, remote.getDefaultContact(), convertRemoteContactType(remote
		.getPartyContactTypeString()), null);
	setRemotePartyContact(remote);
    }

    @Override
    protected void updateFromRemote(RemotePartyContact remote) {
	// TODO: get type and visibility from remote.
	if (remote instanceof RemoteMobilePhone) {
	    RemoteMobilePhone phone = (RemoteMobilePhone) remote;
	    setPhoneType(PhoneType.CELLPHONE);
	    setNumber(phone.getNumber());
	}
	if (remote instanceof RemotePhone) {
	    RemotePhone phone = (RemotePhone) remote;
	    setPhoneType(PhoneType.REGULAR_PHONE);
	    setNumber(phone.getNumber());
	}
	setDefaultContact(remote.getDefaultContact());
	setType(convertRemoteContactType(remote.getPartyContactTypeString()));
    }

    /**
     * Creates, returns and associates a Phone with the given party
     * 
     * @param phoneType the phoneType {@link PhoneType}
     * @param number the telephone number
     * @param party the party to which this phone belongs
     * @param defaultContact if it is the default contact for this party
     * @param partyContactType the type of contact {@link PartyContactType}
     * @param visibilityGroups
     * @return a Phone with the given parameters
     */
    @Service
    public static Phone createNewPhone(PhoneType phoneType, String number, Party party, Boolean defaultContact,
	    PartyContactType partyContactType, User userCreatingTheContact, ArrayList<PersistentGroup> visibilityGroups) {

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
