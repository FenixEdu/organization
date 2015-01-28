/*
 * @(#)CountrySubdivisionLevelName.java
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

import module.geography.domain.exception.GeographyDomainException;

import org.fenixedu.commons.i18n.LocalizedString;

/**
 * Each {@link CountrySubdivision} has a matching level name. Each country has
 * it's own names no a {@link Country} object should have a list of these.
 * 
 * 
 * @author João Antunes
 * @author Pedro Santos
 * 
 */
public class CountrySubdivisionLevelName extends CountrySubdivisionLevelName_Base {

    public CountrySubdivisionLevelName(Integer level, LocalizedString name) {
        super();
        setLevel(level);
        setName(name);
    }

    /**
     * Warning, it doesn't account for the fact that instances of these levels,
     * i.e. CountrySubdivions actually exist
     * 
     * @return true if it isn't connected to a country, false otherwise, not it
     *         doesn't take into account the existing CountrySubdivisions
     */
    public boolean canBeDeleted() {
        if (this.getCountry() != null) {
            throw new GeographyDomainException("error.Party.delete.has.child.accountabilities");
        }
        return true;
    }

    public void delete() {
        if (canBeDeleted()) {
            this.deleteDomainObject();
        }
    }

}
