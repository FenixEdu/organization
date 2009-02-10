/*
 * @(#)OrganizationManagementAction.java
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

package module.organization.presentationTier.renderers.layouts;

import module.organization.domain.Accountability;
import module.organization.domain.Party;
import module.organization.domain.Unit;
import module.organization.presentationTier.renderers.OrganizationRenderer;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlList;
import pt.ist.fenixWebFramework.renderers.components.HtmlListItem;
import pt.ist.fenixWebFramework.renderers.components.HtmlText;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;

public class OrganizationLayout extends Layout {

    private OrganizationRenderer renderer;

    public OrganizationLayout(final OrganizationRenderer renderer) {
	this.renderer = renderer;
    }

    @Override
    public HtmlComponent createComponent(final Object object, final Class type) {
	if (object instanceof MyOrg) {
	    return drawOrganization((MyOrg) object);

	} else if (object instanceof Party && canRender((Party) object)) {
	    return drawOrganization((Party) object);

	} else {
	    // TODO: add message
	    return new HtmlText(object.getClass().getName());
	}
    }

    private HtmlComponent drawOrganization(final MyOrg myOrg) {
	final HtmlList list = new HtmlList();
	list.setClasses(renderer.getClasses());

	for (final Unit unit : myOrg.getTopUnitsSet()) {
	    if (canRender(unit)) {
		createItem(list, unit, 1);
	    }
	}

	return list;
    }

    protected boolean canRender(final Party party) {
	return true;
    }

    private HtmlComponent drawOrganization(final Party party) {
	final HtmlList list = new HtmlList();
	list.setClasses(renderer.getClasses());
	createItem(list, party, 1);
	return list;
    }

    private void createItem(final HtmlList list, final Party party, int level) {
	final HtmlListItem item = createItem(list, getPartyClasses(party, level), getDecorator(party, level));
	final HtmlList childHtmlList = createChildHtmlList(level);

	drawChildInformation(childHtmlList, party, level);

	if (!childHtmlList.getChildren().isEmpty()) {
	    item.addChild(childHtmlList);
	}
    }

    protected void drawChildInformation(final HtmlList childHtmlList, final Party parent, int level) {
	// TODO: add predicate here?
	// TODO: sort by ....?
	for (final Accountability accountability : parent.getChildAccountabilitiesSet()) {
	    createItem(childHtmlList, accountability.getChild(), level + 1);
	}
    }

    private HtmlList createChildHtmlList(int level) {
	final HtmlList list = new HtmlList();
	if (renderer.hasLevelClasses()) {
	    list.setClasses(renderer.getLevelClasses() + level);
	}
	return list;
    }

    private HtmlListItem createItem(final HtmlList list, final String classes, final HtmlComponent component) {
	final HtmlListItem item = list.createItem();
	item.setClasses(classes);
	if (component != null) {
	    item.addChild(component);
	}
	return item;
    }

    private String getPartyClasses(final Party party, int level) {
	return level == 1 ? renderer.getRootClasses() : getPartyClasses(party);
    }

    private String getPartyClasses(final Party party) {
	if (party.isUnit()) {
	    return renderer.getUnitClasses();
	} else if (party.isPerson()) {
	    return renderer.getPersonClasses();
	} else {
	    return null;
	}
    }

    private HtmlComponent getDecorator(final Party party, int level) {
	return renderer.getDecorator().decorate(party, level);
    }
}
