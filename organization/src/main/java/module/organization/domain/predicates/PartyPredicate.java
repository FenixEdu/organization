/*
 * @(#)PartyPredicate.java
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
package module.organization.domain.predicates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.PartyType;

import org.joda.time.LocalDate;

/**
 * 
 * @author João Antunes
 * @author João Figueiredo
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
abstract public class PartyPredicate {

    abstract public boolean eval(final Party party, final Accountability accountability);

    protected boolean hasClass(final Class<? extends Party> clazz, final Party party) {
        return clazz == null || clazz.isAssignableFrom(party.getClass());
    }

    protected boolean hasPartyType(final PartyType type, final Party party) {
        return type == null || party.getPartyTypesSet().contains(type);
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

        public PartyByClassType(final Class<? extends Party> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean eval(Party party, Accountability accountability) {
            return hasClass(clazz, party);
        }
    }

    static public class PartyByPartyType extends PartyByClassType {
        private final PartyType type;

        public PartyByPartyType(final PartyType type) {
            this(null, type);
        }

        public PartyByPartyType(final Class<? extends Party> clazz) {
            this(clazz, null);
        }

        public PartyByPartyType(final Class<? extends Party> clazz, final PartyType type) {
            super(clazz);
            this.type = type;
        }

        @Override
        public boolean eval(Party party, Accountability accountability) {
            return hasClass(clazz, party) && hasPartyType(type, party);
        }
    }

    static public class PartyByAccTypeAndDates extends PartyPredicate {

        private final LocalDate dateOfStart;
        private final LocalDate dateOfEnd;
        List<AccountabilityType> types = new ArrayList<AccountabilityType>();

        public PartyByAccTypeAndDates(final LocalDate dateOfStart, final LocalDate dateOfEnd, final AccountabilityType... types) {
            for (AccountabilityType type : types) {
                this.types.add(type);
            }
            this.dateOfStart = dateOfStart;
            this.dateOfEnd = dateOfEnd;
        }

        public PartyByAccTypeAndDates(final LocalDate dateOfStart, final LocalDate dateOfEnd, List<AccountabilityType> types) {
            if (types != null) {
                this.types.addAll(types);
            }
            this.dateOfStart = dateOfStart;
            this.dateOfEnd = dateOfEnd;
        }

        @Override
        public boolean eval(Party party, Accountability accountability) {

            return accountability.intersects(dateOfStart, dateOfEnd) && hasMatchingOrNoAccountabilityType(accountability);
        }

        public boolean wasActiveAtStartDate(Accountability accountability) {
            return dateOfStart == null || accountability.contains(dateOfStart);
        }

        public boolean wasActiveAtEndDate(Accountability accountability) {
            return dateOfEnd == null || accountability.contains(dateOfEnd);
        }

        protected boolean hasMatchingOrNoAccountabilityType(final Accountability accountability) {
            if (types.isEmpty()) {
                return true;
            }
            for (final AccountabilityType type : types) {
                if (hasAccountabilityType(type, accountability)) {
                    return true;
                }
            }
            return false;
        }

    }

    static public class PartyByAccountabilityType extends PartyByClassType {
        private final Set<AccountabilityType> types = new HashSet<AccountabilityType>();

        public PartyByAccountabilityType(final AccountabilityType type) {
            this(null, type);
        }

        public PartyByAccountabilityType(final Collection<AccountabilityType> types) {
            this(null, types);
        }

        public PartyByAccountabilityType(final AccountabilityType... types) {
            super(null);
            for (AccountabilityType type : types) {
                this.types.add(type);
            }
        }

        public PartyByAccountabilityType(final Class<? extends Party> clazz) {
            this(clazz, (AccountabilityType) null);
        }

        public PartyByAccountabilityType(final Class<? extends Party> clazz, final AccountabilityType type) {
            super(clazz);
            types.add(type);
        }

        public PartyByAccountabilityType(final Class<? extends Party> clazz, final Collection<AccountabilityType> types) {
            super(clazz);
            this.types.addAll(types);
        }

        @Override
        public boolean eval(Party party, Accountability accountability) {
            return hasClass(clazz, party) && hasMatchingAccountabilityType(accountability);
        }

        protected boolean hasMatchingAccountabilityType(final Accountability accountability) {
            for (final AccountabilityType type : types) {
                if (hasAccountabilityType(type, accountability)) {
                    return true;
                }
            }
            return false;
        }
    }
}
