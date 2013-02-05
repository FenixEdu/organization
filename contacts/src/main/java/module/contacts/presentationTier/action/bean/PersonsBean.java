/*
 * @(#)PersonsBean.java
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
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;

import module.contacts.domain.PhoneType;
import module.organization.domain.Person;
import pt.ist.bennu.core.domain.User;

/**
 * 
 * @author João Antunes
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
	if (person != this.person)
	    setPageNumber(new Integer(1));
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
	if (searchName != this.searchName)
	    setPageNumber(new Integer(1));
	this.searchName = searchName;
    }

    public String getSearchName() {
	return searchName;
    }

    public void setSearchEmail(String searchEmail) {
	if (searchEmail != this.searchEmail)
	    setPageNumber(new Integer(1));
	this.searchEmail = searchEmail;
    }

    public String getSearchEmail() {
	return searchEmail;
    }

    public void setSearchUsername(String searchUsername) {
	if (searchUsername != this.searchUsername)
	    setPageNumber(new Integer(1));
	this.searchUsername = searchUsername;
    }

    public String getSearchUsername() {
	return searchUsername;
    }

    public void setSearchAddress(String searchAddress) {
	if (searchAddress != this.searchAddress)
	    setPageNumber(new Integer(1));
	this.searchAddress = searchAddress;
    }

    public String getSearchAddress() {
	return searchAddress;
    }

    public void setSearchPhone(String searchPhone) {
	if (searchPhone != this.searchPhone)
	    setPageNumber(new Integer(1));
	this.searchPhone = searchPhone;
    }

    public String getSearchPhone() {
	return searchPhone;
    }

    public void setSearchWebAddress(String searchWebAddress) {
	if (searchWebAddress != this.searchWebAddress)
	    setPageNumber(new Integer(1));
	this.searchWebAddress = searchWebAddress;
    }

    public String getSearchWebAddress() {
	return searchWebAddress;
    }

    public void setSearchPhoneType(PhoneType searchPhoneType) {
	if (searchPhoneType != null && searchPhoneType != this.searchPhoneType)
	    setPageNumber(new Integer(1));
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

    public void setSearchResult(ArrayList<Person> persons) {
	searchResult = new ArrayList(persons);

    }

    public ArrayList<Person> getSearchResult() {
	return searchResult;
    }

    public void setNumberOfPages(Integer numberOfPages) {
	this.numberOfPages = numberOfPages;
    }

    public Integer getNumberOfPages() {
	return numberOfPages;
    }

}
