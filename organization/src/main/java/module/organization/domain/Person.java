/*
 * @(#)Person.java
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

import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.domain.UserProfile;
import org.fenixedu.commons.StringNormalizer;
import org.fenixedu.commons.i18n.I18N;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;

/**
 *
 * @author João Neves
 * @author João Antunes
 * @author Pedro Santos
 * @author João Figueiredo
 * @author Anil Kassamali
 * @author Sérgio Silva
 * @author Paulo Abrantes
 * @author Luis Cruz
 *
 */
public class Person extends Person_Base {

    static public class PersonBean extends PartyBean {
        private static final long serialVersionUID = -7516282978280402225L;

        private Person person;
        private String name;

        public PersonBean() {
            setBegin(new LocalDate());
        }

        public PersonBean(final Person person) {
            this();

            setPerson(person);
            setName(person.getName());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person getPerson() {
            return this.person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        @Override
        public Party getParty() {
            return getPerson();
        }

        public void edit() {
            getPerson().edit(this);
        }

        @Override
        public void addParent() {
            getPerson().addParent(getParent(), getAccountabilityType(), getBegin(), getEnd(), null);
        }

    }

    public Person(LocalizedString partyName, PartyType partyType) {
        super();
        setMyOrgFromPerson(Bennu.getInstance());
        setPartyName(partyName);
        addPartyTypes(partyType);
    }

    @Override
    final public boolean isPerson() {
        return true;
    }

    @Override
    protected void disconnect() {
        setUser(null);
        setMyOrgFromPerson(null);
        super.disconnect();
    }

    @Atomic
    public void edit(final PersonBean bean) {
        setPartyName(getPartyName().with(I18N.getLocale(), bean.getName()));
    }

    public String getName() {
        final User user = getUser();
        final UserProfile profile = user == null ? null : user.getProfile();
        return profile == null ? getPartyName().getContent() : profile.getFullName();
    }

    public String getFirstAndLastName() {
        final User user = getUser();
        final UserProfile profile = user == null ? null : user.getProfile();
        return profile == null ? "" : profile.getDisplayName();
    }

    @Atomic
    static public Person create(final LocalizedString partyName, final PartyType partyType) {
        return new Person(partyName, partyType);
    }

    @Atomic
    static public Person create(final PersonBean bean) {
        return create(new LocalizedString(I18N.getLocale(), bean.getName()), getPartyTypeInstance());
    }

    static public Person readByPartyName(LocalizedString partyName) {
        final Stream<Party> stream = Bennu.getInstance().getPartiesSet().stream();
        return stream.filter(p -> !p.isUnit() && p.getPartyName().equals(partyName)).map(p -> (Person) p).findAny().orElse(null);
    }

    static public PartyType getPartyTypeInstance() {
        final String type = Person.class.getName();
        PartyType partyType = PartyType.readBy(type);
        if (partyType == null) {
            synchronized (Person.class) {
                partyType = PartyType.readBy(Person.class.getName());
                if (partyType == null) {
                    partyType = PartyType.create(type, new LocalizedString(I18N.getLocale(), "Pessoa"));
                }
            }
        }
        return partyType;
    }

    public static Stream<Person> searchPersonStream(String value) {
        final String trimmedValue = value.trim();
        final String[] input = trimmedValue.split(" ");
        for (int i = 0; i < input.length; i++) {
            input[i] = StringNormalizer.normalize(input[i]);
        }

        final Stream<Person> stream = Bennu.getInstance().getPersonsSet().stream();
        return stream.filter(p -> p.isPerson()).map(p -> (Person) p)
                .filter(p -> hasMatch(input, StringNormalizer.normalize(p.getPartyName().getContent())))
                .sorted(Party.COMPARATOR_BY_NAME);
    }

    private static boolean hasMatch(final String[] input, final String unitNameParts) {
        for (final String namePart : input) {
            if (unitNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getPresentationName() {
        final User user = getUser();
        if (user == null) {
            return getName();
        }
        final UserProfile profile = user.getProfile();
        return profile.getDisplayName() + "(" + user.getUsername() + ")";
    }

}
