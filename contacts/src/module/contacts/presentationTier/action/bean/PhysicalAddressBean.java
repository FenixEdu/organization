/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import module.contacts.domain.PhysicalAddress;
import module.contacts.presentationTier.renderers.PhysicalAddressBeanInputRendererWithPostBack;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.CountrySubdivisionLevelName;
import module.geography.domain.GeographicLocation;

/**
 * Bean for the PhysicalAddress. Check the createContact.jsp for an example on
 * how to use this class
 * 
 * @see PhysicalAddress
 * @see PhysicalAddressBeanInputRendererWithPostBack
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class PhysicalAddressBean implements Serializable {

    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    private Country country;

    private HashMap<String, CountrySubdivision> geographicLevels;

    private AddressBean addressBean;

    private PhysicalAddress physicalAddress;

    /**
     * @return the country
     */
    public Country getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(Country country) {
	this.country = country;
    }

    public void setPhysicalAddress(PhysicalAddress physicalAddress) {
	this.physicalAddress = physicalAddress;
    }

    public PhysicalAddress getPhysicalAddress() {
	return physicalAddress;
    }

    public void setGeographicLevels(HashMap<String, CountrySubdivision> geographicLevels) {
	this.geographicLevels = geographicLevels;
    }

    public HashMap<String, CountrySubdivision> getGeographicLevels() {
	return geographicLevels;
    }

    /**
     * 
     * @param originalCollection
     *            the collection to disconnect and sort
     * @return a 'disconnected', i.e. changes to it won't be propagated, ordered
     *         by {@link CountrySubdivision#getLevel()} ArrayList without null
     *         objects
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<CountrySubdivision> getSubdivisionsOrderedArrayList(Collection<CountrySubdivision> originalCollection)
    {
	ArrayList<CountrySubdivision> geographicLevelsOrdered = new ArrayList<CountrySubdivision>();
	for (CountrySubdivision countrySubdivision : originalCollection) {
	    if (countrySubdivision != null)
		geographicLevelsOrdered.add(countrySubdivision);
	}
	Collections.sort(geographicLevelsOrdered, CountrySubdivision.COMPARATOR_BY_LEVEL);
	return geographicLevelsOrdered;
	
    }

    public void setAddressBean(AddressBean addressBean) {
	this.addressBean = addressBean;
    }

    public AddressBean getAddressBean() {
	return addressBean;
    }

}
