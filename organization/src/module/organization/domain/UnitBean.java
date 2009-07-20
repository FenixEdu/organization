/*
 * @(#)UnitBean.java
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

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.util.DomainReference;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class UnitBean extends PartyBean {

    private static final long serialVersionUID = 2418079845917474575L;
    
    private DomainReference<Unit> unit;
    private DomainReference<Party> child;
    private DomainReference<PartyType> partyType;
    private DomainReference<OrganizationalModel> organizationalModel;

    private MultiLanguageString name;
    private String acronym;

    public UnitBean() {
	setBegin(new LocalDate());
    }

    public UnitBean(final Unit unit) {
	this();

	setUnit(unit);
	setName(unit.getPartyName());
	setAcronym(unit.getAcronym());
    }

    public Party getChild() {
	return (this.child != null) ? this.child.getObject() : null;
    }

    public void setChild(Party party) {
	this.child = (party != null) ? new DomainReference<Party>(party) : null;
    }

    public Unit getUnit() {
	return (this.unit != null) ? this.unit.getObject() : null;
    }

    public void setUnit(Unit unit) {
	this.unit = (unit != null) ? new DomainReference<Unit>(unit) : null;
    }

    public boolean isTop() {
	return getUnit().isTop();
    }
    
    @Override
    public Party getParty() {
	return getUnit();
    }

    public MultiLanguageString getName() {
	return name;
    }

    public void setName(MultiLanguageString name) {
	this.name = name;
    }

    public String getAcronym() {
	return acronym;
    }

    public void setAcronym(String acronym) {
	this.acronym = acronym;
    }

    public PartyType getPartyType() {
	return (this.partyType != null) ? this.partyType.getObject() : null;
    }

    public void setPartyType(PartyType partyType) {
	this.partyType = (partyType != null) ? new DomainReference<PartyType>(partyType) : null;
    }

    public Unit createUnit() {
	return Unit.create(this);
    }

    public Unit editUnit() {
	return getUnit().edit(getName(), getAcronym());
    }

    @Override
    public void addParent() {
	getUnit().addParent(getParent(), getAccountabilityType(), getBegin(), getEnd());
    }

    public void addChild() {
	getParent().addChild(getChild(), getAccountabilityType(), getBegin(), getEnd());
    }

    public OrganizationalModel getOrganizationalModel() {
        return organizationalModel == null ? null : organizationalModel.getObject();
    }

    public void setOrganizationalModel(final OrganizationalModel organizationalModel) {
        this.organizationalModel = organizationalModel == null ? null : new DomainReference<OrganizationalModel>(organizationalModel);
    }

}
