package module.organization.presentationTier.renderers;

import java.util.Set;
import java.util.TreeSet;

import module.organization.domain.Party;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class AllPartiesProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyConverter();
    }

    @Override
    public Object provide(Object source, Object currentValue) {

	// TODO: check method performance

	final Set<Party> result = new TreeSet<Party>(Party.COMPARATOR_BY_NAME);
	for (final Party party : MyOrg.getInstance().getTopUnitsSet()) {
	    result.add(party);
	    result.addAll(party.getDescendents());
	}
	return result;
    }

}
