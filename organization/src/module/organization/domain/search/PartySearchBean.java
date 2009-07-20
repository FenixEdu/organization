package module.organization.domain.search;

import module.organization.domain.Party;
import module.organization.domain.dto.PartyBean;

public class PartySearchBean extends PartyBean {

    private PartySearchType partySearchType = PartySearchType.LOCAL_TREE;

    public PartySearchBean(final Party party) {
	super(party);
    }

    public PartySearchType getPartySearchType() {
        return partySearchType;
    }

    public void setPartySearchType(final PartySearchType partySearchType) {
        this.partySearchType = partySearchType;
    }

}
