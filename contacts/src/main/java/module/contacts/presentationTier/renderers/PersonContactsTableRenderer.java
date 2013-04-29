/*
 * @(#)PersonContactsTableRenderer.java
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
package module.contacts.presentationTier.renderers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import module.contacts.domain.EmailAddress;
import module.contacts.domain.PartyContact;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.PhysicalAddress;
import module.contacts.domain.WebAddress;
import module.organization.domain.Person;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.fenixWebFramework.renderers.OutputRenderer;
import pt.ist.fenixWebFramework.renderers.components.Face;
import pt.ist.fenixWebFramework.renderers.components.HtmlBlockContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlInlineContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlLink;
import pt.ist.fenixWebFramework.renderers.components.HtmlTable;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell.CellType;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableRow;
import pt.ist.fenixWebFramework.renderers.components.HtmlText;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixWebFramework.renderers.model.MetaObject;
import pt.ist.fenixWebFramework.renderers.model.MetaObjectFactory;
import pt.ist.fenixWebFramework.renderers.utils.RenderKit;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

/**
 * Renders the contacts as a table suitable to be the output of the find persons
 * 
 * Developers: see searchContacts.jsp for an example of output
 * 
 * @author João Antunes
 * 
 */
public class PersonContactsTableRenderer extends OutputRenderer {

    private String contactTableClasses;

    private String canEdit;

    private String label;

    private String bundle;

    private String editLinkKey;

    private String editLink;

    private String schema;

    private String headerClasses;

    private String topLineClasses;

    private String personNameStyle;

    private String usernameClasses;

    private String usernameStyle;

    private String editLinkContainerClasses;

    private String contactTypeClasses;

