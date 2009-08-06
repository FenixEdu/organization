/*
 * @(#)LinkablePartyDecorator.java
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

package module.organization.presentationTier.renderers.decorators;

import module.organization.domain.Party;
import module.organization.presentationTier.renderers.layouts.OrganizationLayout;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlLink;

public class LinkablePartyDecorator implements PartyDecorator {

    @Override
    public HtmlComponent decorate(final Party party, final OrganizationLayout layout) {
	final HtmlLink link = new HtmlLink();
	link.setUrl(String.format(layout.getViewPartyUrl(), party.getExternalId()));
	link.setModuleRelative(false);
	link.setIndented(false);
	link.setBody(new PartyNameDecorator().decorate(party, layout));
	return link;
    }

}
