package module.organization.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.organization.domain.ConnectionRule;

import org.fenixedu.bennu.core.domain.Bennu;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class ConnectionRulesProvider implements DataProvider {

    @Override
    public Converter getConverter() {
        return null;
    }

    @Override
    public Object provide(Object source, Object currentValue) {
        return new ArrayList<ConnectionRule>(Bennu.getInstance().getConnectionRulesSet());
    }

}
