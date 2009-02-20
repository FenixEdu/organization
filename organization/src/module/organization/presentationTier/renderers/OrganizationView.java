/*
 * @(#)OrganizationView.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module for the MyOrg web application.
 *
 *   The Organization Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
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
import module.organization.domain.PartyPredicate;
import module.organization.presentationTier.renderers.decorators.PartyDecorator;

public interface OrganizationView {

    public String getRootClasses();

    public String getChildListStyle();

    public String getBlankImage();

    public String getMinusImage();

    public String getPlusImage();

    public String getViewPartyUrl();

    public Comparator<Party> getSortBy();

    public PartyDecorator getDecorator();

    public PartyPredicate getPredicate();
    
    public void setProperty(final String name, final Object value);
}
