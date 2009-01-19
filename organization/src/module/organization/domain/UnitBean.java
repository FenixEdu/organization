package module.organization.domain;

import java.io.Serializable;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.util.DomainReference;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class UnitBean implements Serializable {

    private static final long serialVersionUID = -952861107508339516L;

    private DomainReference<Unit> parent;
    private DomainReference<AccountabilityType> accountabilityType;
    private LocalDate begin;
    private LocalDate end;

    private DomainReference<Unit> unit;
    private MultiLanguageString name;
    private String acronym;
    private DomainReference<PartyType> partyType;

    public UnitBean() {
	setBegin(new LocalDate());
    }

    public UnitBean(final Unit unit) {
	this();

	setUnit(unit);
	setName(unit.getPartyName());
	setAcronym(unit.getAcronym());
	setPartyType(unit.getPartyType());
    }

    public Unit getParent() {
	return (this.parent != null) ? this.parent.getObject() : null;
    }

    public void setParent(Unit parent) {
	this.parent = (parent != null) ? new DomainReference<Unit>(parent) : null;
    }

    public boolean hasParent() {
	return getParent() != null;
    }

    public Unit getUnit() {
	return (this.unit != null) ? this.unit.getObject() : null;
    }

    public void setUnit(Unit unit) {
	this.unit = (unit != null) ? new DomainReference<Unit>(unit) : null;
    }

    public boolean isTop() {
	return getUnit().isTop();
    }

    public MultiLanguageString getName() {
	return name;
    }

    public void setName(MultiLanguageString name) {
	this.name = name;
    }

    public String getAcronym() {
	return acronym;
    }

    public void setAcronym(String acronym) {
	this.acronym = acronym;
    }

    public PartyType getPartyType() {
	return (this.partyType != null) ? this.partyType.getObject() : null;
    }

    public void setPartyType(PartyType partyType) {
	this.partyType = (partyType != null) ? new DomainReference<PartyType>(partyType) : null;
    }

    public AccountabilityType getAccountabilityType() {
	return (this.accountabilityType != null) ? this.accountabilityType.getObject() : null;
    }

    public void setAccountabilityType(AccountabilityType accountabilityType) {
	this.accountabilityType = (accountabilityType != null) ? new DomainReference<AccountabilityType>(accountabilityType)
		: null;
    }

    public LocalDate getBegin() {
	return begin;
    }

    public void setBegin(LocalDate begin) {
	this.begin = begin;
    }

    public LocalDate getEnd() {
	return end;
    }

    public void setEnd(LocalDate end) {
	this.end = end;
    }

    public Unit createUnit() {
	return Unit.create(this);
    }

    public Unit editUnit() {
	return getUnit().edit(getName(), getAcronym(), getPartyType());
    }

    public void addParent() {
	getUnit().addParent(getParent(), getAccountabilityType(), getBegin(), getEnd());
    }

}
