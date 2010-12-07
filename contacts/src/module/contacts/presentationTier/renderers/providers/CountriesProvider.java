/**
 * 
 */
package module.contacts.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.contacts.domain.ContactsConfigurator;
import module.geography.domain.Country;
import myorg.domain.MyOrg;
import myorg.domain.groups.PersistentGroup;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class CountriesProvider implements DataProvider {

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
	final ArrayList<Country> listOfCountries = new ArrayList<Country>(MyOrg.getInstance().getCountries());
	return listOfCountries;
    }

    public static Object provideCountries() {
	ArrayList<Country> listOfCountries = new ArrayList<Country>(MyOrg.getInstance().getCountries());
	return listOfCountries;
    }

}
