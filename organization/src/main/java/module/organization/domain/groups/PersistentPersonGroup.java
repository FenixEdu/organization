/*
 * @(#)PersonGroup.java
 *
 * Copyright 2010 Instituto Superior Tecnico
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
package module.organization.domain.groups;

import org.fenixedu.bennu.core.domain.BennuGroupIndex;
import org.fenixedu.bennu.core.groups.Group;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;

/**
 * 
 * @author Pedro Santos
 * @author Susana Fernandes
 * 
 */
public class PersistentPersonGroup extends PersistentPersonGroup_Base {

    public PersistentPersonGroup() {
        super();
    }

    @Override
    public Group toGroup() {
        return PersonGroup.get();
    }

    static PersistentPersonGroup getInstance() {
        PersistentPersonGroup instance = BennuGroupIndex.getGroupConstant(PersistentPersonGroup.class);
        return instance != null ? instance : create();
    }

    @Atomic(mode = TxMode.WRITE)
    private static PersistentPersonGroup create() {
        PersistentPersonGroup instance = BennuGroupIndex.getGroupConstant(PersistentPersonGroup.class);
        return instance != null ? instance : new PersistentPersonGroup();
    }

}
