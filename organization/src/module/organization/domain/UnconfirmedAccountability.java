package module.organization.domain;

import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import myorg.util.BundleUtil;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class UnconfirmedAccountability extends UnconfirmedAccountability_Base {

    private static final String ACCOUNTABILITY_TYPE_TYPE = "module.organization.domain.UnconfirmedAccountability";
    private static final String ACCOUNTABILITY_TYPE_NAME_KEY = "label.accountabilityType.unconfirmed";
    private static final String ACCOUNTABILITY_TYPE_NAME_BUNDLE = "resources/OrganizationResources";

    protected UnconfirmedAccountability() {
        super();
    }

    protected UnconfirmedAccountability(Party parent, Party child, AccountabilityType type, LocalDate begin, LocalDate end) {
	this();

	check(parent, "error.Accountability.invalid.parent");
	check(child, "error.Accountability.invalid.child");
	check(type, "error.Accountability.invalid.type");
	check(begin, "error.Accountability.invalid.begin");
	checkDates(parent, begin, end);

	canCreate(parent, child, type);

	setParent(parent);
	setChild(child);
	setAccountabilityType(readAccountabilityType());
	setUnconfirmedAccountabilityType(type);
	setBeginDate(begin);
	setEndDate(end);
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

}
