package module.organization.domain.groups;

import module.organization.domain.AccountabilityType;

import org.fenixedu.bennu.core.annotation.GroupArgumentParser;
import org.fenixedu.bennu.core.groups.ArgumentParser;

import pt.ist.fenixframework.FenixFramework;

@GroupArgumentParser
public class AccountabilityTypeArgumentParser implements ArgumentParser<AccountabilityType> {
    @Override
    public AccountabilityType parse(String argument) {
        return FenixFramework.getDomainObject(argument);
    }

    @Override
    public String serialize(AccountabilityType argument) {
        return argument.getExternalId();
    }

    @Override
    public Class<AccountabilityType> type() {
        return AccountabilityType.class;
    }
}
