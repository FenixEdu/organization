package module.organization.presentationTier.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.ConnectionRule;
import module.organization.domain.ConnectionRuleAccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organization.domain.UnitBean;
import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import module.organization.domain.ConnectionRule.ConnectionRuleBean;
import module.organization.domain.PartyType.PartyTypeBean;
import myorg.domain.RoleType;
import myorg.domain.contents.ActionNode;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.Role;
import myorg.presentationTier.actions.ContextBaseAction;
import myorg.presentationTier.forms.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.servlets.functionalities.CreateNodeAction;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/organization", formBeanClass = OrganizationManagementAction.OrganizationForm.class)
public class OrganizationManagementAction extends ContextBaseAction {

    static public class OrganizationForm extends BaseForm {
	private static final long serialVersionUID = 4469811183847905665L;

	private String accountabilityTypeClassName;

	public String getAccountabilityTypeClassName() {
	    return accountabilityTypeClassName;
	}

	public void setAccountabilityTypeClassName(String accountabilityTypeClassName) {
	    this.accountabilityTypeClassName = accountabilityTypeClassName;
	}
    }

    @CreateNodeAction(bundle = "ORGANIZATION_RESOURCES", key = "add.node.manage.organization", groupKey = "label.module.organization")
    public final ActionForward createOrganizationNode(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	ActionNode.createActionNode("/organization", "showOptions", "resources.OrganizationResources",
		"label.manage.organization", Role.getRole(RoleType.MANAGER));
	return showOptions(mapping, form, request, response);
    }

    public ActionForward showOptions(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	return forward(request, "/showOptions.jsp");
    }

    public ActionForward viewPartyTypes(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	request.setAttribute("partyTypes", getMyOrg().getPartyTypes());
	return forward(request, "/viewPartyTypes.jsp");
    }

