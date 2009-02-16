/*
 * @(#)AllPartiesProvider.java
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

package module.organization.presentationTier.renderers.providers;

import java.util.Set;
import java.util.TreeSet;

import module.organization.domain.Party;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class AllPartiesProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyConverter();
    }

    @Override
    public Object provide(Object source, Object currentValue) {

	// TODO: check method performance

	final Set<Party> result = new TreeSet<Party>(Party.COMPARATOR_BY_NAME);
	for (final Party party : MyOrg.getInstance().getTopUnitsSet()) {
	    result.add(party);
	    result.addAll(party.getDescendents());
	}
	return result;
    }

}
