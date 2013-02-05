/*
 * @(#)InterfaceCreationAction.java
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
package module.contacts.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.domain.contents.ActionNode;
import pt.ist.bennu.core.domain.contents.Node;
import pt.ist.bennu.core.domain.groups.AnyoneGroup;
import pt.ist.bennu.core.domain.groups.IntersectionGroup;
import pt.ist.bennu.core.domain.groups.NegationGroup;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.domain.groups.Role;
import pt.ist.bennu.core.domain.groups.UserGroup;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;

/**
 * 
 * @author João Antunes
 * 
 */
@Mapping(path = "/contactsInterfaceCreationAction")
public class InterfaceCreationAction extends ContextBaseAction {

    @CreateNodeAction(bundle = "CONTACTS_RESOURCES", key = "add.node.contacts.interface", groupKey = "label.module.contacts")
    public final ActionForward createAnnouncementNodes(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
	final Node node = getDomainObject(request, "parentOfNodesToManageId");

	UserGroup userGroup = UserGroup.getInstance();
	
	PersistentGroup managerGroup = Role.getRole(RoleType.MANAGER);
	
	NegationGroup usersNotManagers = NegationGroup.createNegationGroup(managerGroup);
	
	ActionNode homeNode = ActionNode.createActionNode(virtualHost, node, "/contacts", "frontPage",
		"resources.ContactsResources", "link.sideBar.contacts", userGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/contacts", "configContactsModule", "resources.ContactsResources",
		"link.sideBar.contacts.configureContacts", managerGroup);

	// simple search for all non manager users:
	ActionNode.createActionNode(virtualHost, homeNode, "/contacts", "searchContacts", "resources.ContactsResources",
		"link.sideBar.contacts.searchContacts", usersNotManagers);

	// search/edit for all manager users:
	ActionNode.createActionNode(virtualHost, homeNode, "/contacts", "searchContacts", "resources.ContactsResources",
		"link.sideBar.contacts.searchSlashEditContacts", managerGroup);

	ActionNode.createActionNode(virtualHost, homeNode, "/contacts", "editContacts", "resources.ContactsResources",
		"link.sideBar.contacts.editOwnContacts", userGroup);
	// ActionNode.createActionNode(virtualHost, homeNode, "/mobility",
	// "frontPage", "resources.MobilityResources",
	// "link.sideBar.mobility.frontPage", managementsGroup);

	return forwardToMuneConfiguration(request, virtualHost, node);
    }
}
