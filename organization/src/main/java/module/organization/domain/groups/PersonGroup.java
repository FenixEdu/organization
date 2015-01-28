package module.organization.domain.groups;

import java.util.Set;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.domain.groups.PersistentGroup;
import org.fenixedu.bennu.core.groups.CustomGroup;
import org.fenixedu.bennu.core.groups.Group;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class PersonGroup extends CustomGroup {

    private static final long serialVersionUID = 5008108517178121023L;

    @Override
    public String getPresentationName() {
        return BundleUtil.getString("resources/OrganizationResources", "label.persistent.group.personGroup.name");
    }

    @Override
    public PersistentGroup toPersistentGroup() {
        return PersistentPersonGroup.getInstance();
    }

    @Override
    public Set<User> getMembers() {
        return FluentIterable.from(Bennu.getInstance().getUserSet()).filter(new Predicate<User>() {
            @Override
            public boolean apply(User input) {
                return input.getPerson() != null;
            }
        }).toSet();
    }

    @Override
    public Set<User> getMembers(DateTime when) {
        return getMembers();
    }

    @Override
    public boolean isMember(User user) {
        return user != null && user.getPerson() != null;
    }

    @Override
    public boolean isMember(User user, DateTime when) {
        return isMember(user);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PersonGroup;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private static final PersonGroup INSTANCE = new PersonGroup();

    public static Group get() {
        return INSTANCE;
    }

}
