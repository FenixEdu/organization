/*
 * @(#)ContactsConfigurator.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import module.organization.domain.Person;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.domain.groups.Role;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.dml.runtime.Relation;
import pt.ist.fenixframework.dml.runtime.RelationListener;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * 
 * @author João Antunes
 * @author Pedro Amaral
 * @author Susana Fernandes
 * 
 */
public class ContactsConfigurator extends ContactsConfigurator_Base {

    public static final int SEARCH_MAXELEMENTS_PER_PAGE = 10;

    // TODO validate this singleton implementation
    private ContactsConfigurator() {
        super();
        MyOrg.getInstance().setContactsConfigurator(this);
        getRelationPersistentGroupContactsConfigurator().addListener(new VisibilityGroupsEnforcerListener());
    }

    @Atomic
    public static ContactsConfigurator getInstance() {
        ContactsConfigurator contactsConfigurator = MyOrg.getInstance().getContactsConfigurator();
        if (contactsConfigurator == null) {
            return createInstance();
        }
        return contactsConfigurator;

    }

    /**
     * 
     * @param className
     *            the .getClass().getName() String that represents the class
     * @return the JavaScript regular expression for the given className
     */
    public static String getJSRegExp(String className) {
        if (className.equalsIgnoreCase(WebAddress.class.getName())) {
            return "((http|https):\\/\\/)?(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";
        } else if (className.equalsIgnoreCase(EmailAddress.class.getName())) {
            return "(\\w+(\\.\\w)*@(\\w+\\.\\w+)+){1}";
        }
        return "(.*)";

    }

    /**
     * Listener used to make sure that all of the contacts are updated when a
     * visibility group is removed from the list of visibility groups
     * 
     * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
     * 
     */
    final static class VisibilityGroupsEnforcerListener implements RelationListener<PersistentGroup, ContactsConfigurator> {

        @Override
        public void afterAdd(Relation<PersistentGroup, ContactsConfigurator> arg0, PersistentGroup arg2, ContactsConfigurator arg1) {
            // nothing needs to be done when adding a group to the list of the
            // possible visibility groups
        }

        /**
         * Iterate through all of the contacts and remove the relation that it
         * might have with that group
         */
        @Override
        public void afterRemove(Relation<PersistentGroup, ContactsConfigurator> relation, PersistentGroup persistentGroup,
                ContactsConfigurator contactsConfigurator) {
            for (PartyContact contact : ContactsConfigurator.getInstance().getPartyContact()) {
                contact.removeVisibilityGroups(persistentGroup);
            }
        }

        @Override
        public void beforeAdd(Relation<PersistentGroup, ContactsConfigurator> arg0, PersistentGroup arg2,
                ContactsConfigurator arg1) {
            // nothing needs to be done when adding a group to the list of the
            // possible visibility groups
        }

        @Override
        public void beforeRemove(Relation<PersistentGroup, ContactsConfigurator> relation, PersistentGroup persistentGroup,
                ContactsConfigurator contactsConfigurator) {
            // nothing needs to be done before removing the relation

        }

    }

    @Atomic
    private static ContactsConfigurator createInstance() {
        return new ContactsConfigurator();
    }

    @Atomic
    public void setVisibilityGroups(Collection<PersistentGroup> groups) {

        if (groups == null) {
            return;
        }
        Set<PersistentGroup> existingGroups = getVisibilityGroups();
        // add the ones on the groups to the list of existing
        for (PersistentGroup persistentGroup : groups) {
            if (!existingGroups.contains(persistentGroup)) {
                if (persistentGroup.getGroupAlias() == null) {
                    GroupAlias.create(persistentGroup, new MultiLanguageString(persistentGroup.getName()));
                }
                addVisibilityGroups(persistentGroup);
            }
        }
        // remove the ones that aren't on the existing list
        for (PersistentGroup persistentGroup : existingGroups) {
            if (!groups.contains(persistentGroup)) {
                removeVisibilityGroups(persistentGroup);
            }
        }
    }

