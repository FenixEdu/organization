package module.contacts.presentationTier.action.bean.addressbean;

import module.contacts.presentationTier.action.bean.AddressBean;
import module.geography.domain.GeographicLocation;

/**
 * Refactor of the PortugalAddressBean and ForeignAddressBean that share some
 * common fields
 * 
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public abstract class CommonAddressBean extends AddressBean {
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private GeographicLocation geographicLocation;

    /**
     * @return the addressLineOne
     */
    public String getAddressLineOne() {
	return addressLineOne;
    }

    /**
     * @param addressLineOne
     *            the addressLineOne to set
     */
    public void setAddressLineOne(String addressLineOne) {
	this.addressLineOne = addressLineOne;
    }

    /**
     * @return the addressLineTwo
     */
    public String getAddressLineTwo() {
	return addressLineTwo;
    }

    /**
     * @param addressLineTwo
     *            the addressLineTwo to set
     */
    public void setAddressLineTwo(String addressLineTwo) {
	this.addressLineTwo = addressLineTwo;
    }

    /**
     * @return the city
     */
    public String getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    @Override
    public GeographicLocation getGeographicLocation() {
	return geographicLocation;
    }

    @Override
    public void setGeographicLocation(GeographicLocation geographicLocation) {
	this.geographicLocation = geographicLocation;
    }

}
