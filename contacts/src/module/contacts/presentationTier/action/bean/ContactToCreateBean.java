/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.EmailAddress;
import module.contacts.domain.PartyContact;
import module.contacts.domain.PartyContactType;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.PhysicalAddress;
import module.contacts.domain.WebAddress;
import module.contacts.presentationTier.KindOfPartyContact;
import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.MyOrg;
import myorg.domain.groups.PersistentGroup;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class ContactToCreateBean extends ContactToEditBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    
    private KindOfPartyContact partyContactKind;


    public ContactToCreateBean() {
	super();
    }

    public String getSchemaSuffix() {
	String toReturn = new String();
	switch (partyContactKind) {
	case PHONE:
	    switch (getPhoneType()) {
	    case VOIP_SIP:
		toReturn = toReturn.concat(".VoIPPhone");
		break;
	    case REGULAR_PHONE:
	    case CELLPHONE:
		toReturn = toReturn.concat(".RegularPhone");
		break;
	    case EXTENSION:
		toReturn = toReturn.concat(".ExtensionPhone");
		break;
	    }
	    break;
	case WEB_ADDRESS:
	    toReturn = toReturn.concat(".WebAddress");
	    break;
	case EMAIL_ADDRESS:
	    toReturn = toReturn.concat(".EmailAddress");
	    break;

	default:
	    break;
	}
	if (ContactsConfigurator.getInstance().isSuperEditor(UserView.getCurrentUser()))
	    toReturn = toReturn.concat(".superEditor");
	else
	    toReturn = toReturn.concat(".regular");
	return toReturn;
    }

    @Override
    public String getClassName() {
	switch (partyContactKind) {
	case PHONE:
	    return Phone.class.getName();
	case EMAIL_ADDRESS:
	    return EmailAddress.class.getName();
	case PHYSICAL_ADDRESS:
	    return PhysicalAddress.class.getName();
	case WEB_ADDRESS:
	    return WebAddress.class.getName();
	default:
	    return null;
	}
    }

    public void setPartyContactKind(KindOfPartyContact partyContactKind) {
	this.partyContactKind = partyContactKind;
    }

    public KindOfPartyContact getPartyContactKind() {
	return partyContactKind;
    }

}
