package module.geography.presentationTier.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.geography.domain.Country;
import myorg.domain.MyOrg;
import myorg.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class CountryAutoCompleteProvider implements AutoCompleteProvider {

    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
	final List<Country> countries = new ArrayList<Country>();

	final String trimmedValue = value.trim();
	final String[] input = trimmedValue.split(" ");
	StringNormalizer.normalize(input);

	for (final Country country : MyOrg.getInstance().getCountriesSet()) {
	    final String countryName = StringNormalizer.normalize(country.getName().getContent());
	    if (hasMatch(input, countryName)) {
		countries.add(country);
	    } else {
		final String countryAcronym = StringNormalizer.normalize(country.getAcronym());
		if (hasMatch(input, countryAcronym)) {
		    countries.add(country);
		}
	    }	    
	}

	Collections.sort(countries, Country.COMPARATOR_BY_NAME);

	return countries;
    }

    private boolean hasMatch(final String[] input, final String countryNameParts) {
	for (final String namePart : input) {
	    if (countryNameParts.indexOf(namePart) == -1) {
		return false;
	    }
	}
	return true;
    }

}
