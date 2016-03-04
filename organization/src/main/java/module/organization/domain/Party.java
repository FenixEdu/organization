/*
 * @(#)Party.java
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

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.groups.DynamicGroup;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.commons.i18n.I18N;
import org.joda.time.LocalDate;

import module.organization.domain.predicates.PartyPredicate.PartyByAccountabilityType;
import module.organization.domain.predicates.PartyPredicate.PartyByClassType;
import module.organization.domain.predicates.PartyPredicate.PartyByPartyType;
import module.organization.domain.predicates.PartyPredicate.TruePartyPredicate;
import module.organization.domain.predicates.PartyResultCollection;
import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author João Antunes
 * @author João Neves
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Sérgio Silva
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
abstract public class Party extends Party_Base {

    static public final Comparator<Party> COMPARATOR_BY_NAME = new Comparator<Party>() {
        @Override
        public int compare(Party o1, Party o2) {
            int res = o1.getPartyName().compareTo(o2.getPartyName());
            return res != 0 ? res : o1.getExternalId().compareTo(o2.getExternalId());
        }
    };

    static public final Comparator<Party> COMPARATOR_BY_TYPE_AND_NAME = new Comparator<Party>() {
        @Override
        public int compare(Party o1, Party o2) {
            int result = o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
            return result == 0 ? COMPARATOR_BY_NAME.compare(o1, o2) : result;
        }
    };

    protected Party() {
        super();
        setMyOrg(Bennu.getInstance());
    }

    public Stream<Party> getParentStream() {
        return getParentAccountabilityStream().map(a -> a.getParent());
    }

    public Set<Accountability> getAllParentAccountabilities() {
        return super.getParentAccountabilitiesSet();
    }

    public Stream<Accountability> getParentAccountabilityStream() {
        final Stream<Accountability> stream = super.getParentAccountabilitiesSet().stream();
        return stream.filter(a -> !a.isErased());
    }

    public Iterator<Accountability> getParentAccountabilitiesIterator() {
        return super.getParentAccountabilitiesSet().iterator();
    }

    public Stream<Accountability> getChildAccountabilityStream() {
        final Stream<Accountability> stream = super.getChildAccountabilitiesSet().stream();
        return stream.filter(a -> !a.isErased());
    }

    public Collection<Party> getAncestors() {
        final PartyResultCollection result = new PartyResultCollection(new TruePartyPredicate());
        getAncestors(result);
        return result.getResult();
    }

    public Collection<Party> getAncestors(final AccountabilityType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByAccountabilityType(type));
        getAncestors(result);
        return result.getResult();
    }

    public Collection<Party> getAncestors(final PartyType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByPartyType(type));
        getAncestors(result);
        return result.getResult();
    }

    public Collection<Unit> getAncestorUnits() {
        final PartyResultCollection result = new PartyResultCollection(new PartyByClassType(Unit.class));
        getAncestors(result);
        return result.getResult();
    }

    public Collection<Unit> getAncestorUnits(final AccountabilityType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByAccountabilityType(Unit.class, type));
        getAncestors(result);
        return result.getResult();
    }

    public Collection<Unit> getAncestorUnits(final PartyType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByPartyType(Unit.class, type));
        getAncestors(result);
        return result.getResult();
    }

    /**
     * 
     * @param result
     *            all of the ancestors (recursively)
     */
    protected void getAncestors(final PartyResultCollection result) {
        getParentAccountabilityStream().forEach(new Consumer<Accountability>() {
            @Override
            public void accept(final Accountability a) {
                result.conditionalAddParty(a.getParent(), a);
                a.getParent().getAncestors(result);

            }
        });
    }

    public boolean ancestorsInclude(final Party party, final AccountabilityType type) {
        return getParentAccountabilityStream().filter(a -> a.hasAccountabilityType(type))
                .anyMatch(a -> a.getParent().equals(party) || a.getParent().ancestorsInclude(party, type));
    }

    public Collection<Party> getDescendents() {
        final PartyResultCollection result = new PartyResultCollection(new TruePartyPredicate());
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Party> getDescendents(final AccountabilityType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByAccountabilityType(type));
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Party> getDescendents(final PartyType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByPartyType(type));
        getDescendents(result);
        return result.getResult();
    }

    public Stream<Unit> getDescendentUnitStream() {
        return getChildAccountabilityStream().map(a -> a.getChild()).filter(p -> p.isUnit()).map(p -> (Unit) p)
                .flatMap(new Function<Unit, Stream<Unit>>() {
                    @Override
                    public Stream<Unit> apply(final Unit u) {
                        return Stream.concat(Stream.of(u), u.getDescendentUnitStream());
                    }
                });
    }

    public Collection<Unit> getDescendentUnits(final AccountabilityType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByAccountabilityType(Unit.class, type));
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Unit> getDescendentUnits(final PartyType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByPartyType(Unit.class, type));
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Person> getDescendentPersons() {
        final PartyResultCollection result = new PartyResultCollection(new PartyByClassType(Person.class));
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Person> getDescendentPersons(final AccountabilityType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByAccountabilityType(Person.class, type));
        getDescendents(result);
        return result.getResult();
    }

    public Collection<Person> getDescendentPersons(final PartyType type) {
        final PartyResultCollection result = new PartyResultCollection(new PartyByPartyType(Person.class, type));
        getDescendents(result);
        return result.getResult();
    }

    protected void getDescendents(final PartyResultCollection result) {
        getChildAccountabilityStream().forEach(new Consumer<Accountability>() {
            @Override
            public void accept(final Accountability a) {
                result.conditionalAddParty(a.getChild(), a);
                a.getChild().getDescendents(result);

            }
        });
    }

    public Stream<Party> getSiblingStream() {
        return getParentAccountabilityStream().map(a -> a.getParent()).flatMap(p -> p.getChildAccountabilityStream())
                .map(a -> a.getChild()).filter(p -> p != this);
    }

    public boolean isUnit() {
        return false;
    }

    public boolean isPerson() {
        return false;
    }

    @Atomic
    public void delete() {
        canDelete();
        disconnect();
        deleteDomainObject();
    }

    protected void canDelete() {
        if (!getChildAccountabilitiesSet().isEmpty()) {
            throw new OrganizationDomainException("error.Party.delete.has.child.accountabilities");
        }
    }

    protected void disconnect() {
        super.getParentAccountabilitiesSet().forEach(a -> a.delete(null));
        getPartyTypesSet().clear();
        setMyOrg(null);
    }

    /**
     * 
     * @param parent parent
     * @param type type
     * @param begin begin
     * @param end end
     * @param justification an information justification/reason for the accountability change, or null if there is none, or none
     *            is provided
     * @return Accountability
     */
    @Atomic
    public Accountability addParent(final Party parent, final AccountabilityType type, final LocalDate begin, final LocalDate end,
            String justification) {
        return Accountability.create(parent, this, type, begin, end, justification);
    }

    /**
     * 
     * @param child child
     * @param type type
     * @param begin begin
     * @param end end
     * @param justification an information justification/reason for the change of accountability, or null if there is none, or
     *            none is provided
     * @return Accountability
     */
    @Atomic
    public Accountability addChild(final Party child, final AccountabilityType type, final LocalDate begin, final LocalDate end,
            String justification) {
        Accountability intersectingAccountability = getIntersectingChildAccountability(child, type, begin, end);
        if (intersectingAccountability != null) {
            if (begin == null || (begin != null && intersectingAccountability.getBeginDate() != null
                    && begin.isBefore(intersectingAccountability.getBeginDate()))) {
                intersectingAccountability.setBeginDate(begin);
            }
            if (end == null || (end != null && intersectingAccountability.getEndDate() != null
                    && end.isAfter(intersectingAccountability.getEndDate()))) {
                intersectingAccountability.editDates(intersectingAccountability.getBeginDate(), end, null);
            }
            return intersectingAccountability;
        }

        return Accountability.create(this, child, type, begin, end, justification);
    }

    public boolean hasAnyIntersectingChildAccountability(final Party child, final AccountabilityType type, final LocalDate begin,
            final LocalDate end) {
        return getChildAccountabilityStream()
                .anyMatch(a -> a.getChild() == child && a.getAccountabilityType() == type && a.intersects(begin, end));
    }

    boolean hasAnyIntersectingChildAccountability(final Party child, final AccountabilityType type, final LocalDate begin,
            final LocalDate end, final Accountability exluding) {
        return getChildAccountabilityStream().anyMatch(
                a -> exluding != a && a.getChild() == child && a.getAccountabilityType() == type && a.intersects(begin, end));
    }

    private Accountability getIntersectingChildAccountability(final Party child, final AccountabilityType type,
            final LocalDate begin, final LocalDate end) {
        Accountability intersectingAccountability = null;
        for (final Accountability accountability : getChildAccountabilitiesSet()) {
            if (accountability.getChild() == child && accountability.getAccountabilityType() == type
                    && accountability.intersects(begin, end)) {
                if (intersectingAccountability != null) {
                    throw new OrganizationDomainException("error.Party.multiple.intersecting.accountabilities");
                }
                intersectingAccountability = accountability;
            }
        }
        return intersectingAccountability;
    }

    @Atomic
    public void removeParent(final Accountability accountability) {
        if (getParentAccountabilitiesSet().contains(accountability)) {
            if (isUnit() && getParentAccountabilitiesSet().size() == 1) {
                throw new OrganizationDomainException("error.Party.cannot.remove.parent.accountability");
            }
            accountability.delete(null);
        }
    }

    @Atomic
    public void editPartyTypes(final List<PartyType> partyTypes) {
        getPartyTypesSet().retainAll(partyTypes);
        getPartyTypesSet().addAll(partyTypes);

        if (getPartyTypesSet().isEmpty()) {
            throw new OrganizationDomainException("error.Party.must.have.at.least.one.party.type");
        }
        if (!accountabilitiesStillValid()) {
            throw new OrganizationDomainException("error.Party.invalid.party.types.accountability.rules.are.not.correct");
        }

    }

    protected boolean accountabilitiesStillValid() {
        return !getParentAccountabilityStream().anyMatch(a -> !a.isValid());
    }

    public String getPartyNameWithSuffixType() {
        return ResourceBundle.getBundle("resources.OrganizationResources", I18N.getLocale())
                .getString("label." + getClass().getSimpleName().toLowerCase()) + " - " + getPartyName().getContent();
    }

    public Set<OrganizationalModel> getAllOrganizationModels() {
        final Set<OrganizationalModel> organizationModels =
                new TreeSet<OrganizationalModel>(OrganizationalModel.COMPARATORY_BY_NAME);
        addAllOrganizationModels(organizationModels, new HashSet<Party>());
        return organizationModels;
    }

    public void addAllOrganizationModels(final Set<OrganizationalModel> organizationModels, final Set<Party> processed) {
        if (!processed.contains(this)) {
            processed.add(this);
            organizationModels.addAll(getOrganizationalModelsSet());
            for (final Accountability accountability : getParentAccountabilitiesSet()) {
                final Party party = accountability.getParent();
                party.addAllOrganizationModels(organizationModels, processed);
            }
        }
    }

    public void setPartyTypes(final List<PartyType> partyTypes) {
        getPartyTypesSet().clear();
        getPartyTypesSet().addAll(partyTypes);
    }

    public boolean isAuthorizedToManage() {
        return DynamicGroup.get("managers").isMember(Authenticate.getUser());
    }

    public boolean hasChildAccountabilityIncludingAncestry(final Predicate<Accountability> accountabilityPredicate,
            final Party party) {
        return hasActiveChildAccountabilityIncludingAncestry(new HashSet<Party>(), accountabilityPredicate, party);
    }
    
    private boolean hasActiveChildAccountabilityIncludingAncestry(final Set<Party> processed,
            final Predicate<Accountability> accountabilityPredicate, final Party party) {
        if (!processed.contains(this)) {
            processed.add(this);

            if (getChildAccountabilityStream()
                    .filter(a -> a.isActiveNow() && accountabilityPredicate.test(a))
                    .map(a -> a.getChild()).anyMatch(p -> p == party)) {
                return true;
            }

            if (getParentAccountabilityStream()
                    .filter(a -> a.isActiveNow() && accountabilityPredicate.test(a))
                    .map(a -> a.getParent())
                    .anyMatch(p -> p.hasActiveChildAccountabilityIncludingAncestry(processed, accountabilityPredicate, party))) {
                return true;
            }
        }
        return false;
    }

    public static Party findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(final Set<Party> parties,
            final AccountabilityType accountabilityType, final PartyType partyType, final String acronym) {
        for (final Party party : parties) {
            final Party result =
                    party.findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(accountabilityType, partyType, acronym);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    protected Party findPartyByPartyTypeAndAcronymForAccountabilityTypeLink(final AccountabilityType accountabilityType,
            final PartyType partyType, final String acronym) {
        return null;
    }

    public String getPresentationName() {
        return getPartyName().getContent();
    }

    public boolean isTop() {
        return false;
    }

    public boolean hasActiveAncestry(final AccountabilityType accountabilityType, final LocalDate when) {
        return isTop() || hasParentWithActiveAncestry(accountabilityType, when);
    }

    public boolean hasDirectActiveAncestry(final AccountabilityType accountabilityType, final LocalDate when) {
        return getParentAccountabilityStream().anyMatch(a -> a.getAccountabilityType() == accountabilityType && a.isActive(when));
    }

    private boolean hasParentWithActiveAncestry(final AccountabilityType accountabilityType, final LocalDate when) {
        return getParentAccountabilityStream().filter(a -> a.getAccountabilityType() == accountabilityType && a.isActive(when))
                .map(a -> a.getParent()).anyMatch(p -> p.hasActiveAncestry(accountabilityType, when));
    }

    public boolean hasPartyAsAncestor(final Party party, final Set<AccountabilityType> accountabilityTypes) {
        return getParentAccountabilityStream()
                .filter(a -> accountabilityTypes.contains(a.getAccountabilityType()) && a.isActiveNow()).map(a -> a.getParent())
                .anyMatch(p -> p == party || p.hasPartyAsAncestor(party, accountabilityTypes));
    }

    public boolean hasPartyAsAncestor(final Party party, final Set<AccountabilityType> accountabilityTypes,
            final LocalDate when) {
        return getParentAccountabilityStream()
                .filter(a -> accountabilityTypes.contains(a.getAccountabilityType()) && a.isActive(when)).map(a -> a.getParent())
                .anyMatch(p -> p == party || p.hasPartyAsAncestor(party, accountabilityTypes));
    }

}
