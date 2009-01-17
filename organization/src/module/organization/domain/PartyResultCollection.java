package module.organization.domain;

import java.util.Collection;
import java.util.HashSet;

public class PartyResultCollection {
    private PartyPredicate predicate;
    private Collection<Party> result;

    PartyResultCollection(final PartyPredicate predicate) {
	this(new HashSet<Party>(), predicate);
    }

    PartyResultCollection(final Collection<Party> result, final PartyPredicate predicate) {
	this.predicate = predicate;
	this.result = result;
    }

    public boolean candAddParty(final Party party, final Accountability accountability) {
	return predicate.eval(party, accountability);
    }

    /**
     * Add Party to result if candAddParty is true and if this collection permit
     * duplicates and does not contains the specified element.
     * 
     */
    public boolean conditionalAddParty(final Party party, final Accountability accountability) {
	return candAddParty(party, accountability) && result.add(party);
    }

    @SuppressWarnings("unchecked")
    public <T extends Party> Collection<T> getResult() {
	return (Collection<T>) result;
    }
}
