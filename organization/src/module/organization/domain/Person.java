package module.organization.domain;

import module.organization.domain.PartyType.PartyTypeBean;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Person extends Person_Base {

    public static PartyType getPartyTypeInstance() {
	final String type = Person.class.getName();
	PartyType partyType = PartyType.readBy(type);
	if (partyType == null) {
	    synchronized (Person.class) {
		partyType = PartyType.readBy(Person.class.getName());
		if (partyType == null) {
		    final PartyTypeBean partyTypeBean = new PartyTypeBean();
		    partyTypeBean.setType(type);
		    partyTypeBean.setName(new MultiLanguageString("Pessoa"));
		    partyType = PartyType.create(partyTypeBean);
		}
	    }
	}
	return partyType;
    }

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

    @Override
    final public boolean isPerson() {
	return true;
    }
}