    /**
     * In case that the name wasn't
     * completely-self-explanatory-as-well-as-long-as-hell-:), this method adds
     * the super editor role to the current list of users in Group groupToUse
     * 
     * @param groupToUse
     *            the group to which all of the current members will be assigned
     *            the super-editor role
     */
    @Atomic
    public void assignSuperEditorToPersonsCurrentlyIn(PersistentGroup groupToUse) {
        Set<User> groupMembers = groupToUse.getMembers();
        for (User user : groupMembers) {
            user.addRoleType(getContactsRoles().MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR);
        }
    }

    @Atomic
    public void assignSuperEditorRole(User user) {
        Role.getRole(getContactsRoles().MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).addUsers(user);

    }

    @Atomic
    public void removeSuperEditorRole(User user) {
        Role.getRole(getContactsRoles().MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).removeUsers(user);
    }

    public List<Person> getPersonsByDetails(User userSearching, String searchName, String searchUsername, String searchPhone,
            PhoneType searchPhoneType, String searchAddress, String searchWebAddress, String searchEmailAddress) {
        return ContactsConfiguratorAux.getPersonsByDetailsV2(userSearching, searchName, searchUsername, searchPhone,
                searchPhoneType, searchAddress, searchWebAddress, searchEmailAddress);
    }

    /**
     * 
     * 
     * @param userSearching
     *            the User that triggered the search so that the visibility
     *            information can be taken into consideration when showing the
     *            results
     * @param searchName
     *            the name or part of the name of the person/persons to search
     *            or null
     * @param searchUsername
     *            the username or null
     * @param searchPhone
     *            the phone of the given searchPhoneType or null
     * @param searchPhoneType
     *            the PhoneType that the searchPhone refers to or null
     * @param searchAddress
     *            the physical address or null
     * @param searchWebAddress
     *            the webaddress or null
     * @return An ArrayList of persons that match the given search criterias,
     *         taking into account the visibility of the information to the user
     *         that triggered the search or null if none is found
     */
    // public ArrayList<Person> getPersonsByDetails(User userSearching, String
    // searchName, String searchUsername,
    // String searchPhone, PhoneType searchPhoneType, String searchAddress,
    // String searchWebAddress,
    // String searchEmailAddress) {
    // if (userSearching == null)
    // return null;
    //
    // ArrayList<Person> searchResult = new ArrayList<Person>();
    //
    // ArrayList<Person> searchByNameResult = null;
    // ArrayList<Person> searchByUsernameResult;
    //
    // if (searchName != null) {
    // searchByNameResult = new ArrayList<Person>();
    // final String trimmedValue = searchName.trim();
    // final String[] input = trimmedValue.split(" ");
    //
    // for (final Person person : MyOrg.getInstance().getPersonsSet()) {
    // if (hasMatch(input, person.getName())) {
    // searchByNameResult.add(person);
    // }
    // }
    // }
    // // TODO the rest of the function, for now i will focus on the layout to
    // // be validated tomorrow
    //
    // // if (searchUsername != null)
    // // {
    // // searchByUsernameResult = new ArrayList<Person>();
    // // Set<Person> setOfPersonsToSearchIn;
    // // if (searchName != null)
    // // // setOfPersonsToSearchIn = new SetsearchByNameResult.;
    // // final String trimmedValue = searchUsername.S
    // // for (Person user : ) {
    // //
    // // }
    // //
    // // }
    //
    // return searchByNameResult;
    // }

    //
    // private boolean hasMatch(final String[] input, final String
    // unitNameParts) {
    // for (final String namePart : input) {
    // if (unitNameParts.indexOf(namePart) == -1) {
    // return false;
    // }
    // }
    // return true;
    // }

    /**
     * 
     * @param userEditor
     *            the user that is trying to edit the contacts of the
     *            personToEdit
     * @param personToEdit
     *            the person whose contacts we are checking to see if they can
     *            be edited by the userEditor
     * @return true if userEditor can edit the contacts, false otherwise a user
     *         can only edit the contacts if he is a super-editor or himself
     */
    public boolean isAllowedToEditContacts(User userEditor, Person personToEdit) {
        if (personToEdit.getUser().equals(userEditor)) {
            return true;
        }
        if (isSuperEditor(userEditor)) {
            return true;
        }
        return false;
    }

    public boolean isSuperEditor(User user) {
        return (Role.getRole(ContactsRoles.MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).isMember(user));
    }
}
