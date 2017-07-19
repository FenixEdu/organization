/*
 * @(#)OrganizationModelAction.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Organization Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Organization Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.organization.presentationTier.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.Person;
import module.organization.domain.Unit;
import module.organization.domain.UnitBean;
import module.organization.domain.dto.OrganizationalModelBean;
import module.organization.domain.predicates.PartyPredicate.PartyByAccountabilityType;
import module.organization.domain.search.PartySearchBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.exceptions.DomainException;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.bennu.core.presentationTier.component.OrganizationChart;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.base.BaseAction;
import org.fenixedu.bennu.struts.portal.EntryPoint;
import org.fenixedu.bennu.struts.portal.StrutsApplication;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

@StrutsApplication(bundle = "OrganizationResources", path = "organization", titleKey = "label.manage.organization",
        accessGroup = "#managers", hint = "Organization")
@Mapping(path = "/organizationModel")
/**
 * 
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
public class OrganizationModelAction extends BaseAction {

    public static class OrganizationalModelChart extends OrganizationChart<OrganizationalModel> {

        public OrganizationalModelChart(final Collection<OrganizationalModel> organizationalModels) {
            super(sortCollection(organizationalModels), 3);
        }

        private static Collection sortCollection(final Collection<OrganizationalModel> organizationalModels) {
            final List<OrganizationalModel> result = new ArrayList<OrganizationalModel>(organizationalModels);
            Collections.sort(result, OrganizationalModel.COMPARATORY_BY_NAME);
            return result;
        }

    }

    public static class PartyChart extends OrganizationChart<Object> {

        public PartyChart(final Collection<Party> parties) {
            super(sortCollection(parties), 2);
        }

        public PartyChart(final Collection<Party> parties, final int size) {
            super(sortCollection(parties), size);
        }

        public PartyChart(final Set<AccountabilityType> accountabilityTypes, final Party party) {
            super(party, sortCollectionByParents(party.getParentAccountabilityStream()
                    .filter(a -> new PartyByAccountabilityType(accountabilityTypes).eval(a.getChild(), a))
                    .collect(Collectors.toSet())), sortCollectionByChildren(party.getChildAccountabilityStream()
                    .filter(a -> new PartyByAccountabilityType(accountabilityTypes).eval(a.getParent(), a))
                    .collect(Collectors.toSet())), 2);
        }

        public PartyChart(final Set<AccountabilityType> accountabilityTypes, final Party party, final Class<? extends Party> clazz) {
            super(party, sortCollectionByParents(party.getParentAccountabilityStream()
                    .filter(a -> new PartyByAccountabilityType(accountabilityTypes).eval(a.getChild(), a))
                    .collect(Collectors.toSet())), sortCollectionByChildren(party.getChildAccountabilityStream()
                    .filter(a -> new PartyByAccountabilityType(clazz, accountabilityTypes).eval(a.getChild(), a))
                    .collect(Collectors.toSet())), 2);
        }

        public PartyChart(final Accountability accountability) {
            super(accountability.getChild(), collect(accountability), Collections.emptyList(), 2);
        }

        public PartyChart(final Party party, final Collection partentParties, final Collection childParties) {
            super(party, partentParties, childParties, 2);
        }

        public PartyChart(final Party party, final Collection partentParties, final Collection childParties, final int size) {
            super(party, partentParties, childParties, size);
        }

        private static Collection collect(final Object object) {
            final List<Object> result = new ArrayList<Object>(1);
            result.add(object);
            return result;
        }

        public static Collection sortCollection(final Collection<Party> parties) {
            final List<Party> result = new ArrayList<Party>(parties);
            Collections.sort(result, Party.COMPARATOR_BY_NAME);
            return result;
        }

        private static Collection sortCollectionByParents(final Collection<Accountability> accountabilities) {
            final List<Accountability> result = new ArrayList<Accountability>(accountabilities);
            Collections.sort(result, Accountability.COMPARATOR_BY_PARENT_PARTY_NAMES);
            return result;
        }

        private static Collection sortCollectionByChildren(final Collection<Accountability> accountabilities) {
            final List<Accountability> result = new ArrayList<Accountability>(accountabilities);
            Collections.sort(result, Accountability.COMPARATOR_BY_CHILD_PARTY_NAMES);
            return result;
        }

        public Person getPerson() {
            final Object object = getElement();
            return object != null && object instanceof Person ? ((Person) object) : null;
        }

        public Unit getUnit() {
            final Object object = getElement();
            return object instanceof Unit ? ((Unit) object) : null;
        }

        public Accountability getAccountability() {
            final Object object = getElement();
            return object != null && object instanceof Accountability ? ((Accountability) object) : null;
        }

    }

    public static abstract class PartyChartView extends PartyViewHook {

        protected Set<AccountabilityType> getAccountabilityTypes(final OrganizationalModel organizationalModel) {
            return organizationalModel.getAccountabilityTypesSet();
        }

        @Override
        public String hook(final HttpServletRequest request, final OrganizationalModel organizationalModel, final Party party) {
            final PartyChart partyChart = createPartyChart(organizationalModel, party);
            request.setAttribute("partyChart", partyChart);
            return "/organization/model/partyChart.jsp";
        }

        protected abstract PartyChart createPartyChart(final OrganizationalModel organizationalModel, final Party party);
    }

    public static final String UNIT_CHART_VIEW_NAME = "00_default";

    public static class UnitChartView extends PartyChartView {

        @Override
        public String getViewName() {
            return UNIT_CHART_VIEW_NAME;
        }

        @Override
        protected PartyChart createPartyChart(final OrganizationalModel organizationalModel, final Party party) {
            return new PartyChart(getAccountabilityTypes(organizationalModel), party, Unit.class);
        }

        @Override
        public String getPresentationName() {
            return BundleUtil.getString("resources.OrganizationResources", "label.viewUnits");
        }

    }

    public static class PeopleChartView extends PartyChartView {

        @Override
        public String getViewName() {
            return "01_viewPeople";
        }

        @Override
        protected PartyChart createPartyChart(final OrganizationalModel organizationalModel, final Party party) {
            return new PartyChart(getAccountabilityTypes(organizationalModel), party, Person.class);
        }

        @Override
        public String getPresentationName() {
            return BundleUtil.getString("resources.OrganizationResources", "label.viewPeople");
        }

    }

    public static final PartyViewHookManager partyViewHookManager = new PartyViewHookManager();

    static {
        partyViewHookManager.register(new UnitChartView());
        partyViewHookManager.register(new PeopleChartView());
    }

    public static void viewModels(final HttpServletRequest request) {
        final Set<OrganizationalModel> organizationalModels =
                new TreeSet<OrganizationalModel>(OrganizationalModel.COMPARATORY_BY_NAME);
        organizationalModels.addAll(Bennu.getInstance().getOrganizationalModelsSet());
        request.setAttribute("organizationalModels", organizationalModels);
        final OrganizationalModelChart organizationalModelChart = new OrganizationalModelChart(organizationalModels);
        request.setAttribute("organizationalModelChart", organizationalModelChart);
    }

    @EntryPoint
    public ActionForward viewModels(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        viewModels(request);
        return forward("/organization/model/viewModels.jsp");
    }

    public ActionForward viewModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);

        Party party;
        PartySearchBean partySearchBean = getRenderedObject("partySearchBean");
        if (partySearchBean == null) {
            party = getDomainObject(request, "partyOid");
            if (party == null && organizationalModel.getPartiesSet().size() == 1) {
                party = organizationalModel.getPartiesSet().iterator().next();
                request.setAttribute("viewName", UNIT_CHART_VIEW_NAME);
            }
            partySearchBean = new PartySearchBean(party);
        } else {
            party = partySearchBean.getParty();
            RenderUtils.invalidateViewState();
        }
        request.setAttribute("partySearchBean", partySearchBean);

        if (party == null) {
            final PartyChart partyChart =
                    party == null ? new PartyChart(organizationalModel.getPartiesSet()) : new PartyChart(
                            organizationalModel.getAccountabilityTypesSet(), party);
            request.setAttribute("partiesChart", partyChart);
        } else {
            request.setAttribute("party", party);
            partyViewHookManager.hook(request, organizationalModel, party);

            final SortedSet<PartyViewHook> hooks = partyViewHookManager.getSortedHooks(party);
            if (hooks.size() > 1) {
                request.setAttribute("hooks", hooks);
            }
        }

        return forward("/organization/model/viewModel.jsp");
    }

    public ActionForward prepareCreateModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModelBean organizationalModelBean = new OrganizationalModelBean();
        request.setAttribute("organizationalModelBean", organizationalModelBean);
        return forward("/organization/model/createModel.jsp");
    }

    public ActionForward createModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModelBean organizationalModelBean = getRenderedObject();
        OrganizationalModel.create(organizationalModelBean.getName());
        return viewModels(mapping, form, request, response);
    }

    public ActionForward editModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        return forward("/organization/model/editModel.jsp");
    }

    public ActionForward deleteModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        organizationalModel.delete();
        return viewModels(mapping, form, request, response);
    }

    public ActionForward prepareAddUnitToModel(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final PartySearchBean partySearchBean = new PartySearchBean(null);
        request.setAttribute("partySearchBean", partySearchBean);
        return forward("/organization/model/addUnitToModel.jsp");
    }

    public ActionForward addUnitToModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final PartySearchBean partySearchBean = getRenderedObject();
        organizationalModel.addPartyService(partySearchBean.getParty());
        return viewModel(mapping, form, request, response);
    }

    public ActionForward manageModelAccountabilityTypes(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        return forward("/organization/model/manageModelAccountabilityTypes.jsp");
    }

    public ActionForward prepareCreateUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        final UnitBean unitBean = new UnitBean();
        if (party != null) {
            request.setAttribute("party", party);
            unitBean.setParent(party);
        }
        request.setAttribute("unitBean", unitBean);
        return forward("/organization/model/createUnit.jsp");
    }

    public ActionForward createUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final UnitBean bean = getRenderedObject("unitBean");
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        if (bean.getParent() == null) {
            bean.setOrganizationalModel(organizationalModel);
        }
        try {
            bean.createUnit();
            return viewModel(mapping, form, request, response);
        } catch (final DomainException e) {
            addMessage(request, e.getKey(), e.getArgs());
            request.setAttribute("organizationalModel", organizationalModel);
            final Party party = getDomainObject(request, "partyOid");
            request.setAttribute("party", party);
            request.setAttribute("unitBean", bean);
            return forward("/organization/model/createUnit.jsp");
        }
    }

    public ActionForward prepareEditUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        request.setAttribute("unitBean", new UnitBean((Unit) party));
        return forward("/organization/model/editUnit.jsp");
    }

    public ActionForward editUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final UnitBean bean = getRenderedObject("unitBean");
        try {
            bean.editUnit();
            return viewModel(mapping, form, request, response);
        } catch (final DomainException e) {
            addMessage(request, e.getKey(), e.getArgs());
            final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
            request.setAttribute("organizationalModel", organizationalModel);
            final Party party = getDomainObject(request, "partyOid");
            request.setAttribute("party", party);
            request.setAttribute("unitBean", bean);
            return forward("/organization/model/editUnit.jsp");
        }
    }

    public ActionForward managePartyPartyTypes(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        return forward("/organization/model/managePartyPartyTypes.jsp");
    }

    public ActionForward prepareAddUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        final UnitBean unitBean = new UnitBean();
        unitBean.setParent(party);
        request.setAttribute("unitBean", unitBean);
        request.setAttribute("viewName",
                request.getParameter("viewName") == null ? UNIT_CHART_VIEW_NAME : request.getParameter("viewName"));
        return forward("/organization/model/addUnit.jsp");
    }

    public ActionForward addUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final UnitBean unitBean = getRenderedObject("unitBean");
        try {
            unitBean.addChild();
            return viewModel(mapping, form, request, response);
        } catch (final DomainException e) {
            addMessage(request, e.getKey(), e.getArgs());
            final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
            request.setAttribute("organizationalModel", organizationalModel);
            final Party party = unitBean.getParent();
            request.setAttribute("party", party);
            request.setAttribute("unitBean", unitBean);
            request.setAttribute("viewName", request.getParameter("viewName"));
            return forward("/organization/model/addUnit.jsp");
        }
    }

    public ActionForward deleteUnit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final Unit unit = getDomainObject(request, "partyOid");
        try {
            unit.delete();

            final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
            request.setAttribute("organizationalModel", organizationalModel);

            final PartySearchBean partySearchBean = new PartySearchBean(null);
            request.setAttribute("partySearchBean", partySearchBean);

            final PartyChart partyChart = new PartyChart(organizationalModel.getPartiesSet());
            request.setAttribute("partiesChart", partyChart);

            return forward("/organization/model/viewModel.jsp");
        } catch (final DomainException e) {
            addMessage(request, e.getKey(), e.getArgs());
        }
        return viewModel(mapping, form, request, response);
    }

    public ActionForward prepareManageParentAccountabilities(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        final UnitBean unitBean = new UnitBean();
        unitBean.setChild(party);
        request.setAttribute("unitBean", unitBean);
        return forward("/organization/model/manageParentAccountabilities.jsp");
    }

    public ActionForward prepareManageChildAccountabilities(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        final UnitBean unitBean = new UnitBean();
        unitBean.setParent(party);
        request.setAttribute("unitBean", unitBean);
        return forward("/organization/model/manageChildAccountabilities.jsp");
    }

    public ActionForward prepareEditAccountability(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final String partyView = request.getParameter("partyView");
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        final UnitBean unitBean = new UnitBean();
        unitBean.setParent(party);
        request.setAttribute("unitBean", unitBean);
        final Accountability accountability = getDomainObject(request, "accountabilityOid");
        request.setAttribute("accountability", accountability);
        request.setAttribute("partyView", partyView);
        return forward("/organization/model/editAccountability.jsp");
    }

    public ActionForward prepareDeleteAccountability(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        request.setAttribute("organizationalModel", organizationalModel);
        final Party party = getDomainObject(request, "partyOid");
        request.setAttribute("party", party);
        final UnitBean unitBean = new UnitBean();
        unitBean.setParent(party);
        request.setAttribute("unitBean", unitBean);
        return forward("/organization/model/deleteAccountability.jsp");
    }

    public ActionForward deleteAccountability(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Accountability accountability = getDomainObject(request, "accountabilityOid");
        if (accountability != null) {
            final Party child = accountability.getChild();
            child.removeParent(accountability);
        }
        return viewModel(mapping, form, request, response);
    }

}
