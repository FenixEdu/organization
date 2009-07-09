package module.organization.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.organization.domain.AccountabilityType;
import pt.ist.fenixWebFramework.util.DomainReference;

public class AccoutabilityTypesSelection implements Serializable {

    final Set<DomainReference<AccountabilityType>> accountabilityTypeReferences = new HashSet<DomainReference<AccountabilityType>>();

    public AccoutabilityTypesSelection(final Collection<AccountabilityType> accountabilityTypes) {
	for (final AccountabilityType accountabilityType : accountabilityTypes) {
	    accountabilityTypeReferences.add(new DomainReference<AccountabilityType>(accountabilityType));
	}
    }

    public List<AccountabilityType> getAccountabilityTypes() {
	final List<AccountabilityType> accountabilityTypes = new ArrayList<AccountabilityType>();
	for (final DomainReference<AccountabilityType> domainReference : accountabilityTypeReferences) {
	    accountabilityTypes.add(domainReference.getObject());
	}
	return accountabilityTypes;
    }

    public void setAccountabilityTypes(final List<AccountabilityType> accountabilityTypes) {
	accountabilityTypeReferences.clear();
	for (final AccountabilityType accountabilityType : accountabilityTypes) {
	    accountabilityTypeReferences.add(new DomainReference<AccountabilityType>(accountabilityType));
	}
    }

}
