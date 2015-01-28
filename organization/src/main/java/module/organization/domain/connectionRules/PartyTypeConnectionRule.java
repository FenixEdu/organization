/*
 * @(#)PartyTypeConnectionRule.java
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

import java.text.MessageFormat;

import module.organization.domain.AccountabilityType;
import module.organization.domain.ConnectionRule;
import module.organization.domain.OrganizationDomainException;
import module.organization.domain.Party;
import module.organization.domain.PartyType;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.i18n.BundleUtil;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Luis Cruz
 * 
 */
public class PartyTypeConnectionRule extends PartyTypeConnectionRule_Base {

    static public class PartyTypeConnectionRuleBean extends ConnectionRuleBean {

        static private final long serialVersionUID = -1173570520382412227L;

        private PartyType parent;
        private PartyType child;

        public PartyTypeConnectionRuleBean() {
            super();
        }

        public PartyTypeConnectionRuleBean(final PartyTypeConnectionRule connectionRule) {
            super(connectionRule);
            setParent(connectionRule.getAllowedParent());
            setChild(connectionRule.getAllowedChild());
        }

        public PartyType getParent() {
            return parent;
        }

        public void setParent(final PartyType parent) {
            this.parent = parent;
        }

        public PartyType getChild() {
            return child;
        }

        public void setChild(final PartyType child) {
            this.child = child;
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

    @Atomic
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
            throw new OrganizationDomainException("error.PartyTypeConnectionRule.parent.and.child.are.equals");
        }
        checkIfExistsRule(allowedParent, allowedChild);
        setAllowedParent(allowedParent);
        setAllowedChild(allowedChild);
    }

    private void checkIfExistsRule(final PartyType allowedParent, final PartyType allowedChild) {
        for (final ConnectionRule each : Bennu.getInstance().getConnectionRulesSet()) {
            if (each != this && each instanceof PartyTypeConnectionRule) {
                final PartyTypeConnectionRule rule = (PartyTypeConnectionRule) each;
                if (rule.getAllowedParent().equals(allowedParent) && rule.getAllowedChild().equals(allowedChild)) {
                    final String[] args =
                            new String[] { allowedParent.getName().getContent(), allowedChild.getName().getContent() };
                    throw new OrganizationDomainException(
                            "error.PartyTypeConnectionRule.already.exists.with.same.parent.and.child", args);
                }
            }
        }
    }

    private void check(final Object obj, final String message) {
        if (obj == null) {
            throw new OrganizationDomainException(message);
        }
    }

    @Override
    protected void disconnect() {
        setAllowedParent(null);
        setAllowedChild(null);
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
        return parent.getPartyTypesSet().contains(getAllowedParent());
    }

    boolean hasAllowedChild(final Party child) {
        return child.getPartyTypesSet().contains(getAllowedChild());
    }

    @Override
    public String getDescription() {
        final String message =
                BundleUtil.getString("resources/OrganizationResources", "label.PartyTypeConnectionRule.description");
        return MessageFormat.format(message, getAllowedParent().getName().getContent(), getAllowedChild().getName().getContent());
    }
}
