/*
 * @(#)ContactToCreateBean.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.EmailAddress;
import module.contacts.domain.Phone;
import module.contacts.domain.PhysicalAddress;
import module.contacts.domain.WebAddress;
import module.contacts.presentationTier.KindOfPartyContact;
import module.organization.domain.Party;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;

/**
 * 
 * @author João Antunes
 * @author Pedro Amaral
 * 
 */
public class ContactToCreateBean extends ContactToEditBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    
    private KindOfPartyContact partyContactKind;

    private Party party;


    public ContactToCreateBean() {
	super();
    }

    public ContactToCreateBean(String forwardPath) {
	super(forwardPath);
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

    public void setParty(Party party) {
	this.party = party;
    }

    public Party getParty() {
	return party;
    }

}
