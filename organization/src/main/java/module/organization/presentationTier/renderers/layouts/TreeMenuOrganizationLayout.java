/*
 * @(#)TreeMenuOrganizationLayout.java
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
package module.organization.presentationTier.renderers.layouts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.organization.domain.Accountability;
import module.organization.domain.Party;
import module.organization.domain.Unit;
import module.organization.presentationTier.renderers.OrganizationView;

import org.fenixedu.bennu.core.domain.Bennu;

import pt.ist.fenixWebFramework.renderers.components.HtmlBlockContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlImage;
import pt.ist.fenixWebFramework.renderers.components.HtmlLink;
import pt.ist.fenixWebFramework.renderers.components.HtmlList;
import pt.ist.fenixWebFramework.renderers.components.HtmlListItem;
import pt.ist.fenixWebFramework.renderers.components.HtmlScript;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;

/**
 * 
 * @author Pedro Santos
 * @author João Figueiredo
 * 
 */
public class TreeMenuOrganizationLayout extends Layout implements OrganizationLayout {

    private static final String IMG_PREFIX = "img";
    private OrganizationView view;

    public TreeMenuOrganizationLayout() {
    }

    @Override
    public TreeMenuOrganizationLayout saveView(final OrganizationView view) {
        this.view = view;
        return this;
    }

    @Override
    public HtmlComponent createComponent(final Object object, final Class type) {
        final HtmlBlockContainer container = new HtmlBlockContainer();
        container.addChild(generateScript());

        if (object instanceof Bennu) {
            container.addChild(drawOrganization((Bennu) object));
        } else if (object instanceof Party) {
            container.addChild(drawOrganization((Party) object));
        } else {
            throw new IllegalArgumentException();
        }

        return container;
    }

    private HtmlScript generateScript() {
        final HtmlScript script = new HtmlScript();
        script.setContentType("text/javascript");
        script.setConditional(true);

        final HtmlLink minusLink = new HtmlLink();
        minusLink.setModuleRelative(false);
        minusLink.setUrl(this.view.getMinusImage());

        final HtmlLink plusLink = new HtmlLink();
        plusLink.setModuleRelative(false);
        plusLink.setUrl(this.view.getPlusImage());

        script.setScript("\n" + " function change(parentId, subId) {\n" + "   var v = document.getElementById(parentId);\n"
                + "   var e = document.getElementById(subId);\n" + "   if (e.style.display == \"none\") {\n"
                + "     e.style.display = \"\";\n" + "     v.src = \"" + minusLink.calculateUrl() + "\";\n" + "   } else {\n"
                + "     e.style.display = \"none\";\n" + "     v.src = \"" + plusLink.calculateUrl() + "\";\n" + "   }\n"
                + " }\n\n");
        return script;
    }

    private HtmlComponent drawOrganization(final Bennu myOrg) {
        final HtmlList list = new HtmlList();
        list.setClasses(this.view.getRootClasses());

        final List<Unit> topUnits = new ArrayList<Unit>(myOrg.getTopUnitsSet());
        Collections.sort(topUnits, this.view.getSortBy());

        for (final Unit unit : topUnits) {
            drawParty(list, unit);
        }

        return list;
    }

    private HtmlComponent drawOrganization(final Party party) {
        final HtmlList list = new HtmlList();
        list.setClasses(this.view.getRootClasses());

        drawParty(list, party);
        return list;
    }

    private void drawParty(final HtmlList list, final Party party) {
        final HtmlImage image = new HtmlImage();
        final HtmlListItem item = createItem(list, image, party);

        final HtmlList childHtmlList = createChildHtmlList(party);
        drawPartyChildren(childHtmlList, party);

        if (!childHtmlList.getChildren().isEmpty()) {
            item.addChild(childHtmlList);
            calculateImageUrl(image, this.view.getPlusImage());
            generateImageOid(party, image);
            generateImageOnClick(party, image, childHtmlList);
        } else {
            calculateImageUrl(image, this.view.getBlankImage());
        }
    }

    private void calculateImageUrl(final HtmlImage image, final String imagePath) {
        final HtmlLink link = new HtmlLink();
        link.setModuleRelative(false);
        link.setUrl(imagePath);
        image.setSource(link.calculateUrl());
    }

    private void generateImageOid(final Party party, final HtmlImage image) {
        image.setId(IMG_PREFIX + party.getExternalId());
    }

    private void generateImageOnClick(final Party party, final HtmlImage image, final HtmlList childHtmlList) {
        image.setOnClick(String.format("change('%s', '%s');return false;", IMG_PREFIX + party.getExternalId(),
                childHtmlList.getId()));
    }

    private HtmlList createChildHtmlList(final Party parent) {
        final HtmlList list = new HtmlList();
        list.setId(IMG_PREFIX + parent.getExternalId() + "chd");
        list.setStyle(this.view.getChildListStyle());
        return list;
    }

    private HtmlListItem createItem(final HtmlList list, final HtmlImage image, final Party party) {
        final HtmlListItem item = list.createItem();
        item.addChild(image);
        item.addChild(getDecorator(party));
        return item;
    }

    private HtmlComponent getDecorator(final Party party) {
        return this.view.getDecorator().decorate(party, this);
    }

    protected void drawPartyChildren(final HtmlList childHtmlList, final Party parent) {
        final List<Party> children = new ArrayList<Party>(parent.getChildAccountabilitiesSet().size());

        for (final Accountability accountability : parent.getChildAccountabilitiesSet()) {
            if (this.view.getPredicate().eval(accountability.getChild(), accountability)) {
                children.add(accountability.getChild());
            }
        }

        Collections.sort(children, this.view.getSortBy());
        for (final Party party : children) {
            drawParty(childHtmlList, party);
        }
    }

    @Override
    public String getViewPartyUrl() {
        return this.view.getViewPartyUrl();
    }
}
