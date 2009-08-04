package module.organization.presentationTier.renderers.providers;

import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class OrganizationalModelProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return null;
    }

    @Override
    public Object provide(Object source, Object currentValue) {
	return MyOrg.getInstance().getOrganizationalModels();
    }

}
