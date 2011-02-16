package module.contacts.domain;

import java.util.ArrayList;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import module.geography.util.AddressPrinter;
import module.organization.domain.Party;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

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

    /**
     * 
     * Creates, returns and associates a PhysicalAddress with the given party
     * 
     * @param geographicLocation
     *            the GeographicLocation see {@link GeographicLocation}
     * @param complementarAddressString
     *            the complementar address string depending on the details or
     *            the lack of them that the Geography module gives (or even if
     *            it is used) see the AddressPrinter for more information
     *            {@link AddressPrinter}
     * @param party
     *            the party to which this physical address is associated
     * @param defaultContact
     *            if it is the default contact for this party
     * @param partyContactType
     *            the PartyContactType, see {@link PartyContactType}
     * @param visibilityGroups
     *            the visibility groups to which this contact will be visible to
     * @return a PhysicalAddress with the given parameters
     */
    @Service
    public static PhysicalAddress createNewPhysicalAddress(GeographicLocation geographicLocation,
	    String complementarAddressString, Party party, Boolean defaultContact, PartyContactType partyContactType,
	    User userCreatingTheContact, ArrayList<PersistentGroup> visibilityGroups) {
	//validate that the user can actually create this contact
	validateUser(userCreatingTheContact, party, partyContactType);

	//making sure the list of visibility groups is a valid one
	validateVisibilityGroups(visibilityGroups);

	//make sure that this isn't a duplicate contact for this party
	for (PartyContact partyContact : party.getPartyContacts()) {
	    if (partyContact instanceof PhysicalAddress && partyContact.getValue() == complementarAddressString
		    && ((PhysicalAddress) partyContact).getGeographicLocation().equals(geographicLocation)) {
		throw new DomainException("error.duplicate.partyContact");
	    }
	}

	PhysicalAddress newPhysicalAddress = new PhysicalAddress(geographicLocation, complementarAddressString, party,
		defaultContact, partyContactType, userCreatingTheContact, visibilityGroups);
	newPhysicalAddress.setComplementarAddress(complementarAddressString);
	return newPhysicalAddress;

    }

    @Override
    public void delete() {
	removeGeographicLocation();
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
	    if (country == null)
		// lets just throw a domainexception so that we know that there
		// is a case that shouldn't be happening
		throw new DomainException("error.illegal.geographiclocation.structure.no.country");
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