    /*
     * (non-Javadoc)
     * 
     * @see
     * pt.ist.fenixWebFramework.renderers.Renderer#getLayout(java.lang.Object,
     * java.lang.Class)
     */
    @Override
    protected Layout getLayout(Object unfiltered, Class type) {
        return new Layout() {

            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public HtmlComponent createComponent(Object unfiltered, Class type) {

                HtmlBlockContainer topDiv = new HtmlBlockContainer();
                // no use in setting the classes here as they are set in the
                // render
                // topDiv.setClasses("infobox1 mvert15px");
                Person person = ((Person) unfiltered);
                MetaObject personMO = MetaObjectFactory.createObject(unfiltered, RenderKit.getInstance().findSchema(getSchema()));
                Collection<PartyContact> partyContacts = ((Person) unfiltered).getPartyContactsSet();
                List<MetaObject> contacts = getFilteredContacts(partyContacts);
                // construct the person's header on the table
                HtmlBlockContainer header = new HtmlBlockContainer();
                header.setClasses(getHeaderClasses());
                // header.setClasses("header");
                topDiv.addChild(header);
                HtmlBlockContainer topLine = new HtmlBlockContainer();
                topLine.setClasses(getTopLineClasses());
                // topLine.setClasses("col2-1 aleft");
                header.addChild(topLine);
                // the person's name
                HtmlText personName = new HtmlText(person.getName());
                personName.setStyle(getPersonNameStyle());
                // personName.setStyle("display: inline;");
                personName.setFace(Face.H3);
                topLine.addChild(personName);

                // the username
                // TODO add some classes to make this inline
                HtmlText username = new HtmlText("(" + person.getUser().getUsername() + ")");
                username.setClasses(getUsernameClasses());
                // username.setClasses("weight-normal");
                username.setStyle(getUsernameStyle());
                // username.setStyle("font-size: 11px;");
                topLine.addChild(username);

                // the edit link
                if (getCanEdit().equalsIgnoreCase("true")) {
                    HtmlBlockContainer editLinkContainer = new HtmlBlockContainer();
                    editLinkContainer.setClasses(getEditLinkContainerClasses());
                    // editLinkContainer.setClasses("col2-2 aright");
                    HtmlLink editLink = new HtmlLink();
                    editLink.setUrl(getEditLink());
                    editLink.setText(RenderUtils.getResourceString(getBundle(), getEditLinkKey()));
                    editLinkContainer.addChild(editLink);
                    header.addChild(editLinkContainer);
                }

                // the clear div
                HtmlBlockContainer clearContainer = new HtmlBlockContainer();
                clearContainer.setClasses("clear");
                header.addChild(clearContainer);

                // if we have no more contact info, we should return now
                if (contacts.isEmpty()) {
                    return topDiv;
                }

                HtmlBlockContainer contactsContentContainer = new HtmlBlockContainer();
                // if not, let's fill the container with the contact information
                HtmlTable contactsTable = new HtmlTable();
                contactsTable.setClasses(getContactTableClasses());
                // the container of all the labels, needed so that we only use a
                // label per group of contacts
                HashMap<String, HtmlTableRow> rowsByHeadersMap = new HashMap<String, HtmlTableRow>();
                for (MetaObject contactMeta : contacts) {
                    // if the row with the label to which the contact belongs
                    // doesn't exist, let's create it
                    boolean firstElement = false;
                    HtmlTableRow row =
                            rowsByHeadersMap.get(RenderUtils.getResourceString(getBundle(), contactMeta.getObject().getClass()
                                    .getName()));
                    if (row == null) {
                        // the table header with the label
                        firstElement = true;
                        row = contactsTable.createRow();
                        HtmlTableCell tableHeader = row.createCell(CellType.HEADER);
                        // the label will depend on the class!
                        // convention: the class's absolute name (including the
                        // package) that implements the PartyContact must be on
                        // the
                        // resource file so that the label is localized and
                        // displayed correctly
                        String headerLabel =
                                RenderUtils.getResourceString(getBundle(), contactMeta.getObject().getClass().getName());
                        tableHeader.setText(headerLabel + ":");
                        rowsByHeadersMap.put(headerLabel, row);
                    }
                    HtmlTableCell value;
                    // it must only contain the header for it to be created
                    if (row.getCells().size() == 1) {
                        value = row.createCell(CellType.DATA);
                        value.setBody(getValue((PartyContact) contactMeta.getObject(), firstElement));
                    } else {
                        // if we have two elements, then we need to retrieve the
                        // DATA cellType and append the new stuff there we will
                        // also add the delimiter to it
                        for (HtmlTableCell cell : row.getCells()) {
                            if (cell.getType().equals(CellType.DATA)) {
                                HtmlInlineContainer dataCell = (HtmlInlineContainer) cell.getBody();
                                /*
                                 * please ignore the following comments :) only
                                 * there because the code might be useful to
                                 * access the previous text element
                                 */
                                // let's get the type span which is inside this
                                // span to add the character
                                // (which is the last element!!)
                                // HtmlInlineContainer lastSpan =
                                // (HtmlInlineContainer)
                                // dataCell.getChildren().get(
                                // dataCell.getChildren().size() - 1);
                                // //the last span either is the typeOfContact
                                // span or a composite one
                                // HtmlInlineContainer typeOfContactSpan;
                                // if (lastSpan.getChildren().size() == 1)
                                // typeOfContactSpan = lastSpan;
                                // else
                                // typeOfContactSpan = (HtmlInlineContainer)
                                // lastSpan.getChildren().get(
                                // lastSpan.getChildren().size() - 1);
                                // //get the last element
                                // HtmlText typeText = (HtmlText)
                                // typeOfContactSpan.getChildren().get(
                                // typeOfContactSpan.getChildren().size() - 1);
                                // typeText.setText(typeText.getText() + ",");

                                dataCell.addChild(getValue((PartyContact) contactMeta.getObject(), firstElement));
                                cell.setBody(dataCell);
                            }

                        }
                    }
                }
                contactsContentContainer.addChild(contactsTable);
                topDiv.addChild(contactsContentContainer);
                return topDiv;
            }
        };
    }

