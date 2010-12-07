/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import module.contacts.presentationTier.action.bean.addressbean.*;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class AddressBeanFactory {
  
    private AddressBeanFactory() {
    }

    /**
     * @param country
     *            the Country {@link Country} to base the AddressBean on
     * @return An AddressBean suited for the given country - note, the schema
     *         must be hardcoded on the contacts-schemas based on the return
     *         result of this method
     */
    public static AddressBean createAddressBean(Country country) {
	// let's return an AbstractAddressBean taking into account the country
	// of the given geographic location
	if (country.equals(Country.getPortugal())) {
	    return new PortugalAddressBean();

	} else {

	    return new ForeignAddressBean();

	}

    }

}
