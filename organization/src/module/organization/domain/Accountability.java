/*
 * @(#)Accountability.java
 *
 * Copyright 2009 Instituto Superior Tecnico, João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
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

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixframework.pstm.Transaction;

public class Accountability extends Accountability_Base {

    protected Accountability() {
	super();
	setMyOrg(MyOrg.getInstance());
	setBeginDate(new LocalDate());
    }

    protected Accountability(final Party parent, final Party child, final AccountabilityType type, final LocalDate begin,
	    final LocalDate end) {
	this();
	check(parent, "error.Accountability.invalid.parent");
	check(child, "error.Accountability.invalid.child");
	check(type, "error.Accountability.invalid.type");
	check(begin, "error.Accountability.invalid.begin");
	checkDates(begin, end);

	canCreate(parent, child, type);
	
	setParent(parent);
	setChild(child);
	setAccountabilityType(type);
	setBeginDate(begin);
	setEndDate(end);
    }

    private void checkDates(LocalDate begin, LocalDate end) {
	if (begin != null && end != null && begin.isAfter(end)) {
	    throw new DomainException("error.Accountability.begin.is.after.end");
	}
    }

    private void check(final Object obj, final String message) {
	if (obj == null) {
	    throw new DomainException(message);
	}
    }

    protected void canCreate(final Party parent, final Party child, final AccountabilityType type) {
	if (parent.equals(child)) {
	    throw new DomainException("error.Accountability.parent.equals.child");
	}
	if (parent.ancestorsInclude(child, type)) {
	    throw new DomainException("error.Accountability.parent.ancestors.include.child.with.type");
	}
	if (!type.isValid(parent, child)) {
	    throw new DomainException("error.Accountability.type.doesnot.allow.parent.child");
	}
    }

    public boolean isValid() {
	return hasParent() && hasChild() && getAccountabilityType().isValid(getParent(), getChild());
    }

    @jvstm.cps.ConsistencyPredicate
    protected boolean checkDateInterval() {
	return hasBeginDate() && (!hasEndDate() || !getBeginDate().isAfter(getEndDate()));
    }

    public boolean isActive(final LocalDate date) {
	return contains(date);
    }

    public boolean contains(final LocalDate date) {
	return !getBeginDate().isAfter(date) && (!hasEndDate() || !getEndDate().isAfter(date));
    }

    public boolean contains(final LocalDate begin, final LocalDate end) {
	check(begin, "error.Accountability.intercepts.invalid.begin");
	return (end == null || !getBeginDate().isAfter(end)) && (!hasEndDate() || !begin.isAfter(getEndDate()));
    }

    private boolean hasBeginDate() {
	return getBeginDate() != null;
    }

    private boolean hasEndDate() {
	return getEndDate() != null;
    }

    public boolean hasAccountabilityType(AccountabilityType type) {
	return getAccountabilityType().equals(type);
    }

    @Service
    void delete() {
	removeParent();
	removeChild();
	removeAccountabilityType();
	removeMyOrg();
	Transaction.deleteObject(this);
    }

    static Accountability create(final Party parent, final Party child, final AccountabilityType type, final LocalDate begin,
	    final LocalDate end) {
	return new Accountability(parent, child, type, begin, end);
    }

}
