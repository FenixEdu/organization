package module.organization.domain.dto;

import java.io.Serializable;

import module.organization.domain.Party;

public class PartyBean implements Serializable {

    private Party party;

    public PartyBean() {
    }

    public PartyBean(final Party party) {
	setParty(party);
    }

    public Party getParty() {
	return party;
    }

    public void setParty(final Party party) {
	this.party = party;
    }

}
