/**
 * 
 */
package module.contacts.presentationTier.renderers.providers;

import java.util.ArrayList;

import myorg.domain.MyOrg;
import myorg.domain.groups.PersistentGroup;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class PersistentGroupsProvider implements DataProvider {

    /*
     * (non-Javadoc)
     * 
     * @see pt.ist.fenixWebFramework.renderers.DataProvider#getConverter()
     */
    @Override
    public Converter getConverter() {
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * pt.ist.fenixWebFramework.renderers.DataProvider#provide(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public Object provide(Object source, Object currentValue) {
	final ArrayList<PersistentGroup> listOfGroups = new ArrayList<PersistentGroup>(MyOrg.getInstance().getPersistentGroups());
	return listOfGroups;
    }

}
