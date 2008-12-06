package module.organization.presentationTier.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import myorg.domain.exceptions.DomainException;
import myorg.presentationTier.actions.ContextBaseAction;
import myorg.presentationTier.forms.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/organization", formBeanClass = OrganizationManagementAction.OrganizationForm.class)
@Forwards( { @Forward(name = "show.options", path = "/showOptions.jsp"),
	@Forward(name = "view.party.types", path = "/viewPartyTypes.jsp"),
	@Forward(name = "create.party.type", path = "/createPartyType.jsp"),
	@Forward(name = "edit.party.type", path = "/editPartyType.jsp"),
	@Forward(name = "view.accountability.types", path = "/viewAccountabilityTypes.jsp"),
	@Forward(name = "select.accountability.type", path = "/selectAccountabilityType.jsp"),
	@Forward(name = "create.accountability.type", path = "/createAccountabilityType.jsp"),
	@Forward(name = "edit.accountability.type", path = "/editAccountabilityType.jsp"),
	@Forward(name = "view.connection.rules", path = "/viewConnectionRules.jsp"),
	@Forward(name = "create.connection.rule", path = "/createConnectionRule.jsp"),
	@Forward(name = "view.organization", path = "/viewOrganization.jsp"),
	@Forward(name = "view.unit", path = "/unit/viewUnit.jsp"), @Forward(name = "create.unit", path = "/unit/createUnit.jsp")

})
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

    public final ActionForward showOptions(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	return mapping.findForward("show.options");
    }

    public final ActionForward viewPartyTypes(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	request.setAttribute("partyTypes", getMyOrg().getPartyTypes());
	return mapping.findForward("view.party.types");
    }

    public final ActionForward prepareCreatePartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	request.setAttribute("partyTypeBean", new PartyTypeBean());
	return mapping.findForward("create.party.type");
    }

    public final ActionForward createPartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final PartyTypeBean bean = getRenderedObject("partyTypeBean");
	try {
	    PartyType.create(bean);
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("partyTypeBean", bean);
	    return mapping.findForward("create.party.type");
	}
	return viewPartyTypes(mapping, form, request, response);
    }

    public final ActionForward prepareEditPartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	request.setAttribute("partyTypeBean", new PartyTypeBean((PartyType) getDomainObject(request, "partyTypeOid")));
	return mapping.findForward("edit.party.type");
    }

    public final ActionForward editPartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final PartyTypeBean bean = getRenderedObject("partyTypeBean");
	try {
	    bean.edit();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("partyTypeBean", bean);
	    return mapping.findForward("edit.party.type");
	}

	return viewPartyTypes(mapping, form, request, response);
    }

    public final ActionForward deletePartyType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	((PartyType) getDomainObject(request, "partyTypeOid")).delete();
	return viewPartyTypes(mapping, form, request, response);
    }

    public final ActionForward viewAccountabilityTypes(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	request.setAttribute("accountabilityTypes", getMyOrg().getAccountabilityTypes());
	return mapping.findForward("view.accountability.types");
    }

    public final ActionForward selectAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	return mapping.findForward("select.accountability.type");
    }

    public final ActionForward prepareCreateAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final OrganizationForm organizationForm = (OrganizationForm) form;
	final Class<?> clazz = Class.forName(organizationForm.getAccountabilityTypeClassName());
	request.setAttribute("accountabilityTypeBean", clazz.newInstance());
	return mapping.findForward("create.accountability.type");
    }

    public final ActionForward createAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityTypeBean bean = getRenderedObject("accountabilityTypeBean");
	try {
	    bean.create();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("accountabilityTypeBean", bean);
	    return mapping.findForward("create.accountability.type");
	}
	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public final ActionForward prepareEditAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("accountabilityTypeBean", type.buildBean());
	return mapping.findForward("edit.accountability.type");
    }

    public final ActionForward editAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final AccountabilityTypeBean bean = getRenderedObject("accountabilityTypeBean");
	try {
	    bean.edit();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("accountabilityTypeBean", bean);
	    return mapping.findForward("edit.accountability.type");
	}

	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public final ActionForward deleteAccountabilityType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	((AccountabilityType) getDomainObject(request, "accountabilityTypeOid")).delete();
	return viewAccountabilityTypes(mapping, form, request, response);
    }

    public final ActionForward viewConnectionRules(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final ConnectionRuleAccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("accountabilityType", type);
	request.setAttribute("connectionRules", type.getConnectionRules());
	return mapping.findForward("view.connection.rules");
    }

    public final ActionForward prepareCreateConnectionRule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final ConnectionRuleAccountabilityType type = getDomainObject(request, "accountabilityTypeOid");
	request.setAttribute("connectionRuleBean", new ConnectionRuleBean(type));
	return mapping.findForward("create.connection.rule");
    }

    public final ActionForward createConnectionRule(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	final ConnectionRuleBean bean = getRenderedObject("connectionRuleBean");
	try {
	    bean.create();
	} catch (final DomainException e) {
	    addMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("connectionRuleBean", bean);
	    return mapping.findForward("create.connection.rule");
	}

	request.setAttribute("accountabilityTypeOid", bean.getAccountabilityType().getOID());
	return viewConnectionRules(mapping, form, request, response);
    }

    public final ActionForward deleteConnectionRule(final ActionMapping mapping, final ActionForm form,
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

    public final ActionForward viewOrganization(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	// TODO: move this to renderer ?
	final List<Unit> topUnits = new ArrayList<Unit>(getMyOrg().getTopUnits());
	Collections.sort(topUnits, Party.COMPARATOR_BY_NAME);
	request.setAttribute("topUnits", topUnits);
	return mapping.findForward("view.organization");
    }

    public final ActionForward viewUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Unit unit = getDomainObject(request, "unitOid");
	if (unit == null) {
	    return viewOrganization(mapping, form, request, response);
	}
	request.setAttribute("unit", unit);
	return mapping.findForward("view.unit");
    }

    public final ActionForward prepareCreateUnit(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	final UnitBean bean = new UnitBean();
	bean.setParent((Unit) getDomainObject(request, "parentOid"));
	request.setAttribute("unitBean", bean);
	return mapping.findForward("create.unit");
    }

    public final ActionForward createUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final UnitBean bean = getRenderedObject("unitBean");
	try {
	    bean.createUnit();
	} catch (final DomainException e) {
	    addMessage(request, e.getKey(), e.getArgs());
	    request.setAttribute("unitBean", bean);
	    return mapping.findForward("create.unit");
	}
	return viewOrganization(mapping, form, request, response);
    }

}
