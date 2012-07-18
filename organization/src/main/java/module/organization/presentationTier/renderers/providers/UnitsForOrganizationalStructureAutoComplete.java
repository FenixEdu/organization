/*
 * @(#)UnitsForOrganizationalStructureAutoComplete.java
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
package module.organization.presentationTier.renderers.providers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import pt.ist.fenixframework.pstm.AbstractDomainObject;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class UnitsForOrganizationalStructureAutoComplete extends UnitAutoCompleteProvider {

    @Override
    protected Set<Party> getParties(Map<String, String> argsMap, String value) {
	String oid = argsMap.get("modelId");
	if (oid == null) {
	    return Collections.emptySet();
	}

	OrganizationalModel model = AbstractDomainObject.fromOID(Long.valueOf(oid));
	Set<Party> parties = new HashSet<Party>();
	parties.addAll(model.getAllUnits());
	return parties;
    }
}
