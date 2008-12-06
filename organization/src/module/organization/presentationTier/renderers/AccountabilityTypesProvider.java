package module.organization.presentationTier.renderers;

import java.util.ArrayList;

import module.organization.domain.AccountabilityType;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class AccountabilityTypesProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return new DomainObjectKeyConverter();
    }

    @Override
    public Object provide(Object source, Object currentValue) {
	return new ArrayList<AccountabilityType>(MyOrg.getInstance().getAccountabilityTypes());
    }

}
