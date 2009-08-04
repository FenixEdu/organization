package module.organization.presentationTier.renderers.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.organization.domain.Party;
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
	    final String unitName = StringNormalizer.normalize(party.getPartyName().getContent());
	    if (hasMatch(input, unitName)) {
		parties.add(party);
	    }
	}

	Collections.sort(parties, Party.COMPARATOR_BY_TYPE_AND_NAME);

	return parties;
    }

    private boolean hasMatch(final String[] input, final String unitNameParts) {
	for (final String namePart : input) {
	    if (unitNameParts.indexOf(namePart) == -1) {
		return false;
	    }
	}
	return true;
    }

}
