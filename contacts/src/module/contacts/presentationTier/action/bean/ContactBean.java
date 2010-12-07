/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class ContactBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    private String value;

    /**
     * @return the value
     */
    public String getValue() {
	return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
	this.value = value;
    }

}
