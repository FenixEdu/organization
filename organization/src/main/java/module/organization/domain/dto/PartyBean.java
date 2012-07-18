/*
 * @(#)PartyBean.java
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
package module.organization.domain.dto;

import java.io.Serializable;

import module.organization.domain.Party;

/**
 * 
 * @author Pedro Santos
 * @author Luis Cruz
 * 
 */
public class PartyBean implements Serializable {

    private Party party;

    public PartyBean() {
    }

    public PartyBean(final Party party) {
	setParty(party);
    }

    public Party getParty() {
	return party;
    }

    public void setParty(final Party party) {
	this.party = party;
    }

}
