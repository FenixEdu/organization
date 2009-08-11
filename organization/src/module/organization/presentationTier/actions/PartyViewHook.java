package module.organization.presentationTier.actions;

import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;

public abstract class PartyViewHook {

    public static final Comparator<PartyViewHook> COMPARATOR_BY_ORDER = new Comparator<PartyViewHook>() {

	@Override
	public int compare(final PartyViewHook o1, final PartyViewHook o2) {
	    final int c = o2.ordinal() - o1.ordinal();
	    return c == 0 ? o1.getViewName().compareTo(o2.getViewName()) : c; 
	}

    };

    public abstract String getViewName();

    public abstract String hook(final HttpServletRequest request, final OrganizationalModel organizationalModel, final Party party);

    public int ordinal() {
	return 0;
    }

    public boolean isAvailableFor(final Party party) {
	return true;
    }

}
