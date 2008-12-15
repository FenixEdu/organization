package module.organization.domain;

import java.util.Collection;

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Unit extends Unit_Base {

    protected Unit() {
	super();
    }

    protected Unit(final Party parent, final MultiLanguageString name, final String acronym, final PartyType partyType,
	    final AccountabilityType accountabilityType) {
	this();

	check(partyType, name, acronym);
	checkNameAndAcronym(parent, name, acronym);

	setPartyType(partyType);
	setPartyName(name);
	setAcronym(acronym);

	if (parent != null) {
	    check(accountabilityType, "error.Unit.invalid.accountability.type");
	    new Accountability(parent, this, accountabilityType);
	} else {
	    setMyOrgFromTopUnit(MyOrg.getInstance());
	}
    }

    private void checkNameAndAcronym(final Party parent, final MultiLanguageString name, final String acronym) {
	if (parent != null) {
	    checkNameAndAcronym(parent.getChildAccountabilities(), name, acronym);
	} else {
	    checkTopUnitsNameAndAcronym(name, acronym);
	}
    }

    private void checkNameAndAcronym(final Collection<Accountability> accountabilities, final MultiLanguageString name,
	    final String acronym) {
	for (final Accountability accountability : accountabilities) {
	    if (accountability.getChild().isUnit() && !accountability.getChild().equals(this)) {
		checkNameAndAcronym(name, acronym, (Unit) accountability.getChild());
	    }
	}
    }

    private void checkTopUnitsNameAndAcronym(final MultiLanguageString name, final String acronym) {
	for (final Party party : MyOrg.getInstance().getTopUnits()) {
	    if (party.isUnit() && !party.equals(this)) {
		checkNameAndAcronym(name, acronym, (Unit) party);
	    }
	}
    }

    private void checkNameAndAcronym(final MultiLanguageString name, final String acronym, final Unit unit) {
	if (unit.getPartyName().equalInAnyLanguage(name)) {
	    throw new DomainException("error.Unit.found.child.with.same.name", name.getContent());
	}
	if (unit.getAcronym().equalsIgnoreCase(acronym)) {
	    throw new DomainException("error.Unit.found.child.with.same.acronym", acronym);
	}
    }

    private void check(final Object obj, final String message) {
	if (obj == null) {
	    throw new DomainException(message);
	}
    }

    private void check(final PartyType partyType, final MultiLanguageString name, final String acronym) {
	check(partyType, "error.Unit.invalid.party.type");

	if (name == null || name.isEmpty()) {
	    throw new DomainException("error.Unit.invalid.name");
	}

	if (acronym == null || acronym.isEmpty()) {
	    throw new DomainException("error.Unit.invalid.acronym");
	}
    }

    @Override
    final public boolean isUnit() {
	return true;
    }

    public boolean isTop() {
	return !hasAnyParentAccountabilities() && hasMyOrgFromTopUnit();
    }

    @Service
    public void addParent(final Party parent, final AccountabilityType type) {
	new Accountability(parent, this, type);
    }

    @Service
    public Unit edit(final MultiLanguageString name, final String acronym, final PartyType partyType) {
	check(partyType, name, acronym);
	for (final Accountability accountability : getParentAccountabilities()) {
	    checkNameAndAcronym(accountability.getParent(), name, acronym);
	}

	setPartyType(partyType);
	setPartyName(name);
	setAcronym(acronym);

	// after setting party type, check if accountabilities continue valid
	for (final Accountability accountability : getParentAccountabilities()) {
	    if (!accountability.areParentAndChildValid()) {
		throw new DomainException("error.Unit.accountability.doesnot.have.valid.parent.and.child");
	    }
	}

	return this;
    }

    @Override
    protected void disconnect() {
	removeMyOrgFromTopUnit();
	super.disconnect();
    }

    @Service
    static public Unit create(final UnitBean bean) {
	return new Unit(bean.getParent(), bean.getName(), bean.getAcronym(), bean.getPartyType(), bean.getAccountabilityType());
    }

    @Service
    static public Unit createRoot(final UnitBean bean) {
	return createRoot(bean.getName(), bean.getAcronym(), bean.getPartyType());
    }

    @Service
    static public Unit createRoot(final MultiLanguageString name, final String acronym, final PartyType partyType) {
	return new Unit(null, name, acronym, partyType, null);
    }
}
