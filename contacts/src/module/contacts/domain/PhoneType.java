/**
 * 
 */
package module.contacts.domain;

import java.util.ResourceBundle;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.ist.fenixframework.plugins.luceneIndexing.IndexableField;
import pt.utl.ist.fenix.tools.util.i18n.Language;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public enum PhoneType implements IPresentableEnum, IndexableField {
    CELLPHONE,

    REGULAR_PHONE,

    EXTENSION,

    VOIP_SIP;

    @Override
    public String getLocalizedName() {
	final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.ContactsResources", Language.getLocale());
	return resourceBundle.getString(PhoneType.class.getSimpleName() + "." + name());
    }

    @Override
    public String getFieldName() {
	return Phone.class.getName() + "." + name();
    }

}
