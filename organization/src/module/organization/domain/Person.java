/*
 * @(#)Person.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module for the MyOrg web application.
 *
 *   The Organization Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
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

package module.organization.domain;

import module.organization.domain.PartyType.PartyTypeBean;
import myorg.domain.MyOrg;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.util.DomainReference;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Person extends Person_Base {

    static public class PersonBean extends PartyBean {
	private static final long serialVersionUID = -7516282978280402225L;

	private DomainReference<Person> person;
	private String name;

	public PersonBean() {
	    setBegin(new LocalDate());
	}

	public PersonBean(final Person person) {
	    this();
	    
	    setPerson(person);
	    setName(person.getName());
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public Person getPerson() {
	    return (this.person != null) ? this.person.getObject() : null;
	}

	public void setPerson(Person person) {
	    this.person = (person != null) ? new DomainReference<Person>(person) : null;
	}

	@Override
	public Party getParty() {
	    return getPerson();
	}
	
	public void edit() {
	    getPerson().edit(this);
	}

	@Override
	public void addParent() {
	    getPerson().addParent(getParent(), getAccountabilityType(), getBegin(), getEnd());
	}

    }

    public Person(MultiLanguageString partyName, PartyType partyType) {
	super();
	setMyOrgFromPerson(MyOrg.getInstance());
	setPartyName(partyName);
	addPartyTypes(partyType);
    }

    @Override
    final public boolean isPerson() {
	return true;
    }

    @Override
    protected void disconnect() {
	removeMyOrgFromPerson();
	super.disconnect();
    }

    @Service
    public void edit(final PersonBean bean) {
	final MultiLanguageString name = getPartyName();
	name.setContent(bean.getName());
	setPartyName(name);
    }

    public String getName() {
	return getPartyName().getContent();
    }

    @Service
    static public Person create(final MultiLanguageString partyName, final PartyType partyType) {
	return new Person(partyName, partyType);
    }

    @Service
    static public Person create(final PersonBean bean) {
	return create(new MultiLanguageString(bean.getName()), getPartyTypeInstance());
    }

    static public Person readByPartyName(MultiLanguageString partyName) {
	for (final Party party : MyOrg.getInstance().getParties()) {
	    if (!party.isUnit()) {
		if (party.getPartyName().equals(partyName)) {
		    return (Person) party;
		}
	    }
	}
	return null;
    }

    static public PartyType getPartyTypeInstance() {
	final String type = Person.class.getName();
	PartyType partyType = PartyType.readBy(type);
	if (partyType == null) {
	    synchronized (Person.class) {
		partyType = PartyType.readBy(Person.class.getName());
		if (partyType == null) {
		    final PartyTypeBean partyTypeBean = new PartyTypeBean();
		    partyTypeBean.setType(type);
		    partyTypeBean.setName(new MultiLanguageString("Pessoa"));
		    partyType = PartyType.create(partyTypeBean);
		}
	    }
	}
	return partyType;
    }

}
