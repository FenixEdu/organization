package module.organization.domain.dto;

import java.io.Serializable;

import module.organization.domain.Party;
import pt.ist.fenixWebFramework.util.DomainReference;

public class PartyBean implements Serializable {

    private DomainReference<Party> party;

    public PartyBean() {
    }

    public PartyBean(final Party party) {
	setParty(party);
    }

    public Party getParty() {
        return party == null ? null : party.getObject();
    }

    public void setParty(final Party party) {
        this.party = party == null ? null : new DomainReference<Party>(party);
    }

}
