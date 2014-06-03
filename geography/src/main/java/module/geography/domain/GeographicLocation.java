/*
 * @(#)GeographicLocation.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: Pedro Santos
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module.
 *
 *   The Geography Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Geography Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Geography Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.geography.domain;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;

import module.geography.domain.exception.GeographyDomainException;
import module.organization.domain.AccountabilityType;
import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.commons.i18n.LocalizedString;

/**
 * Abstract location concept. If you can put it in a "where" sentence it should
 * derive from this.
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public abstract class GeographicLocation extends GeographicLocation_Base implements GeographicConstants {

    public static final Comparator<GeographicLocation> COMPARATOR_BY_NAME = new Comparator<GeographicLocation>() {
        @Override
        public int compare(final GeographicLocation location1, GeographicLocation location2) {
            final String name1 = location1.getName().getContent();
            final String name2 = location2.getName().getContent();
            final int c = Collator.getInstance().compare(name1, name2);
            if (c == 0) {
                final String acronym1 = location1.getAcronym();
                final String acronym2 = location2.getAcronym();
                if (acronym1 == null || acronym2 == null) {
                    return location2.hashCode() - location1.hashCode();
                }
                final int a = Collator.getInstance().compare(acronym1, acronym2);
                return a == 0 ? location2.hashCode() - location1.hashCode() : a;
            }
            return c;
        }
    };

    public GeographicLocation() {
        super();
    }

    public LocalizedString getName() {
        return getUnit().getPartyName();
    }

    /**
     * 
     * @return the {@link Country} that is a parent or event this
     *         GeographicLocation or null if it has none
     */
    public Country getCountry() {
        if (this instanceof Country) {
            return (Country) this;
        }
        if (this instanceof CountrySubdivision || this instanceof PostalExtension) {
            return ((CountrySubdivision) this).getCountry();
        }
        // recursively go and fetch the Country if it is indeed available
        if (getParentLocation() != null) {
            return getParentLocation().getCountry();
        } else {
            return null;
        }

    }

    public String getAcronym() {
        return getUnit().getAcronym();
    }

    public abstract LocalizedString getType();

    protected GeographicLocation getParentLocation() {
        Collection<Unit> parents = getUnit().getParentUnits(AccountabilityType.readBy(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME));
        if (parents.size() != 1) {
            throw new GeographyDomainException("error.geography.invalid-organizational-structure");
        }
        return parents.iterator().next().getGeographicLocation();
    }

    protected Collection<Unit> getChildUnits() {
        return getUnit().getChildUnits(AccountabilityType.readBy(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME));
    }

    public static AccountabilityType getOrCreateAccountabilityType() {
        AccountabilityType geographic = null;
        for (AccountabilityType accountability : Bennu.getInstance().getAccountabilityTypesSet()) {
            if (accountability.getType().equals(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME)) {
                geographic = accountability;
            }
        }
        if (geographic == null) {
            geographic =
                    AccountabilityType.create(new AccountabilityTypeBean(GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME,
                            new LocalizedString().with(new Locale("pt"), "Geográfico").with(Locale.ENGLISH,
                                    GEOGRAPHIC_ACCOUNTABILITY_TYPE_NAME)));
        }
        return geographic;
    }

    protected static PartyType getPartyType(String pt, String en) {
        for (PartyType partyType : Bennu.getInstance().getPartyTypesSet()) {
            if (partyType.getType().equals(en)) {
                return partyType;
            }
        }
        return new PartyType(en, new LocalizedString().with(new Locale("pt"), pt).with(Locale.ENGLISH, en));
    }
}
