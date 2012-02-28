package module.geography.domain.task;

import module.geography.domain.Country;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class PortugueseMunicipalitiesImport extends PortugueseDistrictImport_Base {

    private static final String CTT_MUNICIPALITIESFILE = "/concelhos.txt";

    Country portugal;

    protected int additions = 0;

    protected int deletions = 0;

    protected int modifications = 0;

    protected int touches = 0;

    private final MultiLanguageString municipalityLevelName = new MultiLanguageString().with(Language.pt, "Concelho").with(
	    Language.en, "Municipality");

    public PortugueseMunicipalitiesImport() {
	super();
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/GeographyResources",
		"label.task.ctt.portugal.municipalities.import");
    }

    protected void auxLogInfo(String message) {
	logInfo(message);
    }

    @Override
    @Service
    public void executeTask() {
	// let's initialize the auxiliary class due to the the nasty injector
	// errors
	PortugueseMunicipalitiesImportAuxiliaryServices aux = PortugueseMunicipalitiesImportAuxiliaryServices.getInstance();
	aux.executeTask(this);
    }

}
