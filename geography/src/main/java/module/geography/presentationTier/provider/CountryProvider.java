/**
 * 
 */
package module.geography.presentationTier.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.geography.domain.Country;

import org.fenixedu.bennu.core.domain.Bennu;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author Joao Carvalho (joao.pedro.carvalho@ist.utl.pt)
 * 
 */
public class CountryProvider implements DataProvider {

    @Override
    public Converter getConverter() {
        return null;
    }

    @Override
    public Object provide(Object arg0, Object arg1) {

        List<Country> countries = new ArrayList<Country>(Bennu.getInstance().getCountriesSet());

        Collections.sort(countries, Country.COMPARATOR_BY_NAME);

        return countries;
    }

}
