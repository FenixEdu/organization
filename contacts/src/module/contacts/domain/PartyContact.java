package module.contacts.domain;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixframework.DomainObject;
import pt.ist.fenixframework.plugins.luceneIndexing.IndexableField;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.IndexDocument;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.interfaces.Indexable;
import pt.ist.fenixframework.plugins.luceneIndexing.domain.interfaces.Searchable;
import dml.runtime.Relation;
import dml.runtime.RelationListener;
import module.organization.domain.Party;
import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.groups.Role;

public abstract class PartyContact extends PartyContact_Base implements Indexable, Searchable, IndexableField {
    
    public  PartyContact() {
        super();
	ContactsConfigurator.getInstance().addPartyContact(this);
	this.PersistentGroupPartyContact.addListener(new ValidVisibilityGroupsEnforcer());
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
	    if (!ContactsConfigurator.getInstance().hasVisibilityGroups(persistentGroup))
		throw new DomainException("error.adding.contact.invalid.visibility.group",
			DomainException.getResourceFor("resources/ContactsResources"));
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

    private DomainObject getPerson() {
	return this.getParty();
    }

    /**
     * Sets the contact value making sure that the user that called this method
     * has permissions to do it
     * 
     * @param value
     *            the value to set
     */
    @Service
    public void setContactValue(String value) {
	if (UserView.getCurrentUser() ==null || !isEditableBy(UserView.getCurrentUser()))
	{
	    if (UserView.getCurrentUser() == null)
		throw new DomainException("manage.contacts.edit.denied.nouser", "resources.ContactsResources");
	    else
		throw new DomainException("manage.contacts.edit.denied", "resources.ContactsResources", UserView.getCurrentUser()
			.getUsername());
	}
	setValue(value);
    }

    public boolean isEditableBy(User user) {
	if (isOwner(user) && !getType().equals(PartyContactType.IMMUTABLE))
	    // if he is the owner and the contact isn't immutable, then it can
	    // edit it
	    return true;
	if (Role.getRole(ContactsRoles.MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR).isMember(user))
	    return true;
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
     * the list of visibility groups of the ContactsConfigurator
     * {@link ContactsConfigurator}, otherwise it throws an exception
     * automaticly due to the listener
     * 
     * @param groups
     *            the groups to which this PartyContact will be visibile to
     */
    @Service
    public void setVisibleTo(List<PersistentGroup> groups) {

	//add all of the groups that are on the groups but not on the current list of visibility groups
	for (PersistentGroup persistentGroup : groups) {
	    if (!getVisibilityGroups().contains(persistentGroup))
 {
		addVisibilityGroups(persistentGroup);
	    }
	}
	List<PersistentGroup> currentSurplusGroups = getVisibilityGroups();
	if (currentSurplusGroups.removeAll(groups)) {
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
	    if (group.isMember(currentUser))
		return true;
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
	if (correspondingParty instanceof Person)
	{
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
	removeParty();
	removeContactsConfigurator();
	for (PersistentGroup group : getVisibilityGroups()) {
	    removeVisibilityGroups(group);
	}
	deleteDomainObject();
    }

    @Service
    public void deleteByUser(User currentUser) {
	if (!isEditableBy(currentUser))
	    throw new DomainException("manage.contacts.edit.denied", UserView.getCurrentUser().getUsername());
	delete();

    }

}
