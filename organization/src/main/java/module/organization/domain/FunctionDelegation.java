/*
 * @(#)FunctionDelegation.java
 *
 * Copyright 2010 Instituto Superior Tecnico
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

import java.util.Comparator;

import jvstm.cps.ConsistencyPredicate;

import org.fenixedu.bennu.core.domain.Bennu;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.DomainObject;

/**
 * 
 * @author João Antunes
 * @author João Neves
 * @author Luis Cruz
 * 
 */
public class FunctionDelegation extends FunctionDelegation_Base {

    public static Comparator<FunctionDelegation> COMPARATOR_BY_DELEGATEE_PARENT_UNIT_NAME = new Comparator<FunctionDelegation>() {
        @Override
        public int compare(FunctionDelegation delegation1, FunctionDelegation delegation2) {
            int nameComp =
                    delegation1.getAccountabilityDelegatee().getParent().getPresentationName()
                            .compareTo(delegation2.getAccountabilityDelegatee().getParent().getPresentationName());
            return (nameComp != 0) ? nameComp : COMPARATOR_BY_DELEGATEE_CHILD_PARTY_NAME.compare(delegation1, delegation2);
        }
    };

    public static Comparator<FunctionDelegation> COMPARATOR_BY_DELEGATEE_CHILD_PARTY_NAME = new Comparator<FunctionDelegation>() {
        @Override
        public int compare(FunctionDelegation delegation1, FunctionDelegation delegation2) {
            int nameComp =
                    delegation1.getAccountabilityDelegatee().getChild().getPresentationName()
                            .compareTo(delegation2.getAccountabilityDelegatee().getChild().getPresentationName());
            return (nameComp != 0) ? nameComp : COMPARATOR_BY_EXTERNAL_ID.compare(delegation1, delegation2);
        }
    };

    //TODO: This should be moved to the AbstractDomainObject
    public static Comparator<DomainObject> COMPARATOR_BY_EXTERNAL_ID = new Comparator<DomainObject>() {
        @Override
        public int compare(DomainObject do1, DomainObject do2) {
            return do1.getExternalId().compareTo(do2.getExternalId());
        }
    };

    public FunctionDelegation(final Accountability accountability, final Unit unit, final Person person,
            final LocalDate beginDate, final LocalDate endDate) {
        super();
        setMyOrg(Bennu.getInstance());
        setAccountabilityDelegator(accountability);
        final AccountabilityType accountabilityType = accountability.getAccountabilityType();
        if (unit.hasAnyIntersectingChildAccountability(person, accountabilityType, beginDate, endDate)) {
            throw OrganizationDomainException.functionDelegationAlreadyAssigned();
        }
        final Accountability delegatedAccountability = unit.addChild(person, accountabilityType, beginDate, endDate);
        setAccountabilityDelegatee(delegatedAccountability);
        new FunctionDelegationLog(this, "Create");
    }

    @Atomic
    public static FunctionDelegation create(final Accountability accountability, final Unit unit, final Person person,
            final LocalDate beginDate, final LocalDate endDate) {
        return new FunctionDelegation(accountability, unit, person, beginDate, endDate);
    }

    @Atomic
    public void edit(final LocalDate beginDate, final LocalDate endDate) {
        new FunctionDelegationLog(this, "Edit");
        final Accountability accountabilityDelegatee = getAccountabilityDelegatee();
        // This avoids detecting intersections with itself
        accountabilityDelegatee.editDates(beginDate.minusDays(2), endDate.minusDays(1));

        final Unit unit = (Unit) getAccountabilityDelegatee().getParent();
        if (unit.hasAnyIntersectingChildAccountability(accountabilityDelegatee.getChild(),
                accountabilityDelegatee.getAccountabilityType(), beginDate, endDate)) {
            throw OrganizationDomainException.functionDelegationAlreadyAssigned();
        }

        accountabilityDelegatee.editDates(beginDate, endDate);
    }

    @Atomic
    public void delete() {
        new FunctionDelegationLog(this, "Delete");
        for (FunctionDelegationLog log : getFunctionDelegationLogs()) {
            removeFunctionDelegationLogs(log);
        }

        Accountability delegatedAccountability = getAccountabilityDelegatee();
        setAccountabilityDelegatee(null);
        delegatedAccountability.delete();

        setAccountabilityDelegator(null);
        setMyOrg(null);

        deleteDomainObject();
    }

    @ConsistencyPredicate
    public boolean checkHasAccountabilityDelegator() {
        return getAccountabilityDelegator() != null;
    }

    @ConsistencyPredicate
    public boolean checkHasAccountabilityDelegatee() {
        return getAccountabilityDelegatee() != null;
    }

    @Deprecated
    public java.util.Set<module.organization.domain.FunctionDelegationLog> getFunctionDelegationLogs() {
        return getFunctionDelegationLogsSet();
    }

}
