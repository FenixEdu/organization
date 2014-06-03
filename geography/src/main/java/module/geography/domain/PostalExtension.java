/*
 * @(#)PostalExtension.java
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

import java.util.Locale;

import module.organization.domain.Unit;

import org.apache.commons.lang.StringUtils;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.LocalDate;

/**
 * Special kind of {@link CountrySubdivision} specific for Portugal. It covers a
 * small area inside a parish that shares the same 7 digit postal code. It's
 * commonly just a street name.
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public class PostalExtension extends PostalExtension_Base {
    public PostalExtension(CountrySubdivision parent, String acronym, String street, String postalCode, String postalBranch) {
        super();
        String name = StringUtils.isNotBlank(street) ? street : postalBranch;
        setUnit(Unit.create(parent.getUnit(), new LocalizedString().with(new Locale("pt"), name).with(Locale.ENGLISH, name),
                acronym, getPartyType("Subdivisão de País", COUNTRY_SUBDIVISION_PARTYTYPE_NAME), getOrCreateAccountabilityType(),
                new LocalDate(), null));
        setLevel(parent.getLevel() + 1);
        setCode(postalCode);
        setStreetName(street);
        setPostalCode(postalCode);
        setPostalBranch(postalBranch);
    }

    @Override
    protected String getExtendedName() {
        return "[" + getCode() + "-" + (getStreetName() != null ? getStreetName() : "") + ", " + getName().getContent() + "]";
    }

    @Override
    public String toString() {
        return getParentSubdivision().toString() + " " + getExtendedName();
    }
}
