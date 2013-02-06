/*
 * @(#)GroupsSelectorBean.java
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
import java.util.ArrayList;
import java.util.List;

import pt.ist.bennu.core.domain.groups.PersistentGroup;

/**
 * 
 * @author João Antunes
 * 
 */
public class GroupsSelectorBean implements Serializable {

    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The groups that are selected
     */
    private List<PersistentGroup> groups;

    public void setGroups(List<PersistentGroup> groups) {
        this.groups = new ArrayList<PersistentGroup>();
        this.groups.addAll(groups);
    }

    public List<PersistentGroup> getGroups() {
        return groups;
    }

}
