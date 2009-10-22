package module.personalinformation.domain;

import module.organization.presentationTier.actions.OrganizationModelAction;
import module.personalinformation.presentationTier.actions.PersonalInformationManagementAction.PersonalInformationView;
import myorg.domain.ModuleInitializer;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;

public class PersonalInformationSystem extends PersonalInformationSystem_Base implements ModuleInitializer {

    private static boolean isInitialized = false;

    private static ThreadLocal<PersonalInformationSystem> init = null;

    public static PersonalInformationSystem getInstance() {
	if (init != null) {
	    return init.get();
	}
	
	if (!isInitialized) {
	    initialize();
	}
	final MyOrg myOrg = MyOrg.getInstance();
	return myOrg.getPersonalInformationSystem();
    }

    @Service
    public synchronized static void initialize() {
	if (!isInitialized) {
	    try {
		final MyOrg myOrg = MyOrg.getInstance();
		final PersonalInformationSystem initializer = myOrg.getPersonalInformationSystem();
		if (initializer == null) {
		    new PersonalInformationSystem();
		}
		init = new ThreadLocal<PersonalInformationSystem>();
		init.set(myOrg.getPersonalInformationSystem());

		isInitialized = true;
	    } finally {
		init = null;
	    }
	}
    }

    private PersonalInformationSystem() {
        super();
        setMyOrg(MyOrg.getInstance());
    }

    @Override
    public void init(final MyOrg root) {
	OrganizationModelAction.partyViewHookManager.register(new PersonalInformationView());
    }

}
