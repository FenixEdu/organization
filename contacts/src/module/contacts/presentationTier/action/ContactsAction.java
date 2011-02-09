/**
 * 
 */
package module.contacts.presentationTier.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.EmailAddress;
import module.contacts.domain.GroupAlias;
import module.contacts.domain.PartyContact;
import module.contacts.domain.PartyContactType;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.PhysicalAddress;
import module.contacts.domain.WebAddress;
import module.contacts.presentationTier.action.bean.AddressBeanFactory;
import module.contacts.presentationTier.action.bean.ContactBean;
import module.contacts.presentationTier.action.bean.ContactToCreateBean;
import module.contacts.presentationTier.action.bean.ContactToEditBean;
import module.contacts.presentationTier.action.bean.GroupAliasBean;
import module.contacts.presentationTier.action.bean.GroupsSelectorBean;
import module.contacts.presentationTier.action.bean.PersonsBean;
import module.contacts.presentationTier.action.bean.PhysicalAddressBean;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.CountrySubdivisionLevelName;
import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.groups.Role;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.utl.ist.fenix.tools.util.CollectionPager;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
@Mapping(path = "/contacts")
public class ContactsAction extends ContextBaseAction {

    private static final int FIRST_LEVEL = 1;

    public final ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Person person = UserView.getCurrentUser().getPerson();

	ContactBean emailBean = new ContactBean();

	request.setAttribute("emailBean", emailBean);

