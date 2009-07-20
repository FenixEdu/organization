package module.organization.presentationTier.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.Party;

public class PartyViewHookManager {

    private Map<String, PartyViewHook> hookMap = new HashMap<String, PartyViewHook>();

    public void register(final PartyViewHook partyViewHook) {
	final String viewName = partyViewHook.getViewName();
	hookMap.put(viewName, partyViewHook);
    }

    public String hook(final HttpServletRequest request, final Party party) {
	final String viewName = getViewName(request);
	if (viewName != null) {
	    final PartyViewHook partyViewHook = hookMap.get(viewName);
	    if (partyViewHook != null) {
		final String viewPage = partyViewHook.hook(request, party);
		request.setAttribute("viewPage", viewPage);
	    }
	}
	return null;
    }

    private String getViewName(final HttpServletRequest request) {
	final String viewName = request.getParameter("viewName");
	return viewName == null ? (String) request.getAttribute("viewName") : viewName;
    }

    public int hookCount() {
	return hookMap.size();
    }

    public SortedSet<PartyViewHook> getSortedHooks(final Party party) {
	final SortedSet<PartyViewHook> partyViewHooks = new TreeSet<PartyViewHook>(PartyViewHook.COMPARATOR_BY_ORDER);
	for (final PartyViewHook partyViewHook : hookMap.values()) {
	    if (partyViewHook.isAvailableFor(party)) {
		partyViewHooks.add(partyViewHook);
	    }
	}
	return partyViewHooks;
    }

}
