/*
 * @(#)WebAddress.java
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
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.domain.groups.PersistentGroup;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Pedro Santos
 * @author João Antunes
 * @author Susana Fernandes
 * 
 */
public class WebAddress extends WebAddress_Base {

    public WebAddress(String url, Party party, Boolean defaultContact, PartyContactType type,
	    List<PersistentGroup> visibilityGroups) {
	super();

	super.setVisibleTo(visibilityGroups);
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
     * @param visibilityGroups
     *            the visibility groups that the user defined to make the
     *            contact visible to
     * @return an WebAddress with the given parameters
     */
    @Service
    public static WebAddress createNewWebAddress(String url, Party party, Boolean defaultContact, PartyContactType type,
	    User userCreatingTheContact, List<PersistentGroup> visibilityGroups) {
	// validate that the user can actually create this contact
	validateUser(userCreatingTheContact, party, type);

	// making sure the list of visibility groups is a valid one
	validateVisibilityGroups(visibilityGroups);

	// make sure that this isn't a duplicate contact for this party
	for (PartyContact partyContact : party.getPartyContacts()) {
	    if (partyContact instanceof WebAddress && partyContact.getValue() == url && type.equals(partyContact.getType())) {
		throw new DomainException("error.duplicate.partyContact");
	    }
	}

	return new WebAddress(url, party, defaultContact, type, visibilityGroups);
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
