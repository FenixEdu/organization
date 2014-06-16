package module.organization.domain;

import javax.ws.rs.core.Response.Status;

import org.fenixedu.bennu.core.domain.exceptions.DomainException;

public class OrganizationDomainException extends DomainException {

    private static final long serialVersionUID = 8503676934166774136L;

    public OrganizationDomainException(String key, String... args) {
        super(Status.PRECONDITION_FAILED, "resources.OrganizationResources", key, args);
    }

    public static OrganizationDomainException cannotCreateEmptyUnitGroup() {
        return new OrganizationDomainException("cannot.create.empty.unit.group");
    }

    public static OrganizationDomainException unknownPartyType() {
        return new OrganizationDomainException("unknown.party.type");
    }

    public static OrganizationDomainException emptyUnitName() {
        return new OrganizationDomainException("error.Unit.invalid.name");
    }

    public static OrganizationDomainException emptyUnitAcronym() {
        return new OrganizationDomainException("error.Unit.invalid.acronym");
    }

    public static OrganizationDomainException cannotEditInvalidUnit() {
        return new OrganizationDomainException("error.Unit.invalid.accountabilities.cannot.edit.information");
    }

    public static OrganizationDomainException invalidPartyType() {
        return new OrganizationDomainException("error.Unit.invalid.party.type");
    }

    public static OrganizationDomainException invalidAccountabilityType() {
        return new OrganizationDomainException("error.Unit.invalid.accountability.type");
    }

    public static OrganizationDomainException functionDelegationAlreadyAssigned() {
        return new OrganizationDomainException("error.FunctionDelegation.already.assigned");
    }

    public static OrganizationDomainException uniqueNameAndAcronymConnectionRuleAlreadyExists() {
        return new OrganizationDomainException("error.UniqueNameAndAcronymConnectionRule.rule.already.exists");
    }

}
