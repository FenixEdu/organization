package module.organization.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.Party;

public abstract class PartyViewHook {

    public abstract String getViewName();

    public abstract String hook(final HttpServletRequest request, final Party party);

}
