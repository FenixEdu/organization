/*
 * @(#)PartySearchType.java
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
package module.organization.domain.search;

import java.util.ResourceBundle;

import org.fenixedu.commons.i18n.I18N;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

/**
 * 
 * @author Luis Cruz
 * 
 */
public enum PartySearchType implements IPresentableEnum {

    ASCENDETS_AND_DESCENDENTS, LOCAL_TREE;

    @Override
    public String getLocalizedName() {
        try {
            final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.OrganizationResources", I18N.getLocale());
            return resourceBundle.getString(PartySearchType.class.getSimpleName() + "." + name());
        } catch (final Exception ex) {
            ex.printStackTrace();
            throw new Error(ex);
        }
    }

}
