/*
 * @(#)UnconfirmedAccountability.java
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
package module.organization.domain;

import module.organization.domain.AccountabilityType.AccountabilityTypeBean;

import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 *
 * @author João Antunes
 * @author Luis Cruz
 *
 */
public class UnconfirmedAccountability extends UnconfirmedAccountability_Base {

    private static final String ACCOUNTABILITY_TYPE_TYPE = "UnconfirmedAccountability";
    private static final String ACCOUNTABILITY_TYPE_NAME_KEY = "label.accountabilityType.unconfirmed";
    private static final String ACCOUNTABILITY_TYPE_NAME_BUNDLE = "resources/OrganizationResources";

    protected UnconfirmedAccountability() {
        super();
        setSubmited(new DateTime());
        setUser(Authenticate.getUser());
    }

    protected UnconfirmedAccountability(Party parent, Party child, AccountabilityType type, LocalDate begin, LocalDate end,
            String justification) {
        this();

        check(parent, "error.Accountability.invalid.parent");
        check(child, "error.Accountability.invalid.child");
        check(type, "error.Accountability.invalid.type");
        check(begin, "error.Accountability.invalid.begin");
        checkDates(parent, begin, end);

        canCreate(parent, child, type);

        init(parent, child, readAccountabilityType());
        setUnconfirmedAccountabilityType(type);
        AccountabilityVersion.insertAccountabilityVersion(begin, end, this, false, justification);
    }

    @Atomic
    public static AccountabilityType readAccountabilityType() {
        final AccountabilityType accountabilityType = AccountabilityType.readBy(ACCOUNTABILITY_TYPE_TYPE);
        return accountabilityType == null ? createAccountabilityType() : accountabilityType;
    }

    private static LocalizedString getLocalizedName() {
        return BundleUtil.getLocalizedString(ACCOUNTABILITY_TYPE_NAME_BUNDLE, ACCOUNTABILITY_TYPE_NAME_KEY);
    }

    private static AccountabilityType createAccountabilityType() {
        final AccountabilityTypeBean accountabilityTypeBean =
                new AccountabilityTypeBean(ACCOUNTABILITY_TYPE_TYPE, getLocalizedName());
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
        setUnconfirmedAccountabilityType(null);
        setUser(null);
        setAccountabilityVersion(null);
        setParent(null);
        setChild(null);
        setMyOrg(null);
        if (child.getParentAccountabilitiesSet().size() == 0) {
            child.delete();
        }
    }

    /**
     * @deprecated use {@link #confirm(String)} instead
     */
    @Deprecated
    public void confirm() {
        confirm(null);
        delete();
    }

    @Atomic
    public void confirm(String justification) {
        Accountability.create(getParent(), getChild(), getUnconfirmedAccountabilityType(), getBeginDate(), getEndDate(),
                justification);
        delete();
    }

    @Atomic
    public void reject() {
        delete();
    }

}
