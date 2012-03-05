/*
 * @(#)PersonalInformationSystem.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Personal Information Module.
 *
 *   The Personal Information Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Personal Information Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Personal Information Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.personalinformation.domain;

import module.organization.presentationTier.actions.OrganizationModelAction;
import module.personalinformation.presentationTier.actions.PersonalInformationManagementAction.PersonalInformationView;
import myorg.domain.ModuleInitializer;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Luis Cruz
 * 
 */
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
