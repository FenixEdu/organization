package module.organization.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.organization.domain.AccountabilityType;
import module.organization.domain.Person;
import module.organization.domain.Unit;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class UnitGroup extends UnitGroup_Base {

    private UnitGroup(final Unit unit, final AccountabilityType[] memberTypes, final AccountabilityType[] childUnitTypes) {
	super();
	if (memberTypes == null || memberTypes.length == 0) {
	    throw new DomainException("cannot.create.empty.unit.group");
	}
	setUnit(unit);
	addAccountabilityTypes(getMemberAccountabilityTypeSet(), memberTypes);
	addAccountabilityTypes(getChildUnitAccountabilityTypeSet(), childUnitTypes);
    }

    private void addAccountabilityTypes(final Set<AccountabilityType> accountabilityTypes, final AccountabilityType[] typeArray) {
	if (typeArray != null) {
	    for (final AccountabilityType accountabilityType : typeArray) {
		accountabilityTypes.add(accountabilityType);
	    }
	}
    }

    @Override
    public Set<User> getMembers() {
	final Unit unit = getUnit();
	return unit.getMembers(getAccountabilityTypes());
    }

    @Override
    public String getName() {
	final Unit unit = getUnit();
	final StringBuilder builder = new StringBuilder();
	for (final AccountabilityType accountabilityType : getMemberAccountabilityTypeSet()) {
	    if (builder.length() > 0) {
		builder.append(", ");
	    }
	    builder.append(accountabilityType.getName().getContent());
	}
	final String unitName = unit.getPresentationName();
	final String unitIdentifier = hasAnyChildUnitAccountabilityType() ? BundleUtil.getFormattedStringFromResourceBundle(
		"resources/OrganizationResources", "label.persistent.group.unitGroup.includeing.subunits", unitName) : unitName;
	return BundleUtil.getFormattedStringFromResourceBundle("resources/OrganizationResources",
		"label.persistent.group.unitGroup.name", unitIdentifier, builder.toString());
    }

    @Override
    public boolean isMember(final User user) {
	if (user != null && user.hasPerson()) {
	    final Person person = user.getPerson();
	    final Unit unit = getUnit();
	    return person.hasPartyAsAncestor(unit, getAccountabilityTypes());
	}
	return false;
    }

    private transient Set<AccountabilityType> accountabilityTypes = null;

    private Set<AccountabilityType> getAccountabilityTypes() {
	Set<AccountabilityType> result = accountabilityTypes;
	if (result == null) {
	    result = new HashSet<AccountabilityType>();
	    result.addAll(getMemberAccountabilityTypeSet());
	    result.addAll(getChildUnitAccountabilityTypeSet());
	    accountabilityTypes = result;
	}
	return result;
    }

    @Service
    public static UnitGroup getOrCreateGroup(final Unit unit, final AccountabilityType[] memberTypes,
	    final AccountabilityType[] childUnitTypes) {
	if (unit == null) {
	    return null;
	}
	for (final UnitGroup group : unit.getUnitGroupSet()) {
	    if (match(group.getMemberAccountabilityTypeSet(), memberTypes)
		    && match(group.getChildUnitAccountabilityTypeSet(), childUnitTypes)) {
		return group;
	    }
	}
	return new UnitGroup(unit, memberTypes, childUnitTypes);
    }

    private static boolean match(final Set<AccountabilityType> types1, final AccountabilityType[] types2) {
	if (types2 == null) {
	    return types1.isEmpty();
	}

	if (types1.size() == types2.length) {
	    for (int i = 0; i < types2.length; i++) {
		final AccountabilityType accountabilityType = types2[i];
		if (!types1.contains(accountabilityType)) {
		    return false;
		}
		for (int j = i + 1; j < types2.length; j++) {
		    if (accountabilityType == types2[j]) {
			return false;
		    }
		}
	    }
	    return true;
	}
	return false;
    }

}
