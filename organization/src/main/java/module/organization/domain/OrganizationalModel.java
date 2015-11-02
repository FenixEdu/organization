/*
 * @(#)OrganizationalModel.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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
package module.organization.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.commons.i18n.LocalizedString;

import module.organization.domain.dto.OrganizationalModelBean;
import pt.ist.fenixframework.Atomic;

/**
 *
 * @author Paulo Abrantes
 * @author Luis Cruz
 *
 */
public class OrganizationalModel extends OrganizationalModel_Base {

    public static final Comparator<OrganizationalModel> COMPARATORY_BY_NAME = (o1, o2) -> {
        final int c = o1.getName().compareTo(o2.getName());
        return c == 0 ? o1.hashCode() - o2.hashCode() : c;
    };

    public OrganizationalModel() {
        super();
        setMyOrg(Bennu.getInstance());
    }

    public OrganizationalModel(final LocalizedString name) {
        this();
        setName(name);
    }

    public SortedSet<AccountabilityType> getSortedAccountabilityTypes() {
        final SortedSet<AccountabilityType> accountabilityTypes =
                new TreeSet<AccountabilityType>(AccountabilityType.COMPARATORY_BY_NAME);
        accountabilityTypes.addAll(getAccountabilityTypesSet());
        return accountabilityTypes;
    }

    @Atomic
    public static OrganizationalModel createNewModel(final OrganizationalModelBean organizationalModelBean) {
        return new OrganizationalModel(organizationalModelBean.getName());
    }

    @Atomic
    public void addPartyService(final Party party) {
        addParties(party);
    }

    @Atomic
    public void delete() {
        getAccountabilityTypesSet().clear();
        getPartiesSet().clear();
        setMyOrg(null);
        deleteDomainObject();
    }

    public void setAccountabilityTypes(final List<AccountabilityType> accountabilityTypes) {
        getAccountabilityTypesSet().clear();
        getAccountabilityTypesSet().addAll(accountabilityTypes);
    }

    /**
     * @deprecated use getAllUnitStream instead
     */
    @Deprecated
    public Set<Unit> getAllUnits() {
        return getAllUnitStream().collect(Collectors.toSet());
    }

    public Stream<Unit> getAllUnitStream() {
        return getPartiesSet().stream().filter(p -> p.isUnit()).map(p -> (Unit) p).flatMap(new Function<Unit, Stream<Unit>>() {
            @Override
            public Stream<Unit> apply(final Unit u) {
                return Stream.concat(Stream.of(u), u.getDescendentUnitStream());
            }
        });
    }

    public boolean containsUnit(final Party party) {
        if (getPartiesSet().contains(party)) {
            return true;
        }
        return party.getParentAccountabilityStream()
                .filter(a -> a.isActiveNow() && getAccountabilityTypesSet().contains(a.getAccountabilityType()))
                .map(a -> a.getParent()).anyMatch(p -> containsUnit(p));
    }

    @Deprecated
    public java.util.Set<module.organization.domain.Party> getParties() {
        return getPartiesSet();
    }

    @Deprecated
    public java.util.Set<module.organization.domain.AccountabilityType> getAccountabilityTypes() {
        return getAccountabilityTypesSet();
    }

}
