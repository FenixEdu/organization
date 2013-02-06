/*
 * @(#)PersonalInformationManagementAction.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Personal Information Module.
 *
 *   The Personal Information Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Personal Information Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Personal Information Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.personalinformation.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.presentationTier.actions.PartyViewHook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.domain.contents.ActionNode;
import pt.ist.bennu.core.domain.contents.Node;
import pt.ist.bennu.core.domain.groups.Role;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/personalInformation")
/**
 * 
 * @author João Figueiredo
 * @author Luis Cruz
 * 
 */
public class PersonalInformationManagementAction extends ContextBaseAction {

    public static class PersonalInformationView extends PartyViewHook {

        @Override
        public String hook(final HttpServletRequest request, final OrganizationalModel organizationalModel, final Party party) {
            return "/personalInformation/organizationModelView.jsp";
        }

        @Override
        public String getViewName() {
            return "02_personalInformation";
        }

        @Override
        public String getPresentationName() {
            return BundleUtil.getStringFromResourceBundle("resources.PersonalInformationResources",
                    "label.personalInformationView");
        }

        @Override
        public boolean isAvailableFor(final Party party) {
            return party != null && party.isPerson();
        }
    }

    @CreateNodeAction(bundle = "PERSONAL_INFORMATION_RESOURCES", key = "add.node.manage.personal.information",
            groupKey = "label.module.personal.information")
    public final ActionForward createOrganizationNode(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final VirtualHost virtualHost = getDomainObject(request, "virtualHostToManageId");
        final Node parentOfNodes = getDomainObject(request, "parentOfNodesToManageId");

        final ActionNode topActionNode =
                ActionNode.createActionNode(virtualHost, parentOfNodes, "/personalInformation", "intro",
                        "resources.PersonalInformationResources", "label.module.personal.information",
                        Role.getRole(RoleType.MANAGER));

        ActionNode.createActionNode(virtualHost, topActionNode, "/personalInformation", "manage",
                "resources.PersonalInformationResources", "label.manage.information", Role.getRole(RoleType.MANAGER));

        return forwardToMuneConfiguration(request, virtualHost, topActionNode);
    }

    public ActionForward intro(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        return forward(request, "/personalInformation/intro.jsp");
    }

    public ActionForward manage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        return forward(request, "/personalInformation/manage.jsp");
    }

}
