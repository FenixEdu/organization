package module.organization.domain.search;

import java.util.ResourceBundle;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.utl.ist.fenix.tools.util.i18n.Language;


public enum PartySearchType implements IPresentableEnum {

    ASCENDETS_AND_DESCENDENTS, LOCAL_TREE;

    @Override
    public String getLocalizedName() {
	try {
	    final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.OrganizationResources", Language.getLocale());
	    return resourceBundle.getString(PartySearchType.class.getSimpleName() + "." + name());
	} catch (final Exception ex) {
	    ex.printStackTrace();
	    throw new Error(ex);
	}
    }

}
