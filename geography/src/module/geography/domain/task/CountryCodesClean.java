package module.geography.domain.task;

import java.util.ArrayList;
import java.util.HashMap;

import pt.ist.fenixWebFramework.services.Service;

import module.geography.domain.Country;
import myorg.domain.MyOrg;
import myorg.util.BundleUtil;

public class CountryCodesClean extends CountryCodesClean_Base {


    private int countryDeletes = 0;

    public CountryCodesClean() {
	super();
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/GeographyResources", "label.task.clean.country.codes.with.note");
    }

    @Override
    @Service
    public void executeTask() {
	ArrayList<Country> countriesToClean = new ArrayList<Country>();
	countriesToClean.add(Country.getPortugal());
	HashMap<String, ArrayList<Integer>> infoByCountry = new HashMap<String, ArrayList<Integer>>();

	for (Country country : MyOrg.getInstance().getCountries()) {
	    // try {
		country.delete();
		countryDeletes++;
	    // } catch (Error e) {
	    // logInfo("Error, trying to delete country: " + country.getName()
	    // +
	    // " in order to be able to delete it, please clean the CountrySubdivisionLevelNames");
	    // logInfo("Got error: " + e.getMessage());
	    // }
	}

	logInfo("Deleted " + countryDeletes + " countries");

    }

}
