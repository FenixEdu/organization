/**
 * 
 */
package module.contacts.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.contacts.domain.ContactsConfigurator;
import myorg.domain.MyOrg;
import myorg.domain.groups.PersistentGroup;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class VisibilityGroupsProvider implements DataProvider {

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
	// TODO ?? add a check to see if the source the user that is set on the
	// source bean has privileges to see the visibility information of this
	// contact information
	final ArrayList<PersistentGroup> listOfGroups = new ArrayList<PersistentGroup>(ContactsConfigurator.getInstance()
		.getVisibilityGroups());
	return listOfGroups;
    }

}