    /**
     * Renders in a specific way the value of the contact, depending on its
     * class. This method has to change when a new PartyContact class is made so
     * that it will render it in a specific way, otherwise it will be just
     * plaintext
     * 
     * @param contact
     *            the PartyContact to render the value of
     * @param firstElement
     *            if this is the first element on the table, this is important
     *            because if it isn't a virgul should be used as a delimiter for
     *            the values
     * @return an HtmlComponent that has the value of the given PartyContact
     */
    private HtmlComponent getValue(PartyContact contact, boolean firstElement) {
        HtmlInlineContainer span = new HtmlInlineContainer();
        span.setIndented(false);
        // if this isn't the first element, let's the add the initial virgul and
        // space ', '
        if (!firstElement) {
            span.addChild(new HtmlText(", "));
        }
        HtmlLink link = new HtmlLink();
        link.setIndented(false);
        link.setClasses("secondaryLink");
        link.setModuleRelative(false);
        link.setContextRelative(false);
        if (contact instanceof Phone) {
            if (((Phone) contact).getPhoneType().equals(PhoneType.VOIP_SIP)) {
                link.setUrl("sip:" + contact.getDescription());
            } else {
                link.setUrl("tel:" + contact.getDescription());
            }
            link.setText(contact.getDescription());
            span.addChild(link);
        } else if (contact instanceof EmailAddress) {
            link.setUrl("mailto:" + contact.getDescription());
            link.setBody(new HtmlText(contact.getDescription()));
            span.addChild(link);
        } else if (contact instanceof WebAddress) {
            link.setUrl(((WebAddress) contact).getUrl());
            link.setBody(new HtmlText(((WebAddress) contact).getUrl()));
            span.addChild(link);
        } else if (contact instanceof PhysicalAddress) {
            HtmlText addressText = new HtmlText(contact.getDescription());
            addressText.setFace(Face.ADDRESS);
        }

        // and at the end, let's add the type in a lighter span, localized!
        HtmlInlineContainer contactType = new HtmlInlineContainer();
        contactType.setClasses(getContactTypeClasses());
        // contactType.setClasses("lighter");
        contactType.addChild(new HtmlText(" (" + contact.getType().getLocalizedName() + ")"));
        span.addChild(contactType);
        return span;
    }

    protected List<MetaObject> getFilteredContacts(Collection<PartyContact> unfilteredContacts) {

        User currentUser = UserView.getCurrentUser();
        List<MetaObject> contacts = new ArrayList<MetaObject>();

        if (currentUser == null) {
            return contacts;
        }
        for (PartyContact partyContact : unfilteredContacts) {
            if (partyContact.isVisibleTo(currentUser)) {
                contacts.add(MetaObjectFactory.createObject(partyContact, RenderKit.getInstance().findSchema(getSchema())));
            }

        }
        return contacts;
    }

    /**
     * @return the editLinkKey
     */
    public String getEditLinkKey() {
        return editLinkKey;
    }

    /**
     * @param editLinkKey
     *            the editLinkKey to set
     */
    public void setEditLinkKey(String editLinkKey) {
        this.editLinkKey = editLinkKey;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getBundle() {
        return bundle;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    public String getCanEdit() {
        return canEdit;
    }

    public void setContactTableClasses(String contactTableClasses) {
        this.contactTableClasses = contactTableClasses;
    }

    public String getContactTableClasses() {
        return contactTableClasses;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public void setEditLink(String editLink) {
        this.editLink = editLink;
    }

    public String getEditLink() {
        return editLink;
    }

    public void setHeaderClasses(String headerClasses) {
        this.headerClasses = headerClasses;
    }

    public String getHeaderClasses() {
        return headerClasses;
    }

    public void setTopLineClasses(String topLineClasses) {
        this.topLineClasses = topLineClasses;
    }

    public String getTopLineClasses() {
        return topLineClasses;
    }

    public void setPersonNameStyle(String personNameStyle) {
        this.personNameStyle = personNameStyle;
    }

    public String getPersonNameStyle() {
        return personNameStyle;
    }

    public void setUsernameClasses(String usernameClasses) {
        this.usernameClasses = usernameClasses;
    }

    public String getUsernameClasses() {
        return usernameClasses;
    }

    public void setUsernameStyle(String usernameStyle) {
        this.usernameStyle = usernameStyle;
    }

    public String getUsernameStyle() {
        return usernameStyle;
    }

    /**
     * @return the editLinkContainerClasses
     */
    public String getEditLinkContainerClasses() {
        return editLinkContainerClasses;
    }

    /**
     * @param editLinkContainerClasses
     *            the editLinkContainerClasses to set
     */
    public void setEditLinkContainerClasses(String editLinkContainerClasses) {
        this.editLinkContainerClasses = editLinkContainerClasses;
    }

    /**
     * @return the contactTypeClasses
     */
    public String getContactTypeClasses() {
        return contactTypeClasses;
    }

    /**
     * @param contactTypeClasses
     *            the contactTypeClasses to set
     */
    public void setContactTypeClasses(String contactTypeClasses) {
        this.contactTypeClasses = contactTypeClasses;
    }

}
