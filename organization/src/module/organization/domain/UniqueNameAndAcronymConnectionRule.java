package module.organization.domain;

import java.util.Collection;

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class UniqueNameAndAcronymConnectionRule extends UniqueNameAndAcronymConnectionRule_Base {

    static public class UniqueNameAndAcronymConnectionRuleBean extends ConnectionRuleBean {

	private static final long serialVersionUID = -5795825256317278222L;

	public UniqueNameAndAcronymConnectionRuleBean() {
	    super();
	}

	public UniqueNameAndAcronymConnectionRuleBean(final UniqueNameAndAcronymConnectionRule connectionRule) {
	    super(connectionRule);
	}

	@Override
	public ConnectionRule create() {
	    return UniqueNameAndAcronymConnectionRule.create();
	}
    }

    @Service
    static public UniqueNameAndAcronymConnectionRule create() {
	return new UniqueNameAndAcronymConnectionRule();
    }

    private UniqueNameAndAcronymConnectionRule() {
	super();
	checkIfExistsRule();
    }

    private void checkIfExistsRule() {
	for (final ConnectionRule rule : MyOrg.getInstance().getConnectionRulesSet()) {
	    if (rule != this && rule instanceof UniqueNameAndAcronymConnectionRule) {
		throw new DomainException("error.UniqueNameAndAcronymConnectionRule.rule.already.exists");
	    }
	}
    }

    @Override
    public UniqueNameAndAcronymConnectionRuleBean buildBean() {
	return new UniqueNameAndAcronymConnectionRuleBean(this);
    }

    @Override
    public boolean isValid(final AccountabilityType accountabilityType, final Party parent, final Party child) {
	return (parent.isUnit() && child.isUnit()) ? checkNameAndAcronym((Unit) parent, (Unit) child) : true;
    }

    private boolean checkNameAndAcronym(final Unit parent, final Unit child) {
	return (parent != null) ? checkChildsNameAndAcronym(parent.getChildAccountabilities(), child)
		: checkTopUnitsNameAndAcronym(child);
    }

    private boolean checkChildsNameAndAcronym(final Collection<Accountability> accountabilities, final Unit child) {
	for (final Accountability accountability : accountabilities) {
	    if (accountability.getChild().isUnit() && !accountability.getChild().equals(child)
		    && hasSameNameAndAcronym((Unit) accountability.getChild(), child)) {
		return false;
	    }
	}
	return true;
    }

    private boolean checkTopUnitsNameAndAcronym(final Unit unit) {
	for (final Party party : MyOrg.getInstance().getTopUnits()) {
	    if (party.isUnit() && !party.equals(this) && hasSameNameAndAcronym((Unit) party, unit)) {
		return false;
	    }
	}
	return true;
    }

    private boolean hasSameNameAndAcronym(final Unit one, final Unit other) {
	return one.getPartyName().equalInAnyLanguage(other.getPartyName())
		&& one.getAcronym().equalsIgnoreCase(other.getAcronym());
    }

    @Override
    public String getDescription() {
	return BundleUtil.getStringFromResourceBundle("resources/OrganizationResources",
		"label.UniqueNameAndAcronymConnectionRule.description");
    }
}
