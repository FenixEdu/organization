/*
 * @(#)CleanCountrySubLevels.java
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

import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.CountrySubdivisionLevelName;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

/**
 * *WARNING* This task shouldn't be called to delete the districts as it removes
 * them without checking for connections that it might have with other domain
 * objects. The right way to remove a district is to put its accountibility date
 * end in this moment and maybe eventually if it's not connected to anything
 * delete it
 * 
 * @author João Antunes
 * 
 */
public class CleanCountrySubLevels extends CleanCountrySubLevels_Base {

    /* (non-Javadoc)
     * @see pt.ist.bennu.core.domain.scheduler.Task#getLocalizedName()
     */
    @Override
    public String getLocalizedName() {
        return BundleUtil.getStringFromResourceBundle("resources/GeographyResources",
                "label.task.clean.country.sublevels.with.note");
    }

    private int countrySubDivisionLevelNameDeletes = 0;

    private int countrySubDivisionDeletes = 0;

    /* (non-Javadoc)
     * @see pt.ist.bennu.core.domain.scheduler.Task#executeTask()
     */
    @Atomic
    @Override
    public void executeTask() {
        // add to an array all of the countries one wants to clean the sublevels
        ArrayList<Country> countriesToClean = new ArrayList<Country>();
        countriesToClean.add(Country.getPortugal());
        countriesToClean.addAll(MyOrg.getInstance().getCountriesSet());
        HashMap<String, ArrayList<Integer>> infoByCountry = new HashMap<String, ArrayList<Integer>>();

        for (Country country : countriesToClean) {
            ArrayList<CountrySubdivision> countrySubdivisions = new ArrayList<CountrySubdivision>();
            countrySubdivisions.addAll(country.getChildren());
            for (CountrySubdivision countrySubdivision : countrySubdivisions) {
                // countrySubdivision.setPhysicalAddress(null); TODO implement it
                // in a listener in the Contacts module
                countrySubdivision.delete();
                countrySubDivisionDeletes++;
            }
            ArrayList<CountrySubdivisionLevelName> subdivisionLevelNames = new ArrayList<CountrySubdivisionLevelName>();
            subdivisionLevelNames.addAll(country.getLevelName());

            for (CountrySubdivisionLevelName countrySubdivisionLevelName : subdivisionLevelNames) {
                countrySubdivisionLevelName.setCountry(null);
                countrySubdivisionLevelName.delete();
                countrySubDivisionLevelNameDeletes++;
            }
            ArrayList<Integer> integers = new ArrayList<Integer>();
            integers.add(new Integer(countrySubDivisionDeletes));
            integers.add(new Integer(countrySubDivisionLevelNameDeletes));

            infoByCountry.put(country.getName().getContent(), integers);
        }

        for (String country : infoByCountry.keySet()) {
            logInfo("Cleaned the following registries for " + country + ":");
            ArrayList<Integer> integers = infoByCountry.get(country);
            logInfo("CountrySubDivision deletes: " + integers.get(0));
            logInfo("CountrySubDivisionLevelName deletes: " + integers.get(1));
        }

    }

}
