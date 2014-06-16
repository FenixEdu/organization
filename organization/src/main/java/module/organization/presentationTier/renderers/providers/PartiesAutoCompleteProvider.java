/*
 * @(#)PartiesAutoCompleteProvider.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.organization.domain.Party;
import module.organization.domain.Person;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import org.fenixedu.commons.StringNormalizer;

/**
 * 
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class PartiesAutoCompleteProvider implements AutoCompleteProvider<Party> {

    @Override
    public Collection<Party> getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
        final List<Party> parties = new ArrayList<Party>();

        final String trimmedValue = value.trim();
        final String[] input = StringNormalizer.normalize(trimmedValue).split(" ");

        for (final Party party : Bennu.getInstance().getPartiesSet()) {
            final String partyName = StringNormalizer.normalize(party.getPartyName().getContent());
            if (hasMatch(input, partyName)) {
                if (allowResult(party)) {
                    parties.add(party);
                }
            } else {
                if (party.isUnit()) {
                    final Unit unit = (Unit) party;
                    final String unitAcronym = StringNormalizer.normalize(unit.getAcronym());
                    if (hasMatch(input, unitAcronym)) {
                        if (allowResult(party)) {
                            parties.add(unit);
                        }
                    }
                } else if (party.isPerson()) {
                    final Person person = (Person) party;
                    final String username = person.getUser() != null ? person.getUser().getUsername() : null;
                    if (username != null && username.equalsIgnoreCase(trimmedValue)) {
                        if (allowResult(party)) {
                            parties.add(person);
                        }
                    }
                } else {
                    throw new Error("Unknown party type: " + party);
                }
            }
        }

        Collections.sort(parties, Party.COMPARATOR_BY_TYPE_AND_NAME);

        return parties;
    }

    protected boolean allowResult(final Party party) {
        return true;
    }

    private boolean hasMatch(final String[] input, final String partyNameParts) {
        for (final String namePart : input) {
            if (partyNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

}
