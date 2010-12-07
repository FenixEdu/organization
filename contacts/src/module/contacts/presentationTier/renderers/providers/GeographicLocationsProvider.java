/**
 * 
 */
package module.contacts.presentationTier.renderers.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.joda.time.LocalDate;

import module.contacts.presentationTier.action.bean.PhysicalAddressBean;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.CountrySubdivisionLevelName;
import module.geography.domain.GeographicLocation;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class GeographicLocationsProvider implements DataProvider {

    @Override
    public Object provide(Object source, Object currentValue) {
	PhysicalAddressBean physicalAddressBean = (PhysicalAddressBean) source;
	HashMap<String, CountrySubdivision> geographicLevels = physicalAddressBean.getGeographicLevels();
	Country country = physicalAddressBean.getCountry();
	if (currentValue instanceof CountrySubdivision) {
	    CountrySubdivision countrySubdivision = (CountrySubdivision) currentValue;
	    return countrySubdivision.getCurrentSiblings();
	    // ArrayList<Country> countries = new ArrayList<Country>();
	    // Collections.sort(countries,
	    // GeographicLocation.COMPARATOR_BY_NAME);
	    // return country.getCurrentChildren();
	    // return CountriesProvider.provideCountries();
	    // return country.getCurrentChildren();
	}
	if (currentValue == null) {
	    // let's go to the source and assert what shall we print
	    CountrySubdivision countrySubdivision = getLastNonNullCountrySubDivision(geographicLevels);
	    if (countrySubdivision == null)
		return country.getCurrentChildren();
	    else
		return countrySubdivision.getCurrentChildren();
	}
	return null;
    }

    /**
     * 
     * @param geographicLevels
     *            the original geographicLevels from which the
     *            CountrySubdivision is extracted
     * @return the last non null {@link CountrySubdivision} or null if it
     *         doesn't exist
     */
    private CountrySubdivision getLastNonNullCountrySubDivision(HashMap<String, CountrySubdivision> geographicLevels) {
	ArrayList<CountrySubdivision> countrySubdivisions = PhysicalAddressBean.getSubdivisionsOrderedArrayList(geographicLevels
		.values());
	if (countrySubdivisions.size() == 0)
	    return null;
	else
	    return countrySubdivisions.get(countrySubdivisions.size() - 1);
    }

    @Override
    public Converter getConverter() {
	return null;
    }

}
