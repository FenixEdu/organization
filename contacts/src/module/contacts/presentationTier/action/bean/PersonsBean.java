/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import pt.utl.ist.fenix.tools.util.CollectionPager;

import module.contacts.domain.PhoneType;
import module.organization.domain.Person;
import myorg.domain.User;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class PersonsBean implements Serializable {

	/**
	 * default version
	 */
	private static final long serialVersionUID = 1L;

	private String searchName;

	private String searchEmail;

	private String searchUsername;

	private String searchAddress;

	private String searchPhone;

	private PhoneType searchPhoneType;

	private String searchWebAddress;

	/**
	 * Field currently used to set the users that will have the supereditor role
	 */
	private ArrayList<User> users;

	private Person person;

	private ArrayList<Person> searchResult;

	private Integer pageNumber = new Integer(1);
	
	private Integer numberOfPages;

	private Boolean resultsByDetails;

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchEmail(String searchEmail) {
		this.searchEmail = searchEmail;
	}

	public String getSearchEmail() {
		return searchEmail;
	}

	public void setSearchUsername(String searchUsername) {
		this.searchUsername = searchUsername;
	}

	public String getSearchUsername() {
		return searchUsername;
	}

	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}

	public String getSearchAddress() {
		return searchAddress;
	}

	public void setSearchPhone(String searchPhone) {
		this.searchPhone = searchPhone;
	}

	public String getSearchPhone() {
		return searchPhone;
	}

	public void setSearchWebAddress(String searchWebAddress) {
		this.searchWebAddress = searchWebAddress;
	}

	public String getSearchWebAddress() {
		return searchWebAddress;
	}

	public void setSearchPhoneType(PhoneType searchPhoneType) {
		this.searchPhoneType = searchPhoneType;
	}

	public PhoneType getSearchPhoneType() {
		return searchPhoneType;
	}

	public void setResultsByDetails(Boolean resultsByDetails) {
		this.resultsByDetails = resultsByDetails;
	}

	public Boolean getResultsByDetails() {
		return resultsByDetails;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setSearchResult(Collection<Person> persons) {
		searchResult = new ArrayList(persons);
		
	}

}
