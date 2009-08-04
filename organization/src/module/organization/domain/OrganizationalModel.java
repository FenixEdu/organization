package module.organization.domain;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import module.organization.domain.dto.OrganizationalModelBean;
import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixframework.pstm.Transaction;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class OrganizationalModel extends OrganizationalModel_Base {

    public static final Comparator<OrganizationalModel> COMPARATORY_BY_NAME = new Comparator<OrganizationalModel>() {
	@Override
	public int compare(final OrganizationalModel o1, final OrganizationalModel o2) {
	    return o1.getName().compareTo(o2.getName());
	}
    };

    public OrganizationalModel() {
	super();
	setMyOrg(MyOrg.getInstance());
    }

    public OrganizationalModel(final MultiLanguageString name) {
	this();
	setName(name);
    }

    public SortedSet<AccountabilityType> getSortedAccountabilityTypes() {
	final SortedSet<AccountabilityType> accountabilityTypes = new TreeSet<AccountabilityType>(
		AccountabilityType.COMPARATORY_BY_NAME);
	accountabilityTypes.addAll(getAccountabilityTypesSet());
	return accountabilityTypes;
    }

    @Service
    public static OrganizationalModel createNewModel(final OrganizationalModelBean organizationalModelBean) {
	return new OrganizationalModel(organizationalModelBean.getName());
    }

    @Service
    public void addPartyService(final Party party) {
	addParties(party);
    }

    @Service
    public void delete() {
	getAccountabilityTypesSet().clear();
	getPartiesSet().clear();
	removeMyOrg();
	Transaction.deleteObject(this);
    }

    public void setAccountabilityTypes(final List<AccountabilityType> accountabilityTypes) {
	getAccountabilityTypesSet().clear();
	getAccountabilityTypesSet().addAll(accountabilityTypes);
    }

    public Set<Unit> getAllUnits() {
	Set<Unit> units = new HashSet<Unit>();
	for (Party party : getParties()) {
	    if (party.isUnit()) {
		units.add((Unit) party);
		units.addAll(party.getDescendentUnits());
	    }
	}
	return units;
    }
}
