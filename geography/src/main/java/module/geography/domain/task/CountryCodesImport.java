/*
 * @(#)CountryCodesImport.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import module.geography.domain.Country;
import module.geography.domain.Planet;
import module.geography.util.AddressPrinter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.fenixedu.bennu.scheduler.CronTask;
import org.fenixedu.bennu.scheduler.annotation.Task;
import org.fenixedu.commons.i18n.LocalizedString;

/**
 * Import ISO-3166-1 country codes list. Source import file located in:
 * http://www.davros.org/misc/iso3166.txt
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
@Task(englishTitle = "Import Country Codes from CSV")
public class CountryCodesImport extends CronTask {
    private static final String ISO3166_FILE = "/iso-3166.csv";

    @Override
    public void runTask() {
        InputStream stream = getClass().getResourceAsStream(ISO3166_FILE);
        try {
            List<String> lines = IOUtils.readLines(stream);
            Planet planet = Magrathea.buildEarth();
            for (String line : lines) {
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
                    country =
                            new Country(planet, shortCode, longCode, Integer.parseInt(numericCode), makeName(countryNamePt,
                                    countryNameEn), makeName(nationalityPt, nationalityEn), AddressPrinter.class);
                } else {
                    country.update(planet, shortCode, longCode, Integer.parseInt(numericCode),
                            makeName(countryNamePt, countryNameEn), makeName(nationalityPt, nationalityEn), AddressPrinter.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public String getLocalizedName() {
        return getClass().getName();
    }

    private static LocalizedString makeName(String pt, String en) {
        if (pt != null || en != null) {
            LocalizedString name = new LocalizedString();
            if (pt != null) {
                name = name.with(new Locale("pt"), WordUtils.capitalizeFully(pt));
            }
            if (en != null) {
                name = name.with(Locale.ENGLISH, WordUtils.capitalizeFully(en));
            }
            return name;
        }
        return null;
    }
}
