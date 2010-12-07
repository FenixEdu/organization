/**
 * 
 */
package module.contacts.presentationTier.bean;

import java.io.Serializable;

import module.contacts.domain.PhysicalAddress;
import module.contacts.presentationTier.renderers.PhysicalAddressBeanInputRendererWithPostBack;
import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;

/**
 * Bean for the PhysicalAddress
 * 
 * This bean has an associated render
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

    private GeographicLocation physicalAddressLocation;

    private String complementarAddress;

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

    /**
     * @return the physicalAddressLocation
     */
    public GeographicLocation getPhysicalAddressLocation() {
	return physicalAddressLocation;
    }

    /**
     * @param physicalAddressLocation
     *            the physicalAddressLocation to set
     */
    public void setPhysicalAddressLocation(GeographicLocation physicalAddressLocation) {
	this.physicalAddressLocation = physicalAddressLocation;
    }

    /**
     * @return the complementarAddress
     */
    public String getComplementarAddress() {
	return complementarAddress;
    }

    /**
     * @param complementarAddress
     *            the complementarAddress to set
     */
    public void setComplementarAddress(String complementarAddress) {
	this.complementarAddress = complementarAddress;
    }

}
