package module.organization.domain;

import java.util.Comparator;
import java.util.ResourceBundle;

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixframework.DomainObject;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class FunctionDelegation extends FunctionDelegation_Base {

    public static Comparator<FunctionDelegation> COMPARATOR_BY_DELEGATEE_PARENT_UNIT_NAME = new Comparator<FunctionDelegation>() {
	@Override
	public int compare(FunctionDelegation delegation1, FunctionDelegation delegation2) {
	    int nameComp = delegation1.getAccountabilityDelegatee().getParent().getPresentationName()
		    .compareTo(delegation2.getAccountabilityDelegatee().getParent().getPresentationName());
	    return (nameComp != 0) ? nameComp : COMPARATOR_BY_DELEGATEE_CHILD_PARTY_NAME.compare(delegation1, delegation2);
	}
    };

    public static Comparator<FunctionDelegation> COMPARATOR_BY_DELEGATEE_CHILD_PARTY_NAME = new Comparator<FunctionDelegation>() {
	@Override
	public int compare(FunctionDelegation delegation1, FunctionDelegation delegation2) {
	    int nameComp = delegation1.getAccountabilityDelegatee().getChild().getPresentationName()
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
	setMyOrg(MyOrg.getInstance());
	setAccountabilityDelegator(accountability);
	final AccountabilityType accountabilityType = accountability.getAccountabilityType();
	if (unit.hasAnyIntersectingChildAccountability(person, accountabilityType, beginDate, endDate)) {
	    throw new DomainException("error.FunctionDelegation.already.assigned", ResourceBundle.getBundle(
		    "resources/OrganizationResources", Language.getLocale()));
	}
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
