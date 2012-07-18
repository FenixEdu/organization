/*
 * @(#)PartyViewHookManager.java
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

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class PartyViewHookManager {

    private Map<String, PartyViewHook> hookMap = new HashMap<String, PartyViewHook>();

    public void register(final PartyViewHook partyViewHook) {
	final String viewName = partyViewHook.getViewName();
	hookMap.put(viewName, partyViewHook);
    }

    public String hook(final HttpServletRequest request, final OrganizationalModel organizationalModel, final Party party) {
	final String viewName = getViewName(request);
	if (viewName != null) {
	    final PartyViewHook partyViewHook = hookMap.get(viewName);
	    if (partyViewHook != null) {
		final String viewPage = partyViewHook.hook(request, organizationalModel, party);
		request.setAttribute("viewPage", viewPage);
	    }
	}
	return null;
    }

    private String getViewName(final HttpServletRequest request) {
	final String viewName = request.getParameter("viewName");
	return viewName == null ? (String) request.getAttribute("viewName") : viewName;
    }

    public int hookCount() {
	return hookMap.size();
    }

    public SortedSet<PartyViewHook> getSortedHooks(final Party party) {
	final SortedSet<PartyViewHook> partyViewHooks = new TreeSet<PartyViewHook>(PartyViewHook.COMPARATOR_BY_ORDER);
	for (final PartyViewHook partyViewHook : hookMap.values()) {
	    if (partyViewHook.isAvailableFor(party)) {
		partyViewHooks.add(partyViewHook);
	    }
	}
	return partyViewHooks;
    }

}
