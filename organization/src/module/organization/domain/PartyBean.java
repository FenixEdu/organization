/*
 * @(#)PartyBean.java
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

import java.io.Serializable;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.util.DomainReference;

abstract public class PartyBean implements Serializable {

    private static final long serialVersionUID = -4325706118973127412L;

    private DomainReference<Party> parent;
    private DomainReference<AccountabilityType> accountabilityType;
    private LocalDate begin;
    private LocalDate end;

    public Party getParent() {
	return (this.parent != null) ? this.parent.getObject() : null;
    }

    public void setParent(Party parent) {
	this.parent = (parent != null) ? new DomainReference<Party>(parent) : null;
    }

    public boolean hasParent() {
	return getParent() != null;
    }

    public AccountabilityType getAccountabilityType() {
	return (this.accountabilityType != null) ? this.accountabilityType.getObject() : null;
    }

    public void setAccountabilityType(AccountabilityType accountabilityType) {
	this.accountabilityType = (accountabilityType != null) ? new DomainReference<AccountabilityType>(accountabilityType)
		: null;
    }

    public LocalDate getBegin() {
	return begin;
    }

    public void setBegin(LocalDate begin) {
	this.begin = begin;
    }

    public LocalDate getEnd() {
	return end;
    }

    public void setEnd(LocalDate end) {
	this.end = end;
    }

    abstract public Party getParty();
    abstract public void addParent();
}
