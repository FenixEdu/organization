/*
 * @(#)PortugueseDistrictImport.java
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.GeographicLocation;
import module.organization.domain.Accountability;
import module.organization.domain.Unit;
import myorg._development.PropertiesManager;
import myorg.util.BundleUtil;

/**
 * 
 * @author João Antunes
 * 
 */
public class PortugueseDistrictImport extends PortugueseDistrictImport_Base {


    public PortugueseDistrictImport() {
	super();
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/GeographyResources", "label.task.ctt.portugal.districts.import");
    }

    protected void auxLogInfo(String message) {
	logInfo(message);
    }

    @Override
    public void executeTask() {
	// let's initialize the auxiliary class due to the the nasty injector
	// errors
	PortugueseDistrictImportAuxiliaryServices aux = PortugueseDistrictImportAuxiliaryServices.getInstance();
	aux.executeTask(this);
    }

}
