/*
 * @(#)EmailAddress.java
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
package module.contacts.domain;

import java.util.List;

import module.organization.domain.Party;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Pedro Santos
 * @author João Antunes
 * 
 */
public class EmailAddress extends EmailAddress_Base {

    public EmailAddress(String emailAddress, Party party, Boolean defaultContact, PartyContactType partyContactType,
	    List<PersistentGroup> visibilityGroups) {
	super();

	super.setVisibleTo(visibilityGroups);

	super.setValue(emailAddress);
	super.setParty(party);
	super.setDefaultContact(defaultContact);
	super.setType(partyContactType);

	super.setLastModifiedDate(new DateTime());
    }

    /**
     * Creates, returns and associates an EmailAddress with the given party
     * 
     * @param emailAddress the string with the email value e.g.
     *            johndoe@nonexisting.com
     * @param party the party to which the contact is associated
     * @param defaultContact if this is the default contact of the given party
     * @param partyContactType the partytype, see {@link PartyContactType}
     * @param visibilityGroups the visibility groups to which this contact is
     *            going to be visible to.
     * @return an EmailAddress with the given parameters
     */
    @Service
    public static EmailAddress createNewEmailAddress(String emailAddress, Party party, Boolean defaultContact,
	    PartyContactType partyContactType, User userCreatingTheContact, List<PersistentGroup> visibilityGroups) {
	// validate that the user can actually create this contact
	validateUser(userCreatingTheContact, party, partyContactType);

	// making sure the list of visibility groups is a valid one
	validateVisibilityGroups(visibilityGroups);

	// make sure that this isn't a duplicate contact for this party
	for (PartyContact partyContact : party.getPartyContacts()) {
	    if (partyContact instanceof EmailAddress && partyContact.getValue() == emailAddress
		    && partyContactType.equals(partyContact.getType())) {
		throw new DomainException("error.duplicate.partyContact");
	    }
	}

	return new EmailAddress(emailAddress, party, defaultContact, partyContactType, visibilityGroups);
    }

    @Override
    public String getDescription() {
	return getValue();
    }

}
