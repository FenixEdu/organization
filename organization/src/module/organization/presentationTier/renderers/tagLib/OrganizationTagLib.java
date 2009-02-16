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

package module.organization.presentationTier.renderers.tagLib;

import java.io.IOException;
import java.util.Comparator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import module.organization.domain.Party;
import module.organization.domain.PartyPredicate;
import module.organization.presentationTier.renderers.OrganizationView;
import module.organization.presentationTier.renderers.OrganizationViewConfiguration;
import module.organization.presentationTier.renderers.decorators.PartyDecorator;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;

public class OrganizationTagLib extends TagSupport implements OrganizationView {

    static private final long serialVersionUID = 2726718182435118282L;

    private String rootClasses = "tree";
    private String childListStyle = "display:none";

    private String blankImage = "/organization/images/blank.gif";
    private String minusImage = "/organization/images/minus.gif";
    private String plusImage = "/organization/images/plus.gif";

    private String organization;
    private String configuration;

    private OrganizationViewConfiguration config;

    public int doStartTag() throws JspException {
	try {
	    drawOrganization();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return SKIP_BODY;
    }

    private void drawOrganization() throws IOException {
	final Layout layout = (Layout) getConfig().getLayout().setView(this);
	layout.createComponent(pageContext.findAttribute(this.organization), null).draw(pageContext);
    }

    public String getRootClasses() {
	return rootClasses;
    }

    public void setRootClasses(String rootClasses) {
	this.rootClasses = rootClasses;
    }

    public String getChildListStyle() {
	return childListStyle;
    }

    public void setChildListStyle(String childListStyle) {
	this.childListStyle = childListStyle;
    }

    public String getBlankImage() {
	return blankImage;
    }

    public void setBlankImage(String blankImage) {
	this.blankImage = blankImage;
    }

    public String getMinusImage() {
	return minusImage;
    }

    public void setMinusImage(String minusImage) {
	this.minusImage = minusImage;
    }

    public String getPlusImage() {
	return plusImage;
    }

    public void setPlusImage(String plusImage) {
	this.plusImage = plusImage;
    }

    public String getOrganization() {
	return organization;
    }

    public void setOrganization(String organization) {
	this.organization = organization;
    }

    public String getConfiguration() {
	return configuration;
    }

    public void setConfiguration(String configuration) {
	this.configuration = configuration;
    }

    private OrganizationViewConfiguration getConfig() {
	if (this.config == null) {
	    this.config = (OrganizationViewConfiguration) pageContext.findAttribute(this.configuration);
	}
	return this.config;
    }

    @Override
    public PartyDecorator getDecorator() {
	return getConfig().getDecorator();
    }

    @Override
    public PartyPredicate getPredicate() {
	return getConfig().getPredicate();
    }

    @Override
    public Comparator<Party> getSortBy() {
	return getConfig().getSortBy();
    }

}
