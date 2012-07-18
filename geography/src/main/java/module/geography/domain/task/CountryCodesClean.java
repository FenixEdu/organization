/*
 * @(#)CountryCodesClean.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Pedro Santos
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module.
 *
 *   The Geography Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Geography Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Geography Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.geography.domain.task;

import java.util.ArrayList;
import java.util.HashMap;

import pt.ist.fenixWebFramework.services.Service;

import module.geography.domain.Country;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.util.BundleUtil;

/**
 * 
 * @author João Antunes
 * 
 */
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
