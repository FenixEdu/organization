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

public class PortugueseDistrictImport extends PortugueseDistrictImport_Base {

    private static final String CTT_DISTRICTFILE = "/distritos.txt";

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
