/*
 * @(#)PartyPredicate.java
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

abstract public class PartyPredicate {

    abstract public boolean eval(final Party party, final Accountability accountability);

    protected boolean hasClass(final Class<? extends Party> clazz, final Party party) {
	return clazz == null || clazz.isAssignableFrom(party.getClass());
    }

    protected boolean hasPartyType(final PartyType type, final Party party) {
	return type == null || party.getPartyType().equals(type);
    }

    protected boolean hasAccountabilityType(final AccountabilityType type, final Accountability accountability) {
	return type == null || accountability.hasAccountabilityType(type);
    }

    static public class TruePartyPredicate extends PartyPredicate {
	@Override
	public boolean eval(Party party, Accountability accountability) {
	    return true;
	}
    }

    static public class PartyByClassType extends PartyPredicate {
	protected Class<? extends Party> clazz;

	PartyByClassType(final Class<? extends Party> clazz) {
	    this.clazz = clazz;
	}

	@Override
	public boolean eval(Party party, Accountability accountability) {
	    return hasClass(clazz, party);
	}
    }

    static public class PartyByPartyType extends PartyByClassType {
	private PartyType type;

	PartyByPartyType(final PartyType type) {
	    this(null, type);
	}

	PartyByPartyType(final Class<? extends Party> clazz) {
	    this(clazz, null);
	}

	PartyByPartyType(final Class<? extends Party> clazz, final PartyType type) {
	    super(clazz);
	    this.type = type;
	}

	@Override
	public boolean eval(Party party, Accountability accountability) {
	    return hasClass(clazz, party) && hasPartyType(type, party);
	}
    }

    static public class PartyByAccountabilityType extends PartyByClassType {
	private AccountabilityType type;

	PartyByAccountabilityType(final AccountabilityType type) {
	    this(null, type);
	}

	PartyByAccountabilityType(final Class<? extends Party> clazz) {
	    this(clazz, null);
	}

	PartyByAccountabilityType(final Class<? extends Party> clazz, final AccountabilityType type) {
	    super(clazz);
	    this.type = type;
	}

	@Override
	public boolean eval(Party party, Accountability accountability) {
	    return hasClass(clazz, party) && hasAccountabilityType(type, accountability);
	}
    }
}
