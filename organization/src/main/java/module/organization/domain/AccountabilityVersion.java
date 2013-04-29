/*
 * @(#)AccountabilityVersion.java
 *
 * Copyright 2012 Instituto Superior Tecnico
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

import jvstm.cps.ConsistencyPredicate;
import module.organization.domain.util.OrganizationConsistencyException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.bennu.core.domain.User;

/**
 * 
 * @author João Antunes
 * @author João Neves
 * @author Susana Fernandes
 * 
 */
public class AccountabilityVersion extends AccountabilityVersion_Base {

    private AccountabilityVersion(LocalDate beginDate, LocalDate endDate, Accountability acc, boolean erased, String justification) {
        super();
        super.setAccountability(acc);
        super.setJustification(justification);
        super.setErased(erased);
        super.setBeginDate(beginDate);
        super.setEndDate(endDate);
        super.setCreationDate(new DateTime());
        super.setUserWhoCreated(pt.ist.bennu.core.applicationTier.Authenticate.UserView.getCurrentUser());
    }

    // let's protect all of the methods that could compromise the workings of
    // the Acc. Version
    @Deprecated
    @Override
    public void setAccountability(Accountability accountability) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @Deprecated
    @Override
    public void setErased(boolean erased) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @Deprecated
    @Override
    public void setBeginDate(LocalDate beginDate) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @Deprecated
    @Override
    public void setEndDate(LocalDate endDate) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @Deprecated
    @Override
    public void setCreationDate(DateTime creationDate) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @Deprecated
    @Override
    public void setUserWhoCreated(User userWhoCreated) {
        throw new UnsupportedOperationException("this.slot.shouldn't.be.editable.make.new.object.instead");
    }

    @ConsistencyPredicate
    public boolean checkIsConnectedToList() {
        return (getPreviousAccVersion() != null && getAccountability() == null)
                || (getPreviousAccVersion() == null && getAccountability() != null);
    }

    @ConsistencyPredicate
    public boolean checkErasedAsFinalVersion() {
        return !getErased() || getAccountability() != null;
    }

    @ConsistencyPredicate(OrganizationConsistencyException.class)
    protected boolean checkDateInterval() {
        return getBeginDate() != null && (getEndDate() == null || !getBeginDate().isAfter(getEndDate()));
    }

    public void delete() {
        super.setUserWhoCreated(null);
        deleteDomainObject();
    }

    /**
     * It creates a new AccountabilityHistory item and pushes the others (if
     * they exist)
     * 
     * @param userWhoCreated
     * @param instantOfCreation
     * @param beginDate
     * @param endDate
     * @param acc
     *            the Accountability which
     * @param active
     *            if true, the new AccountabilityHistory will be marked as
     *            active, if it is false it is equivalent of deleting the new
     *            AccountabilityHistory
     * @param justification an information justification/reason for the change of accountability, or null if there is none, or
     *            none is provided
     * 
     * 
     */
    protected static void insertAccountabilityVersion(LocalDate beginDate, LocalDate endDate, Accountability acc, boolean erased,
            String justification) {
        if (acc == null) {
            throw new IllegalArgumentException("cant.provide.a.null.accountability");
        }
        // let's check on the first case i.e. when the given acc does not have
        // an AccountabilityHistory associated
        AccountabilityVersion firstAccVersion = acc.getAccountabilityVersion();
        if (firstAccVersion == null) {
            //we are the first ones, let's just create ourselves
            if (erased) {
                throw new IllegalArgumentException("creating.a.deleted.acc.does.not.make.sense"); //we shouldn't be creating a deleted accountability to start with!
            }
            new AccountabilityVersion(beginDate, endDate, acc, erased, justification);
        } else {
            // let's push all of the next accHistories into their rightful
            // position
            if (firstAccVersion.getBeginDate().equals(beginDate) && firstAccVersion.getErased() == erased
                    && matchingDates(firstAccVersion.getEndDate(), endDate)) {
                // do not create a new version with exactly the same data
                return;
            }
            AccountabilityVersion newAccountabilityHistory =
                    new AccountabilityVersion(beginDate, endDate, acc, erased, justification);
            newAccountabilityHistory.setNextAccVersion(firstAccVersion);
        }
    }

    public static boolean redundantInfo(AccountabilityVersion av1, AccountabilityVersion av2) {
        return ((av1.getBeginDate().equals(av2.getBeginDate())) && (av1.getErased() == av2.getErased()) && matchingDates(
                av1.getEndDate(), av2.getEndDate()));
    }

    private static boolean matchingDates(LocalDate date1, LocalDate date2) {
        if (date1 == null) {
            return date2 == null;
        }
        return date1.equals(date2);
    }
}
