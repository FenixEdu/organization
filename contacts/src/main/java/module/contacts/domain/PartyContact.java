/*
 * @(#)PartyContact.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import module.organization.domain.Party;
import module.organization.domain.Person;

import org.apache.commons.lang.StringUtils;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.domain.groups.Role;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.dml.runtime.Relation;
import pt.ist.fenixframework.dml.runtime.RelationListener;
import pt.ist.fenixframework.plugins.luceneIndexing.IndexableField;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.IndexDocument;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.interfaces.Indexable;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.interfaces.Searchable;

/**
 * 
 * @author Pedro Santos
 * @author João Antunes
 * @author Pedro Amaral
 * 
 */
public abstract class PartyContact extends PartyContact_Base implements Indexable, Searchable, IndexableField {

    public PartyContact() {
        super();
        ContactsConfigurator.getInstance().addPartyContact(this);
        getRelationPersistentGroupPartyContact().addListener(new ValidVisibilityGroupsEnforcer());
    }

    static protected void validateUser(User userCreatingTheContact, Party partyThatWillOwnTheContact, PartyContactType type) {
        if (!type.equals(PartyContactType.IMMUTABLE)) {
            return;
        }

        // TODO: Tornar esta validacao parametrizavel

        // if (isOwner(userCreatingTheContact, partyThatWillOwnTheContact) &&
        // !type.equals(PartyContactType.IMMUTABLE))
        // // if he is the owner and the contact isn't immutable, then it can
        // // edit it
        // return;
        // if
        // (Role.getRole(ContactsRoles.MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).isMember(userCreatingTheContact))
        // return;
        //
        // // Enterprises do not have users and NPE members can edit the
        // contacts!
        // if (JobBankSystem.getInstance().isNPEMember(userCreatingTheContact)
        // && Enterprise.isEnterprise(partyThatWillOwnTheContact))
        // return;

        throw new DomainException("manage.contacts.edit.denied.nouser");

    }

    static protected void validateVisibilityGroups(Collection<PersistentGroup> visibilityGroups) {
        if (!ContactsConfigurator.getInstance().getVisibilityGroups().containsAll(visibilityGroups)) {
            throw new DomainException("manage.contacts.wrong.visibility.groups.defined");
        }

    }

    /**
     * Class that makes sure that when you add a visibility group to a contact,
     * that it exists on the list of visibility groups defined in the
     * ContactsConfigurator {@link ContactsConfigurator};
     * 
     * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
     * 
     */
    final static class ValidVisibilityGroupsEnforcer implements RelationListener<PartyContact, PersistentGroup> {

        @Override
        public void beforeAdd(Relation<PartyContact, PersistentGroup> relation, PartyContact partyContact,
                PersistentGroup persistentGroup) {
            if (!ContactsConfigurator.getInstance().getVisibilityGroups().contains(persistentGroup)) {
                throw new DomainException("error.adding.contact.invalid.visibility.group",
                        DomainException.getResourceFor("resources/ContactsResources"));
            }
        }

        @Override
        public void afterAdd(Relation<PartyContact, PersistentGroup> rel, PartyContact o1, PersistentGroup o2) {
            // nothing needs to be done after as we don't have to worry about
            // concurrency issues
        }

        @Override
        public void beforeRemove(Relation<PartyContact, PersistentGroup> rel, PartyContact o1, PersistentGroup o2) {
        }

        @Override
        public void afterRemove(Relation<PartyContact, PersistentGroup> rel, PartyContact o1, PersistentGroup o2) {
        }

    }

    @Override
    public String getFieldName() {
        return this.getClass().getName();
    }

    @Override
    public Set<Indexable> getObjectsToIndex() {
        return Collections.singleton((Indexable) this);
    }

    @Override
    public IndexDocument getDocumentToIndex() {
        IndexDocument document = new IndexDocument(this);
        document.indexField(this, getValue());
        return document;
    }

    @Override
    public IndexMode getIndexMode() {
        return IndexMode.MANUAL;
    }

    public Person getPerson() {
        if (this.getParty() instanceof Person) {
            return (Person) this.getParty();
        } else {
            return null;
        }
    }

    /**
     * Sets the contact value making sure that the user that called this method
     * has permissions to do it
     * 
     * @param value
     *            the value to set
     */
    @Atomic
    public void setContactValue(String value) {
        // if (UserView.getCurrentUser() != null /*&&
        // !isEditableBy(UserView.getCurrentUser())*/) {
        // throw new DomainException("manage.contacts.edit.denied",
        // "resources.ContactsResources", UserView.getCurrentUser()
        // .getUsername());
        // }
        setValue(value);
    }

    /**
     * 
     * Changes the contact value without looking if the user has permissions or
     * not
     * 
     * @param value
     *            the value to set
     */
    @Atomic
    public void forceChangeContactValue(String value) {
        setValue(value);
    }

