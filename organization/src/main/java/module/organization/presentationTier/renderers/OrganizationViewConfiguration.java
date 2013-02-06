/*
 * @(#)OrganizationViewConfiguration.java
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
package module.organization.presentationTier.renderers;

import java.util.Comparator;

import module.organization.domain.Party;
import module.organization.domain.predicates.PartyPredicate;
import module.organization.presentationTier.renderers.decorators.LinkablePartyDecorator;
import module.organization.presentationTier.renderers.decorators.PartyDecorator;
import module.organization.presentationTier.renderers.layouts.OrganizationLayout;
import module.organization.presentationTier.renderers.layouts.TreeMenuOrganizationLayout;

/**
 * 
 * @author João Figueiredo
 * 
 */
public class OrganizationViewConfiguration {

    protected OrganizationLayout layout = null;

    protected PartyDecorator decorator = null;

    protected PartyPredicate predicate = null;

    protected Comparator<Party> sortBy = null;

    public OrganizationLayout getLayout() {
        return layout;
    }

    public PartyDecorator getDecorator() {
        return decorator;
    }

    public PartyPredicate getPredicate() {
        return predicate;
    }

    public Comparator<Party> getSortBy() {
        return sortBy;
    }

    static private final OrganizationViewConfiguration DEFAULT = new OrganizationViewConfiguration() {
        {
            this.layout = new TreeMenuOrganizationLayout();
            this.decorator = new LinkablePartyDecorator();
            this.predicate = new PartyPredicate.TruePartyPredicate();
            this.sortBy = Party.COMPARATOR_BY_NAME;
        }
    };

    static public OrganizationViewConfiguration defaultConfiguration() {
        return DEFAULT;
    }
}
