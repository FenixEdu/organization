/*
 * @(#)UnitBean.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 *
 *      https://fenix-ashes.ist.utl.pt/
 *
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version
 *   3 of the License, or (at your option) any later version.
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

import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

/**
 *
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Luis Cruz
 *
 */
public class UnitBean extends PartyBean {

    private static final long serialVersionUID = 2418079845917474575L;

    private Unit unit;
    private Party child;
    private PartyType partyType;
    private OrganizationalModel organizationalModel;

    private LocalizedString name;
    private String acronym;

    private String accountabilityJustification;

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
        return child;
    }

    public void setChild(Party party) {
        this.child = party;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isTop() {
        return getUnit().isTop();
    }

    @Override
    public Party getParty() {
        return getUnit();
    }

    public LocalizedString getName() {
        return name;
    }

    public void setName(LocalizedString name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
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
        return organizationalModel;
    }

    public void setOrganizationalModel(final OrganizationalModel organizationalModel) {
        this.organizationalModel = organizationalModel;
    }

    public String getAccountabilityJustification() {
        return accountabilityJustification;
    }

    public void setAccountabilityJustification(String accountabilityJustification) {
        this.accountabilityJustification = accountabilityJustification;
    }

}
