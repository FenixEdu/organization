package module.organization.domain.groups;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import module.organization.domain.AccountabilityType;
import module.organization.domain.Person;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.annotation.GroupArgument;
import org.fenixedu.bennu.core.annotation.GroupOperator;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.domain.groups.PersistentGroup;
import org.fenixedu.bennu.core.groups.CustomGroup;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.joda.time.DateTime;

@GroupOperator("unit")
public class UnitGroup extends CustomGroup {

    private static final long serialVersionUID = 8820598765184907161L;

    @GroupArgument
    private Unit unit;

    @GroupArgument
    private Set<AccountabilityType> memberTypes;

    @GroupArgument
    private Set<AccountabilityType> childUnitTypes;

    private UnitGroup() {
        super();
    }

    private UnitGroup(Unit unit, Set<AccountabilityType> memberTypes, Set<AccountabilityType> childUnitTypes) {
        this.unit = unit;
        this.memberTypes = memberTypes;
        this.childUnitTypes = childUnitTypes;
    }

    public static UnitGroup of(Unit unit, Set<AccountabilityType> memberTypes) {
        return new UnitGroup(unit, memberTypes, Collections.<AccountabilityType> emptySet());
    }

    public static UnitGroup of(Unit unit, Set<AccountabilityType> memberTypes, Set<AccountabilityType> childUnitTypes) {
        return new UnitGroup(unit, memberTypes, childUnitTypes);
    }

    @Override
    public String getPresentationName() {
        final StringBuilder builder = new StringBuilder();
        for (final AccountabilityType accountabilityType : memberTypes) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(accountabilityType.getName().getContent());
        }
        final String unitName = unit.getPresentationName();
        final String unitIdentifier =
                !childUnitTypes.isEmpty() ? BundleUtil.getString("resources/OrganizationResources",
                        "label.persistent.group.unitGroup.includeing.subunits", unitName) : unitName;
        return BundleUtil.getString("resources/OrganizationResources", "label.persistent.group.unitGroup.name", unitIdentifier,
                builder.toString());
    }

    @Override
    public PersistentGroup toPersistentGroup() {
        return PersistentUnitGroup.getInstance(unit, memberTypes, childUnitTypes);
    }

    @Override
    public Set<User> getMembers() {
        return unit.getMembers(getAccountabilityTypes());
    }

    @Override
    public Set<User> getMembers(DateTime when) {
        return getMembers();
    }

    @Override
    public boolean isMember(User user) {
        if (user != null && user.getPerson() != null) {
            final Person person = user.getPerson();
            return person.hasPartyAsAncestor(unit, getAccountabilityTypes());
        }
        return false;
    }

    @Override
    public boolean isMember(User user, DateTime when) {
        return isMember(user);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof UnitGroup) {
            UnitGroup group = (UnitGroup) object;
            return Objects.equals(unit, group.unit) && Objects.equals(memberTypes, group.memberTypes)
                    && Objects.equals(childUnitTypes, group.childUnitTypes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, memberTypes, childUnitTypes);
    }

    private Set<AccountabilityType> getAccountabilityTypes() {
        Set<AccountabilityType> result = new HashSet<AccountabilityType>();
        result.addAll(memberTypes);
        result.addAll(childUnitTypes);
        return result;
    }

}
