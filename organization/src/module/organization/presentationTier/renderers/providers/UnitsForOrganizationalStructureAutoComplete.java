package module.organization.presentationTier.renderers.providers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import pt.ist.fenixframework.pstm.AbstractDomainObject;

public class UnitsForOrganizationalStructureAutoComplete extends UnitAutoCompleteProvider {

    @Override
    protected Set<Party> getParties(Map<String, String> argsMap, String value) {
	String oid = argsMap.get("modelId");
	if (oid == null) {
	    return Collections.emptySet();
	}

	OrganizationalModel model = AbstractDomainObject.fromOID(Long.valueOf(oid));
	Set<Party> parties = new HashSet<Party>();
	parties.addAll(model.getAllUnits());
	return parties;
    }
}
