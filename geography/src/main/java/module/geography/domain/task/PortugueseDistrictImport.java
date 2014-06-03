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

import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.bennu.scheduler.CronTask;
import org.fenixedu.bennu.scheduler.annotation.Task;

/**
 * 
 * @author João Antunes
 * 
 */
@Task(englishTitle = "Import Portuguese District from CSV")
public class PortugueseDistrictImport extends CronTask {

    public PortugueseDistrictImport() {
        super();
    }

    @Override
    public String getLocalizedName() {
        return BundleUtil.getString("resources/GeographyResources", "label.task.ctt.portugal.districts.import");
    }

    protected void auxLogInfo(String message) {
        taskLog(message);
    }

    @Override
    public void runTask() {
        // let's initialize the auxiliary class due to the the nasty injector
        // errors
        PortugueseDistrictImportAuxiliaryServices aux = PortugueseDistrictImportAuxiliaryServices.getInstance();
        aux.executeTask(this);
    }

}
