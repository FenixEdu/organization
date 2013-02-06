/*
 * @(#)PersonGroup.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Organization Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Organization Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.organization.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.organization.domain.Party;
import module.organization.domain.Person;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Pedro Santos
 * @author Susana Fernandes
 * 
 */
public class PersonGroup extends PersonGroup_Base {

    public PersonGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Service
    public static PersonGroup getInstance() {
        final PersonGroup personGroup = (PersonGroup) PersistentGroup.getSystemGroup(PersonGroup.class);
        return personGroup == null ? new PersonGroup() : personGroup;
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (final Party party : MyOrg.getInstance().getPartiesSet()) {
            if (party.isPerson()) {
                users.add(((Person) party).getUser());
            }
        }
        return users;
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle("resources/OrganizationResources",
                "label.persistent.group.personGroup.name");
    }

    @Override
    public boolean isMember(User user) {
        return user != null && user.getPerson() != null;
    }

}
