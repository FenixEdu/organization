/*
 * @(#)KindOfPartyContact.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.presentationTier;

import java.util.ResourceBundle;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.EmailAddress;
import module.contacts.domain.PhysicalAddress;
import module.contacts.domain.WebAddress;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.ist.fenixframework.plugins.luceneIndexing.IndexableField;
import pt.utl.ist.fenix.tools.util.i18n.Language;

/**
 * Enumerate that contains all of the extensions of the PartyContact class.
 * 
 * Also used for the lucene index
 * 
 * @author João Antunes
 * 
 */
public enum KindOfPartyContact implements IPresentableEnum, IndexableField {
    PHONE,

    WEB_ADDRESS,

    PHYSICAL_ADDRESS,

    EMAIL_ADDRESS;

    /**
     * Fields used for the lucene search in the {@link ContactsConfigurator} they were extracted here because of the incredibly
     * annoying
     * java.lang.ClassFormatError: Invalid length 65527 in LocalVariableTable
     * error
     */
    public static final String PHONE_SEARCH = "Phonesearch";
    public static final String PHYSICAL_SEARCH = "Physicalsearch";
    public static final String EMAIL_SEARCH = "Emailsearch";
    public static final String WEB_SEARCH = "Websearch";
    public static final String PERSON_SEARCH = "UsernameSearch";

    /**
     * Making this enum ActionBean friendly
     * 
     * @return the result of the name() function
     */
    public String getName() {
        return name();
    }

    @Override
    public String getLocalizedName() {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.ContactsResources", Language.getLocale());
        return resourceBundle.getString(KindOfPartyContact.class.getName() + "." + name());
    }

    @Override
    public String getFieldName() {
        switch (this) {
        case PHONE:
            throw new IllegalArgumentException(
                    "Invalid enum used! when using a Phone please use the phonetype for the indexable field");
        case WEB_ADDRESS:
            return WebAddress.class.getName();
        case EMAIL_ADDRESS:
            return EmailAddress.class.getName();
        case PHYSICAL_ADDRESS:
            return PhysicalAddress.class.getName();

        }
        return null;
    }

}
