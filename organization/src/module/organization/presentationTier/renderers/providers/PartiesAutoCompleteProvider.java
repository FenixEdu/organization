package module.organization.presentationTier.renderers.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.organization.domain.Party;
import module.organization.domain.Person;
import module.organization.domain.Unit;
import myorg.domain.MyOrg;
import myorg.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class PartiesAutoCompleteProvider implements AutoCompleteProvider {

    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
	final List<Party> parties = new ArrayList<Party>();

	final String trimmedValue = value.trim();
	final String[] input = trimmedValue.split(" ");
	StringNormalizer.normalize(input);

	for (final Party party : MyOrg.getInstance().getParties()) {
	    final String partyName = StringNormalizer.normalize(party.getPartyName().getContent());
	    if (hasMatch(input, partyName)) {
		parties.add(party);
	    } else {
		if (party.isUnit()) {
		    final Unit unit = (Unit) party;
		    final String unitAcronym = StringNormalizer.normalize(unit.getAcronym());
		    if (hasMatch(input, unitAcronym)) {
			parties.add(unit);
		    }
		} else if (party.isPerson()) {
		    final Person person = (Person) party;
		    final String username = person.hasUser() ? person.getUser().getUsername() : null;
		    if (username != null && username.equalsIgnoreCase(trimmedValue)) {
			parties.add(person);
		    }
		} else {
		    throw new Error("Unknown party type: " + party);
		}
	    }
	}

	Collections.sort(parties, Party.COMPARATOR_BY_TYPE_AND_NAME);

	return parties;
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
