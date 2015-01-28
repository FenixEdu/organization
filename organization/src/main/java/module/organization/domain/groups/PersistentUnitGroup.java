/*
 * @(#)UnitGroup.java
 *
 * Copyright 2011 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Organization Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Organization Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.organization.domain.groups;

import java.util.Objects;
import java.util.Set;

import module.organization.domain.AccountabilityType;
import module.organization.domain.OrganizationDomainException;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.groups.Group;

import pt.ist.fenixframework.Atomic;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class PersistentUnitGroup extends PersistentUnitGroup_Base {

    private PersistentUnitGroup(final Unit unit, final Set<AccountabilityType> memberTypes,
            final Set<AccountabilityType> childUnitTypes) {
        super();
        if (memberTypes.size() == 0) {
            throw OrganizationDomainException.cannotCreateEmptyUnitGroup();
        }
        setUnit(unit);
        for (final AccountabilityType accountabilityType : memberTypes) {
            getMemberAccountabilityTypeSet().add(accountabilityType);
        }
        for (final AccountabilityType accountabilityType : childUnitTypes) {
            getChildUnitAccountabilityTypeSet().add(accountabilityType);
        }
    }

    @Override
    public Group toGroup() {
        return UnitGroup.of(getUnit(), getMemberAccountabilityTypeSet(), getChildUnitAccountabilityTypeSet());
    }

    static PersistentUnitGroup getInstance(final Unit unit, final Set<AccountabilityType> memberTypes,
            final Set<AccountabilityType> childUnitTypes) {
        Optional<PersistentUnitGroup> group = select(unit, memberTypes, childUnitTypes);
        return group.isPresent() ? group.get() : create(unit, memberTypes, childUnitTypes);
    }

    @Atomic
    private static PersistentUnitGroup create(final Unit unit, final Set<AccountabilityType> memberTypes,
            final Set<AccountabilityType> childUnitTypes) {
        Optional<PersistentUnitGroup> group = select(unit, memberTypes, childUnitTypes);
        return group.isPresent() ? group.get() : new PersistentUnitGroup(unit, memberTypes, childUnitTypes);
    }

    private static Optional<PersistentUnitGroup> select(Unit unit, final Set<AccountabilityType> memberTypes,
            final Set<AccountabilityType> childUnitTypes) {
        return FluentIterable.from(unit.getUnitGroupSet()).filter(new Predicate<PersistentUnitGroup>() {
            @Override
            public boolean apply(PersistentUnitGroup group) {
                return Objects.equals(group.getMemberAccountabilityTypeSet(), memberTypes)
                        && Objects.equals(group.getChildUnitAccountabilityTypeSet(), childUnitTypes);
            }
        }).first();
    }

}