	request.setAttribute("person", person);
	return forward(request, "/contacts/frontPage.jsp");
    }

    public final ActionForward createCustomEmail(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactBean emailBean = getRenderedObject("xpto");
	Person person = UserView.getCurrentUser().getPerson();

	EmailAddress.createNewEmailAddress(emailBean.getValue(), person, false, PartyContactType.PERSONAL);

	return frontPage(mapping, form, request, response);

    }

    public final ActionForward modifyContacts(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	// making it a getDomainObject because we can use the same method to
	// edit the other user's contacts
	Person person = getDomainObject(request, "personOid");
	// for now let's create the multiple contacts with default values
	// TODO make it interpret a form to edit/create the new contact info

	List<PersistentGroup> existingVisbilityGroups = ContactsConfigurator.getInstance().getVisibilityGroups();
	/*
	 * (EmailAddress.createNewEmailAddress("johnDoe@VisibleToAll.com",
	 * person, true, PartyContactType.PERSONAL))
	 * .setVisibleTo(existingVisbilityGroups);
	 * EmailAddress.createNewEmailAddress("johnDoePublic@ist.pt", person,
	 * true, PartyContactType.WORK).setVisibleTo( existingVisbilityGroups);
	 * EmailAddress.createNewEmailAddress("johnDoePublic@ist.pt", person,
	 * false, PartyContactType.IMMUTABLE).setVisibleTo(
	 * existingVisbilityGroups);
	 * 
	 * Phone.createNewPhone(PhoneType.CELLPHONE, "961231234", person, true,
	 * PartyContactType.PERSONAL).setVisibleTo( existingVisbilityGroups);
	 * Phone.createNewPhone(PhoneType.EXTENSION, "1234", person, true,
	 * PartyContactType.IMMUTABLE).setVisibleTo( existingVisbilityGroups);
	 * Phone.createNewPhone(PhoneType.REGULAR_PHONE, "212345678", person,
	 * false, PartyContactType.WORK).setVisibleTo( existingVisbilityGroups);
	 */
	// physical address TODO improve it

	PhysicalAddress.createNewPhysicalAddress(Country.getPortugal(), "none", person, true, PartyContactType.PERSONAL)
		.setVisibleTo(existingVisbilityGroups);

	/*
	 * WebAddress.createNewWebAddress("http://johndoerulez.com", person,
	 * true, PartyContactType.PERSONAL).setVisibleTo(
	 * existingVisbilityGroups);
	 * 
	 * WebAddress.createNewWebAddress(
	 * "http://deec.ist.utl.pt/~jdoesProjectsAndCourses", person, false,
	 * PartyContactType.WORK) .setVisibleTo(existingVisbilityGroups);
	 * 
	 * WebAddress.createNewWebAddress("http://web.ist.utl.pt/~jdoe", person,
	 * true, PartyContactType.IMMUTABLE).setVisibleTo(
	 * existingVisbilityGroups);
	 */
	return frontPage(mapping, form, request, response);
    }

    public final ActionForward configContactsModule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	request.setAttribute("contactsConfiguration", ContactsConfigurator.getInstance());

	GroupsSelectorBean groupsSelectorBean = new GroupsSelectorBean();

	groupsSelectorBean.setGroups(ContactsConfigurator.getInstance().getVisibilityGroups());

	request.setAttribute("groupsSelectorBean", groupsSelectorBean);

	GroupsSelectorBean roleSelectorGroupsBean = new GroupsSelectorBean();

	request.setAttribute("roleSelectorGroupsBean", roleSelectorGroupsBean);

	PersonsBean personsBean = new PersonsBean();

	request.setAttribute("personsBean", personsBean);

	// get the persons who have the supereditor role
	PersonsBean usersWithSuperEditorRoleBean = new PersonsBean();
	usersWithSuperEditorRoleBean.setUsers(new ArrayList<User>(((PersistentGroup) Role.getRole(ContactsConfigurator
		.getInstance().getContactsRoles().MODULE_CONTACTS_DOMAIN_CONTACTSEDITOR)).getMembers()));

	request.setAttribute("usersWithSuperEditorRoleBean", usersWithSuperEditorRoleBean);

	return forward(request, "/contacts/configure/configureModule.jsp");
    }

    public final ActionForward editAlias(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	final PersistentGroup persistentGroup = AbstractDomainObject.fromExternalId(request.getParameter("groupId"));

	GroupAliasBean groupAliasBean = new GroupAliasBean();
	groupAliasBean.setGroupToEdit(persistentGroup);
	groupAliasBean.setAlias(persistentGroup.getGroupAlias().getGroupAlias());
	request.setAttribute("groupAliasBean", groupAliasBean);

	return forward(request, "/contacts/configure/setPersistentGroupAlias.jsp");

    }

    public final ActionForward modifyGroupAlias(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	GroupAliasBean groupAliasBean = getRenderedObject("myorg.modules.contacts.edit.setGroupAliasBean");
	GroupAlias newAlias = GroupAlias.create(groupAliasBean.getGroupToEdit(), groupAliasBean.getAlias());

	return configContactsModule(mapping, form, request, response);

    }

    public final ActionForward setVisibilityGroups(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	GroupsSelectorBean groupsSelectorBean = getRenderedObject("groupsSelectorBean");
	ContactsConfigurator.getInstance().setVisibilityGroups(groupsSelectorBean.getGroups());
	Boolean changesSubmitted = new Boolean(true);
	request.setAttribute("changesSubmitted", changesSubmitted);

	return configContactsModule(mapping, form, request, response);
    }

    public final ActionForward assignSuperEditorRoleToGroup(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	GroupsSelectorBean groupsSelectorBean = getRenderedObject("roleSelectorGroupsBean");

	for (PersistentGroup group : groupsSelectorBean.getGroups()) {
	    ContactsConfigurator.getInstance().assignSuperEditorToPersonsCurrentlyIn(group);
	}

	return configContactsModule(mapping, form, request, response);
    }

    public final ActionForward assignSuperEditorRoleToPerson(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	PersonsBean personsBean = getRenderedObject("personsBean");

	ContactsConfigurator.getInstance().assignSuperEditorRole(personsBean.getPerson().getUser());

	return configContactsModule(mapping, form, request, response);
    }

    public final ActionForward removeUserFromSuperEditorRole(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final User user = AbstractDomainObject.fromExternalId(request.getParameter("userId"));

	ContactsConfigurator.getInstance().removeSuperEditorRole(user);

	return configContactsModule(mapping, form, request, response);

    }

    public final ActionForward searchContacts(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final PersistentGroup persistentGroup = AbstractDomainObject.fromExternalId(request.getParameter("groupId"));

	PersonsBean personSearchBean = new PersonsBean();
	request.setAttribute("personSearchBean", personSearchBean);

	return forward(request, "/contacts/searchContacts.jsp");

    }

    /**
     * This method searches for a person, and if for that search it used the
     * parameters on the request, it fills the Bean with the used fields (to
     * repopulate the form) either by using the fields on the bean or the
     * parameters that have been passed to the actions that invoke this method
     * through the request
     * 
     * @param request
     *            the request that was passed to the action and that may contain
     *            the parameters needed for the search
     * @param personSearchBean
     *            the bean that contains the info needed by this method to
     *            search for the person, or if no info is present there, the
     * @param useBean
     *            if true, the bean is used, otherwise the request is
     * @return the CollectionPager {@link CollectionPager} with the results of
     *         the search
     */
    private CollectionPager<Person> searchPerson(PersonsBean personSearchBean, HttpServletRequest request, boolean useBean) {
	PhoneType searchPhoneType;
	Integer pageNumber;
	if (useBean) {
	    String searchName = personSearchBean.getSearchName();
	    String searchEmail = personSearchBean.getSearchEmail();
	    String searchUsername = personSearchBean.getSearchUsername();
	    String searchAddress = personSearchBean.getSearchAddress();
	    String searchPhone = personSearchBean.getSearchPhone();
	    searchPhoneType = personSearchBean.getSearchPhoneType();
	    String searchWebAddress = personSearchBean.getSearchWebAddress();

	    // we are assuming that we should display the first page
	    pageNumber = new Integer(1);

	} else {
	    String searchName = request.getParameter("searchName");
	    String searchEmail = request.getParameter("searchEmail");
	    String searchUsername = request.getParameter("searchUsername");
	    String searchAddress = request.getParameter("searchAddress");
	    String searchPhone = request.getParameter("searchPhone");
	    String searchPhoneTypeString = request.getParameter("searchPhoneType");
	    searchPhoneType = (searchPhoneTypeString != null) ? PhoneType.valueOf(searchPhoneTypeString) : null;
	    String searchWebAddress = request.getParameter("searchWebAddress");

	    Boolean resultsByDetails = (request.getParameter("resultsByDetails") != null) ? Boolean.getBoolean(request
		    .getParameter("resultsByDetails")) : null;

	    pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

	    // let's also repopulate the fields in the form
	    personSearchBean.setSearchName(searchName);
	    personSearchBean.setSearchEmail(searchEmail);
	    personSearchBean.setSearchUsername(searchUsername);
	    personSearchBean.setSearchAddress(searchAddress);
	    personSearchBean.setSearchPhone(searchPhone);
	    personSearchBean.setSearchPhoneType(searchPhoneType);
	    personSearchBean.setSearchWebAddress(searchWebAddress);
	    personSearchBean.setResultsByDetails(resultsByDetails);
	    personSearchBean.setPageNumber(pageNumber);
	}

	// let's use the parameters to search for the person(s)
	ArrayList<Person> results = new ArrayList(ContactsConfigurator.getInstance().getPersonsByDetails(
		UserView.getCurrentUser(), personSearchBean.getSearchName(), personSearchBean.getSearchUsername(),
		personSearchBean.getSearchPhone(), personSearchBean.getSearchPhoneType(), personSearchBean.getSearchAddress(),
		personSearchBean.getSearchWebAddress(), personSearchBean.getSearchEmail()));


	return new CollectionPager<Person>(results, ContactsConfigurator.SEARCH_MAXELEMENTS_PER_PAGE);

    }

    public final ActionForward searchPersonsByDetails(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	PersonsBean personSearchBean = getRenderedObject("personSearchByDetailsBean");

	String typeOfSearch = request.getParameter("typeOfSearch");

	Collection<Person> searchResults;
	CollectionPager<Person> searchResultsPager;
	if (personSearchBean == null) {
	    personSearchBean = new PersonsBean();
	    searchResultsPager = searchPerson(personSearchBean, request, false);
	} else {
	    searchResultsPager = searchPerson(personSearchBean, request, true);
	}

	if (typeOfSearch != null && typeOfSearch.equals("byDetails"))
	    personSearchBean.setResultsByDetails(Boolean.TRUE);
	else if (typeOfSearch != null && typeOfSearch.equals("basic"))
	    personSearchBean.setResultsByDetails(Boolean.FALSE);

	searchResults = searchResultsPager.getPage(personSearchBean.getPageNumber());
	personSearchBean.setNumberOfPages(new Integer(searchResultsPager.getNumberOfPages()));
	personSearchBean.setSearchResult(new ArrayList<Person>(searchResults));

	request.setAttribute("personSearchBean", personSearchBean);

	return forward(request, "/contacts/searchContacts.jsp");

    }

    public final ActionForward searchPersonsByName(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	PersonsBean personSearchBean = getRenderedObject("personSearchBean");
	personSearchBean.setResultsByDetails(false);
	CollectionPager<Person> results = null;
	boolean useBean = true;
	if (personSearchBean == null) {
	    personSearchBean = new PersonsBean();
	    useBean = false;
	}

	if (personSearchBean.getPerson() == null) {
	    results = searchPerson(personSearchBean, request, useBean);
	} else {
	    results = new CollectionPager<Person>(Collections.singleton(personSearchBean.getPerson()),
		    ContactsConfigurator.SEARCH_MAXELEMENTS_PER_PAGE);
	    personSearchBean.setPerson(null);
	}
	personSearchBean.setNumberOfPages(new Integer(results.getNumberOfPages()));
	personSearchBean.setSearchResult(new ArrayList(results.getPage(personSearchBean.getPageNumber())));
	RenderUtils.invalidateViewState();
	request.setAttribute("personSearchBean", personSearchBean);

	return forward(request, "/contacts/searchContacts.jsp");

    }

    // Edit contact info

    public final ActionForward editContacts(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	final PersistentGroup persistentGroup = AbstractDomainObject.fromExternalId(request.getParameter("groupId"));

	Person person = null;
	// let's assert if we are editing our own info or not, depending if the
	// personEId parameter is set or not
	if (request.getParameter("personEId") != null) {
	    // let's assert that the person is either the owner or the super
	    // editor - if it can edit the contacts
	    person = AbstractDomainObject.fromExternalId(request.getParameter("personEId"));
	    if (person == null)
		throw new DomainException("manage.contacts.edit.error.noPerson", "resources.ContactsResources");
	    if (!ContactsConfigurator.getInstance().isAllowedToEditContacts(UserView.getCurrentUser(), person))
		throw new DomainException("manage.contacts.edit.denied.forPerson", "resources.ContactsResources",
			person.getName());

	} else {
	    person = UserView.getCurrentUser().getPerson();
	}

	request.setAttribute("person", person);
	// retrieve the contacts sorted by class (using the alphabetical order
	// of the class name)!

	if (person != null) {
	    ComparatorChain chain = new ComparatorChain();
	    chain.addComparator(new BeanComparator("class.simpleName"));
	    chain.addComparator(new BeanComparator("externalId"));

	    TreeSet<PartyContact> sortedContacts = new TreeSet<PartyContact>(chain);
	    sortedContacts.addAll(person.getPartyContacts());

	    request.setAttribute("sortedContacts", sortedContacts);

	    // get all of the visibility groups
	    ArrayList<PersistentGroup> visibilityGroups = new ArrayList<PersistentGroup>(ContactsConfigurator.getInstance()
		    .getVisibilityGroups());

	    request.setAttribute("visibilityGroups", visibilityGroups);

	}

	return forward(request, "/contacts/editContact/editContactInfo.jsp");

    }

    public final ActionForward deletePartyContact(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	PartyContact contactToDelete = getDomainObject(request, "partyContactOid");
	// check if the contact can be edited or not by the user
	if (!contactToDelete.isEditableBy(UserView.getCurrentUser()))
	    throw new DomainException("manage.contacts.edit.denied", UserView.getCurrentUser().getUsername());

	// let's set the personEId so that it is caught by the editContacts and
	// we continue to edit the contacts of who we have been editing
	request.setAttribute("personEId", contactToDelete.getOwner().getExternalId());

	contactToDelete.deleteByUser(UserView.getCurrentUser());

	return editContacts(mapping, form, request, response);

    }

    public final ActionForward choosePhoneRegexpPostBack(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactToEditBean contactToEditBean = getRenderedObject();
	RenderUtils.invalidateViewState();

	request.setAttribute("contactToEditBean", contactToEditBean);

	return editPartyContact(mapping, form, request, response);
	// return frontPage(mapping, form, request, response);
    }

    public final ActionForward editPartyContact(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	// TODO ?? ask extra security validation here?!?!?!?!?!
	PartyContact contactToEdit = getDomainObject(request, "partyContactOid");

	// if we don't have the contactToEdit, let's check if the bean is
	// already there if it is it's because it is a postback if not it isn't
	ContactToEditBean contactToEditBean = getAttribute(request, "contactToEditBean");
	if (contactToEditBean == null) {
	    contactToEditBean = new ContactToEditBean(contactToEdit);
	    request.setAttribute("contactToEditBean", contactToEditBean);
	} else
	    contactToEdit = contactToEditBean.getWrappedContact();

	// create the group selector bean
	GroupsSelectorBean visibilityGroupsConcededBean = new GroupsSelectorBean();

	visibilityGroupsConcededBean.setGroups(contactToEdit.getVisibilityGroups());
	request.setAttribute("visibilityGroupsConcededBean", visibilityGroupsConcededBean);
	// get all of the visibility groups
	// ArrayList<PersistentGroup> visibilityGroups = new
	// ArrayList<PersistentGroup>(ContactsConfigurator.getInstance()
	// .getVisibilityGroups());

	// request.setAttribute("visibilityGroups", visibilityGroups);

	// let's assert what kind of contact it is and send it to the correct
	// jsp

	if (contactToEdit instanceof Phone) {
	    Phone phoneToEdit = (Phone) contactToEdit;
	    if (phoneToEdit.getPhoneType().equals(PhoneType.VOIP_SIP))
		return forward(request, "/contacts/editContact/editVoipPhoneContact.jsp");
	    else if (phoneToEdit.getPhoneType().equals(PhoneType.EXTENSION))
		return forward(request, "/contacts/editContact/editExtensionPhoneContact.jsp");
	    else
		return forward(request, "/contacts/editContact/editRegularPhoneContact.jsp");

	} else if (contactToEdit instanceof EmailAddress) {
	    return forward(request, "/contacts/editContact/editOneFieldContact.jsp");
	} else if (contactToEdit instanceof PhysicalAddress) {
	    return forward(request, "/contacts/editContact/editPhysicalAddressContact.jsp");
	} else if (contactToEdit instanceof WebAddress) {
	    return forward(request, "/contacts/editContact/editOneFieldContact.jsp");
	}

	return forward(request, "/contacts/editContact/editContact.jsp");

    }

    public final ActionForward applyPartyContactEdit(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactToEditBean contactToEditBean = getRenderedObject();

	PartyContact contactToEdit = contactToEditBean.getWrappedContact();
	contactToEdit.setContactValue(contactToEditBean.getValue());
	if (contactToEdit instanceof Phone)
	    ((Phone) contactToEdit).changePhoneType(contactToEditBean.getPhoneType());

	// change the visibility

	contactToEdit.setVisibleTo(contactToEditBean.getVisibilityGroups());

	// get the person and set it so that the editContacts catches it

	Person owner = contactToEdit.getOwner();

	request.setAttribute("personEId", owner.getExternalId());

	// return frontPage(mapping, form, request, response);
	return editContacts(mapping, form, request, response);
    }

    public final ActionForward createPartyContact(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactToCreateBean contactToCreateBean = new ContactToCreateBean();
	request.setAttribute("contactToCreateBean", contactToCreateBean);

	return forward(request, "/contacts/createContact.jsp");
    }

    public final ActionForward chooseKindOfContactPostBack(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactToCreateBean contactToCreateBean = getRenderedObject();

	RenderUtils.invalidateViewState();

	request.setAttribute("contactToCreateBean", contactToCreateBean);

	return forward(request, "/contacts/createContact.jsp");
	// return frontPage(mapping, form, request, response);
    }

    @SuppressWarnings("unchecked")
    public final ActionForward chooseGeographicLevelPostBack(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {

	ContactToCreateBean contactToCreateBean = getRenderedObject();

	RenderUtils.invalidateViewState();

	if (contactToCreateBean.getPhysicalAddressBean().getCountry() != null) {
	    Country country = contactToCreateBean.getPhysicalAddressBean().getCountry();
	    HashMap<String, CountrySubdivision> geographicLevels = contactToCreateBean.getPhysicalAddressBean()
		    .getGeographicLevels();
	    boolean createGeographicLocations = hasSubDivisions(country);

	    if (geographicLevels == null && createGeographicLocations)
		geographicLevels = new HashMap<String, CountrySubdivision>();

	    // go and check if we have any subdivisions that aren't labels
	    if (!createGeographicLocations) {

		if (geographicLevels != null)
		    geographicLevels.clear();
		// add the AddressBean with the labels:
		contactToCreateBean.getPhysicalAddressBean().setAddressBean(AddressBeanFactory.createAddressBean(country));

	    } else {
		CountrySubdivisionLevelName toAdd = null;
		if (geographicLevels.size() == 0) { // if we are on the first
		    // entry, let's populate it
		    // with the first level
		    geographicLevels.put(country.getSubdivisionLevelName(new Integer(FIRST_LEVEL)).getContent(), null);
		    if (country.getSubdivisionDepth() > 1
			    && contactToCreateBean.getPhysicalAddressBean().getAddressBean() != null)
			contactToCreateBean.getPhysicalAddressBean().setAddressBean(null);

		} else {
		    // get the last CountrySubdivisionLevelName and add the next
		    // level if it isn't a label
		    ArrayList<CountrySubdivision> geographicLevelsOrdered = PhysicalAddressBean
			    .getSubdivisionsOrderedArrayList(geographicLevels.values());

		    int nextLevel = geographicLevelsOrdered.get(geographicLevelsOrdered.size() - 1).getLevel().intValue() + 1;

		    CountrySubdivisionLevelName nextSubdivision = country.getCountrySubdivisionLevel(nextLevel);
		    if (nextSubdivision != null) {
			contactToCreateBean.getPhysicalAddressBean().setAddressBean(null);
			geographicLevels.put(nextSubdivision.getName().getContent(), null);
		    } else
			contactToCreateBean.getPhysicalAddressBean()
				.setAddressBean(AddressBeanFactory.createAddressBean(country));

		}
	    }
	    // TODO take care of the labels that might exist

	    contactToCreateBean.getPhysicalAddressBean().setGeographicLevels(geographicLevels);
	}

	request.setAttribute("contactToCreateBean", contactToCreateBean);

	return forward(request, "/contacts/createContact.jsp");
	// return frontPage(mapping, form, request, response);
    }

    /**
     * @param country
     *            the country to check on
     * @return true if country has actual {@link CountrySubdivisionLevelName}
     *         which aren't labels
     */
    private boolean hasSubDivisions(Country country) {
	return (country.getSubdivisionDepth() > 0);
    }

}
