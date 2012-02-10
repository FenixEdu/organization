package module.organization.domain;

import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.BundleUtil;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class UnconfirmedAccountability extends UnconfirmedAccountability_Base {

    private static final String ACCOUNTABILITY_TYPE_TYPE = "UnconfirmedAccountability";
    private static final String ACCOUNTABILITY_TYPE_NAME_KEY = "label.accountabilityType.unconfirmed";
    private static final String ACCOUNTABILITY_TYPE_NAME_BUNDLE = "resources/OrganizationResources";

    protected UnconfirmedAccountability() {
        super();
        setSubmited(new DateTime());
        final User user = UserView.getCurrentUser();
        setUser(user);
    }

    protected UnconfirmedAccountability(Party parent, Party child, AccountabilityType type, LocalDate begin, LocalDate end) {
	this();

	check(parent, "error.Accountability.invalid.parent");
	check(child, "error.Accountability.invalid.child");
	check(type, "error.Accountability.invalid.type");
	check(begin, "error.Accountability.invalid.begin");
	checkDates(parent, begin, end);

	canCreate(parent, child, type);

	init(parent, child, readAccountabilityType());
	setUnconfirmedAccountabilityType(type);
	createDates(begin, end);
    }

    @Service
    public static AccountabilityType readAccountabilityType() {
	final AccountabilityType accountabilityType = AccountabilityType.readBy(ACCOUNTABILITY_TYPE_TYPE);
        return accountabilityType == null ? createAccountabilityType() : accountabilityType;
    }

    private static MultiLanguageString getLocalizedName() {
	return BundleUtil.getMultilanguageString(ACCOUNTABILITY_TYPE_NAME_BUNDLE, ACCOUNTABILITY_TYPE_NAME_KEY);
    }

    private static AccountabilityType createAccountabilityType() {
	final AccountabilityTypeBean accountabilityTypeBean = new AccountabilityTypeBean(ACCOUNTABILITY_TYPE_TYPE, getLocalizedName());
	return AccountabilityType.create(accountabilityTypeBean);
    }

    @Override
    public String getDetailsString() {
	final StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getUnconfirmedAccountabilityType().getName().getContent());
	stringBuilder.append(": ");
	if (getBeginDate() != null) {
	    stringBuilder.append(getBeginDate().toString(LOCAL_DATE_FORMAT));
	}
	stringBuilder.append(" - ");
	if (getEndDate() != null) {
	    stringBuilder.append(getEndDate().toString(LOCAL_DATE_FORMAT));
	}
	return stringBuilder.toString();
    }

    @Override
    public void delete() {
	final Party child = getChild();
	removeUnconfirmedAccountabilityType();
	removeUser();
	removeAccountabilityVersion();
	removeParent();
	removeChild();
	removeCreatorUser();
	removeMyOrg();
	if (child.getParentAccountabilitiesCount() == 0) {
	    child.delete();
	}
    }

    @Service
    public void confirm() {
	Accountability.create(getParent(), getChild(), getUnconfirmedAccountabilityType(), getBeginDate(), getEndDate());
	delete();
    }

    @Service
    public void reject() {
	delete();
    }

}
