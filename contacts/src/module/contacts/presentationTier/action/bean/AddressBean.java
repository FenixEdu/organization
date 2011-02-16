/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

import module.geography.domain.GeographicLocation;

/**
 * Class used by the AddressBeanFactory
 * 
 * @see #getType()
 * @see AddressBeanFactory
 * 
 * 
 * 
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public abstract class AddressBean implements Serializable {

    /**
     * Default serializable {@link Serializable} version
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return the getClass().getName() {@link Class#getName()} of the concrete
     *         address bean that should extend this class. This method is used
     *         to retrieve the schema associated with the concrete
     *         implementation of the bean bean
     */
    public final String getSchema() {
	return this.getClass().getName();
//	return this.getClass().getPackage() + "." + nameCountry + this.getClass().getCanonicalName();
    }

    public abstract String getComplementarAddress();

    /**
     * 
     * @return the closest GeographicLocation to the given address which is
     *         known by the system
     */
    public abstract GeographicLocation getGeographicLocation();

    public abstract void setGeographicLocation(GeographicLocation geographicLocation);

    /**
     * 
     * @return true if the address is valid, that is, if the GeographicLocation
     *         and the given fields do all match, false otherwise;
     */
    public abstract boolean isValid();

}
