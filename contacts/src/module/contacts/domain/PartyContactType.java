/**
 * 
 */
package module.contacts.domain;

import java.util.ResourceBundle;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.utl.ist.fenix.tools.util.i18n.Language;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public enum PartyContactType implements IPresentableEnum {
	PERSONAL,

	WORK,

	IMMUTABLE;

	@Override
	public String getLocalizedName() {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"resources.ContactsResources", Language.getLocale());
		return resourceBundle.getString(PartyContactType.class
				.getSimpleName()
				+ "." + name());
	}

}
