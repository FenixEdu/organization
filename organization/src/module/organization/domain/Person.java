package module.organization.domain;

import myorg.domain.MyOrg;
import myorg.domain.User;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Person extends Person_Base {

    public Person(MultiLanguageString partyName, PartyType partyType) {
	super();
	setPartyName(partyName);
	setPartyType(partyType);
    }

    public static Person readByPartyName(MultiLanguageString partyName) {
	for (final Party party : MyOrg.getInstance().getParties()) {
	    if (!party.isUnit()) {
		if (party.getPartyName().equals(partyName)) {
		    return (Person) party;
		}
	    }
	}
	return null;
    }

    @Service
    static public Person create(final MultiLanguageString partyName, PartyType partyType) {
	return new Person(partyName, partyType);
    }

}
