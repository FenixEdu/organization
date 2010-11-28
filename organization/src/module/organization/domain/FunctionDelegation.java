package module.organization.domain;

import myorg.domain.MyOrg;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;

public class FunctionDelegation extends FunctionDelegation_Base {
    
    public FunctionDelegation(final Accountability accountability, final Unit unit, final Person person,
	    final LocalDate beginDate, final LocalDate endDate) {
        super();
        setMyOrg(MyOrg.getInstance());
        setAccountabilityDelegator(accountability);
        final AccountabilityType accountabilityType = accountability.getAccountabilityType();
        final Accountability delegatedAccountability = unit.addChild(person, accountabilityType, beginDate, endDate);
        setAccountabilityDelegatee(delegatedAccountability);
        new FunctionDelegationLog(this);
    }

    @Service
    public static FunctionDelegation create(final Accountability accountability, final Unit unit, final Person person,
	    final LocalDate beginDate, final LocalDate endDate) {
	return new FunctionDelegation(accountability, unit, person, beginDate, endDate);
    }
    
}
