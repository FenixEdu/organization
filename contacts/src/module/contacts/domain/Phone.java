package module.contacts.domain;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import module.organization.domain.Party;

public class Phone extends Phone_Base {

    public Phone(PhoneType phoneType, String number, Party party, Boolean defaultContact, PartyContactType partyContactType) {
	super();
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
     * @return a Phone with the given parameters
     */
    @Service
    public static Phone createNewPhone(PhoneType phoneType, String number, Party party, Boolean defaultContact,
	    PartyContactType partyContactType) {
	return new Phone(phoneType, number, party, defaultContact, partyContactType);

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
