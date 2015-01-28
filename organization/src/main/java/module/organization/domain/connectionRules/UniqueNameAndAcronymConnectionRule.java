/*
 * @(#)UniqueNameAndAcronymConnectionRule.java
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
package module.organization.domain.connectionRules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.ConnectionRule;
import module.organization.domain.OrganizationDomainException;
import module.organization.domain.Party;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.commons.i18n.LocalizedString;

import pt.ist.fenixframework.Atomic;

/**
 *
 * @author João Figueiredo
 *
 */
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

    @Atomic
    static public UniqueNameAndAcronymConnectionRule create() {
        return new UniqueNameAndAcronymConnectionRule();
    }

    private UniqueNameAndAcronymConnectionRule() {
        super();
        checkIfExistsRule();
    }

    private void checkIfExistsRule() {
        for (final ConnectionRule rule : Bennu.getInstance().getConnectionRulesSet()) {
            if (rule != this && rule instanceof UniqueNameAndAcronymConnectionRule) {
                throw OrganizationDomainException.uniqueNameAndAcronymConnectionRuleAlreadyExists();
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
        return (parent != null) ? checkChildsNameAndAcronym(parent.getChildAccountabilitiesSet(), child) : checkTopUnitsNameAndAcronym(child);
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
        for (final Party party : Bennu.getInstance().getTopUnitsSet()) {
            if (party.isUnit() && !party.equals(this) && hasSameNameAndAcronym((Unit) party, unit)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSameNameAndAcronym(final Unit one, final Unit other) {
        return equalInAnyLanguage(one.getPartyName(), other.getPartyName())
                && one.getAcronym().equalsIgnoreCase(other.getAcronym());
    }

    private static boolean equalInAnyLanguage(LocalizedString one, LocalizedString other) {
        Set<Locale> locales = new HashSet<Locale>();
        locales.addAll(one.getLocales());
        locales.addAll(other.getLocales());
        for (Locale locale : locales) {
            if (one.getContent(locale) != null && one.getContent(locale).equalsIgnoreCase(other.getContent(locale))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return BundleUtil.getString("resources/OrganizationResources", "label.UniqueNameAndAcronymConnectionRule.description");
    }
}
