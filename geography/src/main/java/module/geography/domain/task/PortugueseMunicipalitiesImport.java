/*
 * @(#)PortugueseMunicipalitiesImport.java
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

import java.util.Locale;

import module.geography.domain.Country;

import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.bennu.scheduler.CronTask;
import org.fenixedu.bennu.scheduler.annotation.Task;
import org.fenixedu.commons.i18n.LocalizedString;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author João Antunes
 * 
 */
@Task(englishTitle = "Import Portuguese Municipalities from TXT file")
public class PortugueseMunicipalitiesImport extends CronTask {

    private static final String CTT_MUNICIPALITIESFILE = "/concelhos.txt";

    Country portugal;

    protected int additions = 0;

    protected int deletions = 0;

    protected int modifications = 0;

    protected int touches = 0;

    private final LocalizedString municipalityLevelName = new LocalizedString().with(new Locale("pt"), "Concelho").with(
            Locale.ENGLISH, "Municipality");

    public PortugueseMunicipalitiesImport() {
        super();
    }

    @Override
    public String getLocalizedName() {
        return BundleUtil.getString("resources/GeographyResources", "label.task.ctt.portugal.municipalities.import");
    }

    protected void auxLogInfo(String message) {
        taskLog(message);
    }

    @Atomic
    @Override
    public void runTask() {
        // let's initialize the auxiliary class due to the the nasty injector
        // errors
        PortugueseMunicipalitiesImportAuxiliaryServices aux = PortugueseMunicipalitiesImportAuxiliaryServices.getInstance();
        aux.executeTask(this);
    }

}
