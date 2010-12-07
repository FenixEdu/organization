/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

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

}
