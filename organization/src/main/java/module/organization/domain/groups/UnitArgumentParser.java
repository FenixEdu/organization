package module.organization.domain.groups;

import module.organization.domain.Unit;

import org.fenixedu.bennu.core.annotation.GroupArgumentParser;
import org.fenixedu.bennu.core.groups.ArgumentParser;

import pt.ist.fenixframework.FenixFramework;

@GroupArgumentParser
public class UnitArgumentParser implements ArgumentParser<Unit> {
    @Override
    public Unit parse(String argument) {
        return FenixFramework.getDomainObject(argument);
    }

    @Override
    public String serialize(Unit argument) {
        return argument.getExternalId();
    }

    @Override
    public Class<Unit> type() {
        return Unit.class;
    }
}
