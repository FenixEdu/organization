package module.organization.presentationTier.actions;

import java.util.HashMap;
import java.util.Map;

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

}
