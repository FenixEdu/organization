/*
 * @(#)PhysicalAddress.java
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

import java.util.ArrayList;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import module.geography.util.AddressPrinter;
import module.organization.domain.Party;

import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author João Antunes
 * @author Sérgio Silva
 * 
 */
public class PhysicalAddress extends PhysicalAddress_Base {

    public PhysicalAddress(GeographicLocation geographicLocation, String complementarAddressString, Party party,
            Boolean defaultContact, PartyContactType partyContactType, User userCreatingTheContact,
            ArrayList<PersistentGroup> visibilityGroups) {
        super();

        super.setVisibleTo(visibilityGroups);

        super.setGeographicLocation(geographicLocation);
        super.setComplementarAddress(complementarAddressString);
        super.setParty(party);
        super.setDefaultContact(defaultContact);
        super.setType(partyContactType);

        super.setLastModifiedDate(new DateTime());

    }

    public PhysicalAddress() {
//	this(null,complementarAddressString,null,true,PartyContactType.IMMUTABLE, null, null);
        super();
    }

    /**
     * 
     * Creates, returns and associates a PhysicalAddress with the given party
     * 
     * @param geographicLocation the GeographicLocation see {@link GeographicLocation}
     * @param complementarAddressString the complementar address string
     *            depending on the details or the lack of them that the
     *            Geography module gives (or even if it is used) see the
     *            AddressPrinter for more information {@link AddressPrinter}
     * @param party the party to which this physical address is associated
     * @param defaultContact if it is the default contact for this party
     * @param partyContactType the PartyContactType, see {@link PartyContactType}
     * @param visibilityGroups the visibility groups to which this contact will
     *            be visible to
     * @return a PhysicalAddress with the given parameters
     */
    @Atomic
    public static PhysicalAddress createNewPhysicalAddress(GeographicLocation geographicLocation,
            String complementarAddressString, Party party, Boolean defaultContact, PartyContactType partyContactType,
            User userCreatingTheContact, ArrayList<PersistentGroup> visibilityGroups) {
        // validate that the user can actually create this contact
        validateUser(userCreatingTheContact, party, partyContactType);

        // making sure the list of visibility groups is a valid one
        validateVisibilityGroups(visibilityGroups);

        // make sure that this isn't a duplicate contact for this party
        for (PartyContact partyContact : party.getPartyContactsSet()) {
            if (partyContact instanceof PhysicalAddress && partyContact.getValue() == complementarAddressString
                    && ((PhysicalAddress) partyContact).getGeographicLocation().equals(geographicLocation)) {
                throw new DomainException("error.duplicate.partyContact");
            }
        }

        PhysicalAddress newPhysicalAddress =
                new PhysicalAddress(geographicLocation, complementarAddressString, party, defaultContact, partyContactType,
                        userCreatingTheContact, visibilityGroups);
        newPhysicalAddress.setComplementarAddress(complementarAddressString);
        return newPhysicalAddress;

    }

    @Override
    public void delete() {
        setGeographicLocation(null);
        super.delete();
    }

    @Override
    public String getDescription() {
        // TODO verify that is working
        GeographicLocation geographicLocation = getGeographicLocation();
        if (geographicLocation == null) {
            return getComplementarAddress();
        } else {

            Country country = geographicLocation.getCountry();
            if (country == null) {
                // lets just throw a domainexception so that we know that there
                // is a case that shouldn't be happening
                throw new DomainException("error.illegal.geographiclocation.structure.no.country");
            }
            AddressPrinter addressPrinter;
            try {
                addressPrinter = country.getAddressPrinter();
            } catch (Exception e) {
                throw new DomainException("error.using.iaddressprinter", e);
            }
            return addressPrinter.getFormatedAddress(this.getComplementarAddress(), getGeographicLocation().getCountry(),
                    getGeographicLocation());
        }
    }

    @Override
    public void setValue(String value) {
        // it only sets the complementar string!!
        setComplementarAddress(value);
    }

}
