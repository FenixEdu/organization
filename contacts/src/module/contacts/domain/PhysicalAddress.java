package module.contacts.domain;

import java.lang.reflect.InvocationTargetException;

import org.apache.pdfbox.util.operator.SetCharSpacing;
import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import module.geography.util.AddressPrinter;
import module.organization.domain.Party;
import myorg.domain.exceptions.DomainException;

public class PhysicalAddress extends PhysicalAddress_Base {

    public PhysicalAddress(GeographicLocation geographicLocation, String complementarAddressString, Party party,
	    Boolean defaultContact, PartyContactType partyContactType) {
	super();

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
     * @return a PhysicalAddress with the given parameters
     */
    @Service
    public static PhysicalAddress createNewPhysicalAddress(GeographicLocation geographicLocation,
	    String complementarAddressString, Party party, Boolean defaultContact, PartyContactType partyContactType) {
	PhysicalAddress newPhysicalAddress = new PhysicalAddress(geographicLocation, complementarAddressString, party,
		defaultContact, partyContactType);
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
	    return addressPrinter.getFormatedAddress(this.getComplementarAddress(), country);
	}
    }

    @Override
    public void setValue(String value) {
	// it only sets the complementar string!!
	setComplementarAddress(value);
    }

}