    public ActionForward prepareCreatePartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	request.setAttribute("partyTypeBean", new PartyTypeBean());
	return forward(request, "/createPartyType.jsp");
    }

    public ActionForward createPartyType(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final PartyTypeBean bean = getRenderedObject("partyTypeBean");
	try {
	    PartyType.create(bean);
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("partyTypeBean", bean);
	    return forward(request, "/createPartyType.jsp");
	}
	return viewPartyTypes(mapping, form, request, response);
    }

    public ActionForward prepareEditPartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	request.setAttribute("partyTypeBean", new PartyTypeBean((PartyType) getDomainObject(request, "partyTypeOid")));
	return forward(request, "/editPartyType.jsp");
    }

    public ActionForward editPartyType(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final PartyTypeBean bean = getRenderedObject("partyTypeBean");
	try {
	    bean.edit();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("partyTypeBean", bean);
	    return forward(request, "/editPartyType.jsp");
	}

	return viewPartyTypes(mapping, form, request, response);
    }

    public ActionForward deletePartyType(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	((PartyType) getDomainObject(request, "partyTypeOid")).delete();
	return viewPartyTypes(mapping, form, request, response);
    }

    public ActionForward viewAccountabilityTypes(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	request.setAttribute("accountabilityTypes", getMyOrg().getAccountabilityTypes());
	return forward(request, "/viewAccountabilityTypes.jsp");
    }

    public ActionForward selectAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	return forward(request, "/selectAccountabilityType.jsp");
    }

    public ActionForward prepareCreateAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final OrganizationForm organizationForm = (OrganizationForm) form;
	final Class<?> clazz = Class.forName(organizationForm.getAccountabilityTypeClassName());
	request.setAttribute("accountabilityTypeBean", clazz.newInstance());
	return forward(request, "/createAccountabilityType.jsp");
    }

    public ActionForward createAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityTypeBean bean = getRenderedObject("accountabilityTypeBean");
	try {
	    bean.create();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("accountabilityTypeBean", bean);
	    return forward(request, "/createAccountabilityType.jsp");
	}
	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public ActionForward prepareEditAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("accountabilityTypeBean", type.buildBean());
	return forward(request, "/editAccountabilityType.jsp");
    }

    public ActionForward editAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityTypeBean bean = getRenderedObject("accountabilityTypeBean");
	try {
	    bean.edit();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("accountabilityTypeBean", bean);
	    return forward(request, "/editAccountabilityType.jsp");
	}

	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public ActionForward deleteAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	((AccountabilityType) getDomainObject(request, "accountabilityTypeOid")).delete();
	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public ActionForward viewConnectionRules(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final ConnectionRuleAccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("accountabilityType", type);
	request.setAttribute("connectionRules", type.getConnectionRules());
	return forward(request, "/viewConnectionRules.jsp");
    }

    public ActionForward prepareCreateConnectionRule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final ConnectionRuleAccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("connectionRuleBean", new ConnectionRuleBean(type));
	return forward(request, "/createConnectionRule.jsp");
    }

    public ActionForward createConnectionRule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	final ConnectionRuleBean bean = getRenderedObject("connectionRuleBean");
	try {
	    bean.create();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("connectionRuleBean", bean);
	    return forward(request, "/createConnectionRule.jsp");
	}

	request.setAttribute("accountabilityTypeOid", bean.getAccountabilityType().getOID());
	return viewConnectionRules(mapping, form, request, response);
    }

    public ActionForward deleteConnectionRule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	final ConnectionRule connectionRule = getDomainObject(request, "connectionRuleOid");
	final ConnectionRuleAccountabilityType accountabilityType = connectionRule.getAccountabilityType();
	try {
	    accountabilityType.deleteConnectionRule(connectionRule);
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	}

	request.setAttribute("accountabilityTypeOid", accountabilityType.getOID());
	return viewConnectionRules(mapping, form, request, response);
    }

    public ActionForward viewOrganization(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	// TODO: move this to renderer ?
	final List<Unit> topUnits = new ArrayList<Unit>(getMyOrg().getTopUnits());
	Collections.sort(topUnits, Party.COMPARATOR_BY_NAME);
	request.setAttribute("topUnits", topUnits);
	return forward(request, "/viewOrganization.jsp");
    }

    public ActionForward viewUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	request.setAttribute("unit", getDomainObject(request, "unitOid"));
	return forward(request, "/unit/viewUnit.jsp");
    }

    public ActionForward prepareCreateUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final UnitBean bean = new UnitBean();
	bean.setParent((Unit) getDomainObject(request, "parentOid"));
	request.setAttribute("unitBean", bean);
	return forward(request, "/unit/createUnit.jsp");
    }

    public ActionForward createUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final UnitBean bean = getRenderedObject("unitBean");
	Unit result = null;
	try {
	    result = bean.createUnit();
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	    request.setAttribute("unitBean", bean);
	    return forward(request, "/unit/createUnit.jsp");
	}

	if (result.isTop()) {
	    return viewOrganization(mapping, form, request, response);
	} else {
	    request.setAttribute("unit", bean.getParent());
	    return forward(request, "/unit/viewUnit.jsp");
	}
    }

    public ActionForward prepareEditUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Unit unit = getDomainObject(request, "unitOid");
	request.setAttribute("unitBean", new UnitBean(unit));
	return forward(request, "/unit/editUnit.jsp");
    }

    public ActionForward editUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final UnitBean bean = getRenderedObject("unitBean");
	try {
	    bean.editUnit();
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	    request.setAttribute("unitBean", bean);
	    return forward(request, "/unit/editUnit.jsp");
	}

	request.setAttribute("unit", bean.getUnit());
	return forward(request, "/unit/viewUnit.jsp");
    }

    public ActionForward deleteUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Unit unit = getDomainObject(request, "unitOid");
	try {
	    unit.delete();
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	    request.setAttribute("unit", unit);
	    return forward(request, "/unit/viewUnit.jsp");
	}
	return viewOrganization(mapping, form, request, response);
    }

    public ActionForward prepareAddParent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	request.setAttribute("unitBean", new UnitBean((Unit) getDomainObject(request, "unitOid")));
	return forward(request, "/unit/addParent.jsp");
    }

    public ActionForward addParent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {

	final UnitBean bean = getRenderedObject("unitBean");
	try {
	    bean.addParent();
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	    request.setAttribute("unitBean", bean);
	    return forward(request, "/unit/addParent.jsp");
	}

	request.setAttribute("unit", bean.getUnit());
	return forward(request, "/unit/viewUnit.jsp");
    }

    public ActionForward removeParent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Accountability accountability = getDomainObject(request, "accOid");
	try {
	    accountability.getChild().removeParent(accountability);
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	}
	request.setAttribute("unit", accountability.getChild());
	return forward(request, "/unit/viewUnit.jsp");

    }
}
