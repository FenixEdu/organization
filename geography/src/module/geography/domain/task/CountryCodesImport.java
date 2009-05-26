/*
 * @(#)CountryCodesImport.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module for the MyOrg web application.
 *
 *   The Geography Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import module.geography.domain.Country;
import module.geography.domain.Planet;
import myorg._development.PropertiesManager;
import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * Import ISO-3166-1 country codes list. Source import file located in:
 * http://www.davros.org/misc/iso3166.txt
 * 
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class CountryCodesImport extends CountryCodesImport_Base {

    private static final String ISO3166_FILE = "/iso-3166.csv";
    private int touches = 0;
    private int additions = 0;

    @Override
    public void executeTask() {
	Planet planet = Magrathea.buildEarth();
	LineNumberReader reader = null;
	try {
	    File file = new File(PropertiesManager.getProperty("file.import.location") + ISO3166_FILE);
	    if (getIsoFileLastModified() == null || file.lastModified() > getIsoFileLastModified().getMillis()) {
		DateTime lastReview = new DateTime(file.lastModified());
		reader = new LineNumberReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    String[] parts = line.split(";");
		    String shortCode = parts[0];
		    String longCode = parts[1];
		    String numericCode = parts[2];
		    String countryNameEn = parts[3];
		    String countryNamePt = parts.length > 5 && StringUtils.isNotEmpty(parts[5]) ? parts[5] : null;
		    String nationalityEn = parts.length > 6 && StringUtils.isNotEmpty(parts[6]) ? parts[6] : null;
		    String nationalityPt = parts.length > 7 && StringUtils.isNotEmpty(parts[7]) ? parts[7] : null;
		    Country country = planet.getChildByAcronym(longCode);
		    if (country == null) {
			country = createCountry(planet, shortCode, longCode, Integer.parseInt(numericCode), makeName(
				countryNamePt, countryNameEn), makeName(nationalityPt, nationalityEn), lastReview);
		    } else {
			touchCountry(country, lastReview);
		    }
		}
		// TODO: cleanup
		logInfo("File last modification was: " + lastReview);
		logInfo(additions + " countries added.");
		logInfo(touches + " countries unmodified.");
		setLastModified(lastReview);
	    } else {
		logInfo("File unmodified, nothing imported.");
	    }
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

    @Service
    private void setLastModified(DateTime lastReview) {
	setIsoFileLastModified(lastReview);
    }

    @Service
    private Country createCountry(Planet parent, String shortCode, String longCode, Integer numericCode,
	    MultiLanguageString countryName, MultiLanguageString nationality, DateTime lastReview) {
	Country country = new Country(parent, shortCode, longCode, numericCode, countryName, nationality);
	country.setLastReview(lastReview);
	additions++;
	return country;
    }

    @Service
    private void touchCountry(Country country, DateTime lastReview) {
	country.setLastReview(lastReview);
	touches++;
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/GeographyResources", "label.task.iso3166.import");
    }

    private MultiLanguageString makeName(String pt, String en) {
	if (pt != null || en != null) {
	    MultiLanguageString name = new MultiLanguageString();
	    if (pt != null) {
		name.setContent(Language.pt, WordUtils.capitalizeFully(pt));
	    }
	    if (en != null) {
		name.setContent(Language.en, WordUtils.capitalizeFully(en));
	    }
	    return name;
	} else
	    return null;
    }
}
