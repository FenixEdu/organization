/*
 * @(#)OrganizationPropertyTag.java
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
package module.organization.presentationTier.renderers.tagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 
 * @author João Figueiredo
 * 
 */
public class OrganizationPropertyTag extends BodyTagSupport {

    private static final long serialVersionUID = -3100934168569590888L;

    private String name;

    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private boolean hasValue() {
        return getValue() != null;
    }

    @Override
    public int doStartTag() throws JspException {
        return hasValue() ? SKIP_BODY : EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        final OrganizationTagLib parent = (OrganizationTagLib) findAncestorWithClass(this, OrganizationTagLib.class);
        if (parent != null) {
            if (getValue() != null) {
                parent.setProperty(getName(), getValue());
            } else {
                parent.setProperty(getName(), getBodyContent().getString());
            }
        } else {
            throw new RuntimeException("could.not.find.correct.ancestor");
        }
        return super.doEndTag();
    }
}
