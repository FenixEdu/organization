/*
 * @(#)GroupAliasBean.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

import pt.ist.bennu.core.domain.groups.PersistentGroup;

/**
 * 
 * @author João Antunes
 * 
 */
public class GroupAliasBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;

    private MultiLanguageString alias;

    private PersistentGroup groupToEdit;

    public void setAlias(MultiLanguageString alias) {
	this.alias = alias;
    }

    public MultiLanguageString getAlias() {
	return alias;
    }

    public void setGroupToEdit(PersistentGroup groupToEdit) {
	this.groupToEdit = groupToEdit;
    }

    public PersistentGroup getGroupToEdit() {
	return groupToEdit;
    }

}
