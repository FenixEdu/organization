package module.organization.domain;

import jvstm.cps.ConsistencyPredicate;
import myorg.domain.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class AccountabilityVersion extends AccountabilityVersion_Base {
    
    private AccountabilityVersion(LocalDate beginDate, LocalDate endDate,
 Accountability acc, boolean erased) {
        super();
	setAccountability(acc);
	setErased(erased);
	setBeginDate(beginDate);
	setEndDate(endDate);
	setCreationDate(new DateTime());
	setUserWhoCreated(myorg.applicationTier.Authenticate.UserView.getCurrentUser());
    }
    
    /**
     * FENIX-337 - temporary constructor used only for migration
     * 
     * @param beginDate
     * @param endDate
     * @param acc
     * @param erased
     */
    public AccountabilityVersion(LocalDate beginDate, LocalDate endDate, Accountability acc, boolean erased,
	    User userWhoCreatedThis, DateTime timeWhenItWasCreated) {
	super();
	setAccountability(acc);
	setErased(erased);
	setBeginDate(beginDate);
	setEndDate(endDate);
	setCreationDate(timeWhenItWasCreated);
	setUserWhoCreated(userWhoCreatedThis);
    }

    @ConsistencyPredicate
    public boolean checkListBehaviour() {
	if (hasPreviousAccVersion() && hasAccountability())
	    return false;
	if (!hasPreviousAccVersion() && !hasAccountability())
	    return false;
	return true;

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
     * 
     *            //TODO FENIX-337 after migration put this back to protected
     * 
     */
    public static void insertAccountabilityVersion(LocalDate beginDate,
 LocalDate endDate, Accountability acc, boolean erased) {
	if (acc == null)
	    throw new IllegalArgumentException("cant.provide.a.null.accountability");
	//let's check on the first case i.e. when the given acc does not have an AccountabilityHistory associated
	AccountabilityVersion firstAccHistory = acc.getAccountabilityVersion();
	AccountabilityVersion newAccountabilityHistory = new AccountabilityVersion(beginDate, endDate, acc, erased);
	if (firstAccHistory == null) {
	    //we are the first ones, let's just create ourselves
	    //TODO uncomment FENIX-337 this verification doesn't make sense untill all of the Accountabilities are migrated
	    //	    if (erased)
	    //		throw new IllegalArgumentException("creating.a.deleted.acc.does.not.make.sense"); //we shouldn't be creating a deleted accountability to start with!
	} else {
	    //let's push all of the next accHistories into their rightful position
	    firstAccHistory.setPreviousAccVersion(newAccountabilityHistory);
	    newAccountabilityHistory.setNextAccVersion(firstAccHistory);
	}
    }



}
