package module.organization.domain;

import java.text.MessageFormat;

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.util.DomainReference;

public class PartyTypeConnectionRule extends PartyTypeConnectionRule_Base {

    static public class PartyTypeConnectionRuleBean extends ConnectionRuleBean {

	static private final long serialVersionUID = -1173570520382412227L;

	private DomainReference<PartyType> parent;
	private DomainReference<PartyType> child;

	public PartyTypeConnectionRuleBean() {
	    super();
	}

	public PartyTypeConnectionRuleBean(final PartyTypeConnectionRule connectionRule) {
	    super(connectionRule);
	    setParent(connectionRule.getAllowedParent());
	    setChild(connectionRule.getAllowedChild());
	}

	public PartyType getParent() {
	    return parent != null ? parent.getObject() : null;
	}

	public void setParent(final PartyType parent) {
	    this.parent = (parent != null ? new DomainReference<PartyType>(parent) : null);
	}

	public PartyType getChild() {
	    return child != null ? child.getObject() : null;
	}

	public void setChild(final PartyType child) {
	    this.child = (child != null ? new DomainReference<PartyType>(child) : null);
	}

	@Override
	public PartyTypeConnectionRule getConnectionRule() {
	    return (PartyTypeConnectionRule) super.getConnectionRule();
	}

	@Override
	public PartyTypeConnectionRule create() {
	    return PartyTypeConnectionRule.create(getParent(), getChild());
	}
    }

    @Service
    static public PartyTypeConnectionRule create(final PartyType allowedParent, final PartyType allowedChild) {
	return new PartyTypeConnectionRule(allowedParent, allowedChild);
    }

    private PartyTypeConnectionRule() {
	super();
    }

    PartyTypeConnectionRule(final PartyType allowedParent, final PartyType allowedChild) {
	this();
	check(allowedParent, "error.PartyTypeConnectionRule.invalid.parent.type");
	check(allowedChild, "error.PartyTypeConnectionRule.invalid.child.type");
	if (allowedParent == allowedChild) {
	    throw new DomainException("error.PartyTypeConnectionRule.parent.and.child.are.equals");
	}
	checkIfExistsRule(allowedParent, allowedChild);
	setAllowedParent(allowedParent);
	setAllowedChild(allowedChild);
    }

    private void checkIfExistsRule(final PartyType allowedParent, final PartyType allowedChild) {
	for (final ConnectionRule each : MyOrg.getInstance().getConnectionRulesSet()) {
	    if (each != this && each instanceof PartyTypeConnectionRule) {
		final PartyTypeConnectionRule rule = (PartyTypeConnectionRule) each;
		if (rule.getAllowedParent().equals(allowedParent) && rule.getAllowedChild().equals(allowedChild)) {
		    throw new DomainException("error.PartyTypeConnectionRule.already.exists.with.same.parent.and.child",
			    allowedParent.getName().getContent(), allowedChild.getName().getContent());
		}
	    }
	}
    }

    private void check(final Object obj, final String message) {
	if (obj == null) {
	    throw new DomainException(message);
	}
    }

    @Override
    protected void disconnect() {
	removeAllowedParent();
	removeAllowedChild();
	super.disconnect();
    }

    @Override
    public PartyTypeConnectionRuleBean buildBean() {
	return new PartyTypeConnectionRuleBean(this);
    }

    @Override
    public boolean isValid(final AccountabilityType accountabilityType, final Party parent, final Party child) {
	return hasAllowedParent(parent) && hasAllowedChild(child);
    }

    boolean hasAllowedParent(final Party parent) {
	return parent.hasPartyTypes(getAllowedParent());
    }

    boolean hasAllowedChild(final Party child) {
	return child.hasPartyTypes(getAllowedChild());
    }

    @Override
    public String getDescription() {
	final String message = BundleUtil.getStringFromResourceBundle("resources/OrganizationResources",
		"label.PartyTypeConnectionRule.description");
	return MessageFormat.format(message, getAllowedParent().getName().getContent(), getAllowedChild().getName().getContent());
    }
}
