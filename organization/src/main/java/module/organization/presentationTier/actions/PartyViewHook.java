/*
 * @(#)PartyViewHook.java
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
package module.organization.presentationTier.actions;

import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;

/**
 * 
 * @author Luis Cruz
 * 
 */
public abstract class PartyViewHook {

    public static final Comparator<PartyViewHook> COMPARATOR_BY_ORDER = new Comparator<PartyViewHook>() {

        @Override
        public int compare(final PartyViewHook o1, final PartyViewHook o2) {
            final int c = o2.ordinal() - o1.ordinal();
            return c == 0 ? o1.getViewName().compareTo(o2.getViewName()) : c;
        }

    };

    public abstract String getViewName();

    public abstract String getPresentationName();

    public abstract String hook(final HttpServletRequest request, final OrganizationalModel organizationalModel, final Party party);

    public int ordinal() {
        return 0;
    }

    public boolean isAvailableFor(final Party party) {
        return true;
    }

}
