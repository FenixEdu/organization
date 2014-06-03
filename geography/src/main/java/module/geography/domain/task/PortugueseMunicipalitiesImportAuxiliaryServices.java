/*
 * @(#)PortugueseMunicipalitiesImportAuxiliaryServices.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Locale;

import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.util.GeographyPropertiesManager;
import module.organization.domain.Accountability;

import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author João Neves
 * @author João Antunes
 * 
 */
public class PortugueseMunicipalitiesImportAuxiliaryServices {

    private static final String CTT_MUNICIPALITIESFILE = "/concelhos.txt";

    Country portugal;

    protected int additions = 0;

    protected int deletions = 0;

    protected int modifications = 0;

    protected int touches = 0;

    private final LocalizedString municipalityLevelName = new LocalizedString().with(new Locale("pt"), "Concelho").with(
            Locale.ENGLISH, "Municipality");

    private static PortugueseMunicipalitiesImportAuxiliaryServices singletonHolder;

    public PortugueseMunicipalitiesImportAuxiliaryServices() {
        super();
    }

    protected static PortugueseMunicipalitiesImportAuxiliaryServices getInstance() {
        if (singletonHolder == null) {
            singletonHolder = new PortugueseMunicipalitiesImportAuxiliaryServices();
        }
        return singletonHolder;
    }

    @Atomic
    public void executeTask(PortugueseMunicipalitiesImport originalTask) {
        // let's retrieve Portugal, if it doesn't exist, we must create it
        portugal = Country.getPortugal();
        LineNumberReader reader = null;
        ArrayList<CountrySubdivision> municipalitiesOnFile = new ArrayList<CountrySubdivision>();
        ArrayList<CountrySubdivision> existingMunicipalities = new ArrayList<CountrySubdivision>();

        try {
            File file =
                    new File(GeographyPropertiesManager.getConfiguration().getGeographyImportFilesLocation()
                            + CTT_MUNICIPALITIESFILE);
            DateTime lastReview = new DateTime(file.lastModified());
            FileInputStream fileReader = new FileInputStream(file);
            reader = new LineNumberReader(new InputStreamReader(fileReader, "ISO-8859-1"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String districtCode = parts[0];
                String municipalityCode = parts[1];
                String municipalityName = parts[2];
                CountrySubdivision district = portugal.getChildByCode(districtCode);
                CountrySubdivision municipality = null;
                if (district == null) {
                    // abort the transaction!! throw some kind of exception
                    // and warn the user of the outcome
                    originalTask.auxLogInfo("Script aborted because the district with code: " + districtCode
                            + " couldn't be found");
                    throw new RuntimeException("Script aborted because the district with code: " + districtCode
                            + " couldn't be found");
                } else {
                    // get the municipality
                    municipality = district.getChildByCode(municipalityCode);
                    if (municipality == null) {
                        municipality = createMunicipality(district, municipalityCode, municipalityName, lastReview);
                    } else {
                        modifyMunicipality(municipality, municipalityCode, municipalityName, "", lastReview);

                    }
                }
                municipalitiesOnFile.add(municipality);
            }

            // 'removing' all of the existing municipalities

            // getting all of the existing districts
            for (CountrySubdivision countrySubdivision : portugal.getChildren()) {
                if (countrySubdivision.getLevelName().getContent(new Locale("pt"))
                        .equalsIgnoreCase(municipalityLevelName.getContent(new Locale("pt")))) {
                    existingMunicipalities.add(countrySubdivision);
                }
            } // let's assert
              // which ones are in excess and remove them
            existingMunicipalities.removeAll(municipalitiesOnFile);
            for (CountrySubdivision countrySubdivision : existingMunicipalities) {
                removeMunicipality(countrySubdivision);
            }
            originalTask.auxLogInfo("File last modification was: " + lastReview);
            originalTask.auxLogInfo(additions + " municipalities added.");
            originalTask.auxLogInfo(touches + " municipalities unmodified, but whose date has changed.");
            originalTask.auxLogInfo(modifications + " municipalities modified");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private void removeMunicipality(CountrySubdivision municipality) {
        // modify the district by setting the accountability of the last one
        // end before the date of the last review
        Accountability activeAccountability = null;
        for (Accountability accountability : municipality.getUnit().getParentAccountabilities(
                municipality.getOrCreateAccountabilityType())) {
            if (accountability.isActiveNow()) {
                activeAccountability = accountability;
            }
        }

        if (activeAccountability != null) {
            // make the accountability expire a minimal measure of time before
            // of
            // the current date
            LocalDate endDate = new LocalDate();
            // endDate = endDate.minusDays(1);
            activeAccountability.setEndDate(endDate);
        }
        deletions++;

    }

    private void modifyMunicipality(CountrySubdivision municipality, String municipalityCode, String municipalityName,
            String municipalityAcronym, DateTime lastReview) {
        String originalMunicipalityName = municipality.getName().getContent(new Locale("pt"));
        String originalMunicipalityAcronym = municipality.getAcronym();
        // let's check if there are changes on the data:
        if (!originalMunicipalityName.equals(municipalityName) || !originalMunicipalityAcronym.equals(municipalityAcronym)
                || !municipality.getLevelName().equals(municipalityLevelName)) {

            removeMunicipality(municipality);
            deletions--;
            // create the new district!
            // lets correct the counters afterwards!
            createMunicipality(municipality.getParentSubdivision(), municipalityCode, municipalityName, lastReview);
            additions--;
            modifications++;
        }
        // if not let's just 'touch' it
        else {
            touchMunicipality(municipality, lastReview);
        }

    }

    private void touchMunicipality(CountrySubdivision municipality, DateTime lastReview) {
        municipality.setLastReview(lastReview);
        touches++;
    }

    private CountrySubdivision createMunicipality(CountrySubdivision district, String municipalityCode, String municipalityName,
            DateTime lastReview) {
        CountrySubdivision municipality = new CountrySubdivision(district, municipalityName, "", municipalityCode);
        municipality.setLevelName(municipalityLevelName, false);
        municipality.setLastReview(lastReview);
        additions++;
        return municipality;
    }

}