    public boolean isEditableBy(User user) {
        if (isOwner(user) && !getType().equals(PartyContactType.IMMUTABLE)) {
            // if he is the owner and the contact isn't immutable, then it can
            // edit it
            return true;
        }
        if (Role.getRole(ContactsRoles.MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).isMember(user)) {
            return true;
        }
        return false;
    }

    /**
     * @param value
     *            the value to set on the contact.
     */
    protected abstract void setValue(String value);

    public String getValue() {
        return getDescription();
    }

    /**
     * 
     * @return the String representation of the contact
     */
    public abstract String getDescription();

    /**
     * Sets the contact visible to the given groups if they are all contained in
     * the list of visibility groups of the ContactsConfigurator {@link ContactsConfigurator}, otherwise it throws an exception
     * automaticly due to the listener
     * 
     * @param groups
     *            the groups to which this PartyContact will be visibile to
     */
    @Atomic
    public void setVisibleTo(Collection<PersistentGroup> groups) {
        // add all of the groups that are on the groups but not on the current
        // list of visibility groups
        if (groups != null) {
            for (PersistentGroup persistentGroup : groups) {
                if (!getVisibilityGroups().contains(persistentGroup)) {
                    addVisibilityGroups(persistentGroup);
                }
            }
        }
        List<PersistentGroup> currentSurplusGroups = new ArrayList<PersistentGroup>(getVisibilityGroups());
        if (groups != null) {
            currentSurplusGroups.removeAll(groups);
        }
        if (!currentSurplusGroups.isEmpty()) {
            for (PersistentGroup persistentGroup : currentSurplusGroups) {
                removeVisibilityGroups(persistentGroup);
            }
        }

    }

    public boolean isVisibleTo(User currentUser) {
        if (isOwner(currentUser)) {
            return true;
        }
        for (PersistentGroup group : getVisibilityGroups()) {
            if (group.isMember(currentUser)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleTo(PersistentGroup group) {
        return (getVisibilityGroups().contains(group));
    }

    // FIXME (?) dependency of the structure of the Organization!!
    // DEPENDENCY
    /**
     * 
     * @param currentUser
     *            the User to assert if it is the owner of this partycontact or
     *            not
     * @return true if the currentUser is the owner of this PartyContact, false
     *         otherwise
     */
    private boolean isOwner(User currentUser) {
        Party correspondingParty = getParty();
        if (correspondingParty instanceof Person) {
            return (((Person) correspondingParty).getUser().equals(currentUser));
        }
        return false;
    }

    /**
     * 
     * @param currentUser
     *            the User to assert if it is the owner of this partycontact or
     *            not
     * @param partyFutureContactOwner
     *            the {@link Party} that will have the contact
     * @return true if the currentUser is the owner of this PartyContact, false
     *         otherwise
     */
    static private boolean isOwner(User currentUser, Party partyFutureContactOwner) {
        Party correspondingParty = partyFutureContactOwner;
        if (correspondingParty instanceof Person) {
            return (((Person) correspondingParty).getUser().equals(currentUser));
        }
        return false;
    }

    /**
     * 
     * @return the Person that owns this PartyContact or null if it doesn't
     *         exist
     */
    public Person getOwner() {
        Party correspondingParty = getParty();
        if (correspondingParty instanceof Person) {
            return (Person) correspondingParty;
        }
        return null;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public void delete() {
        setParty(null);
        setContactsConfigurator(null);
        for (PersistentGroup group : getVisibilityGroups()) {
            removeVisibilityGroups(group);
        }
        deleteDomainObject();
    }

    @Override
    public void setDefaultContact(Boolean defaultContact) {
        if (defaultContact != null && defaultContact.booleanValue()) {
            // remove the other default contacts of this type so that there is
            // only one for each type
            for (PartyContact partyContact : getParty().getPartyContactsSet()) {
                if (partyContact.getClass().isInstance(this.getClass()) && partyContact.getDefaultContact().booleanValue()) {
                    partyContact.setDefaultContact(Boolean.FALSE);
                }
            }
        }
        super.setDefaultContact(defaultContact);
    }

    @Atomic
    public void deleteByUser(User currentUser) {
        // Confirmations not done here anymore

        // if (!isEditableBy(currentUser))
        // throw new DomainException("manage.contacts.edit.denied",
        // UserView.getCurrentUser().getUsername());
        delete();
    }

    public static EmailAddress getEmailAddressForSendingEmails(Party party) {
        for (PartyContact contact : party.getPartyContactsSet()) {
            if (contact instanceof EmailAddress) {
                EmailAddress email = (EmailAddress) contact;
                if (email.getType().equals(PartyContactType.IMMUTABLE)) {
                    return email;
                }
            }
        }
        return null;
    }

    public static String getEmailForSendingEmails(Party party) {
        EmailAddress email = getEmailAddressForSendingEmails(party);
        return email != null ? email.getValue() : StringUtils.EMPTY;
    }

    @Deprecated
    public java.util.Set<pt.ist.bennu.core.domain.groups.PersistentGroup> getVisibilityGroups() {
        return getVisibilityGroupsSet();
    }

}
