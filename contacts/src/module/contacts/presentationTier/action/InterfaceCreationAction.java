/**
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
import myorg.domain.RoleType;
import myorg.domain.VirtualHost;
import myorg.domain.contents.ActionNode;
import myorg.domain.contents.Node;
import myorg.domain.groups.AnyoneGroup;
import myorg.domain.groups.IntersectionGroup;
import myorg.domain.groups.NegationGroup;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.groups.Role;
import myorg.domain.groups.UserGroup;
import myorg.presentationTier.actions.ContextBaseAction;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
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
