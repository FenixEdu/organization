package module.contacts.domain;

import myorg.domain.groups.PersistentGroup;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class GroupAlias extends GroupAlias_Base {
    
    public GroupAlias(PersistentGroup groupToEdit, MultiLanguageString alias) {
        super();
	super.setGroupAlias(alias);
	super.setPersistentGroupAlias(groupToEdit);
	groupToEdit.setGroupAlias(this);
    }

    @Service
    public static GroupAlias create(PersistentGroup groupToEdit, MultiLanguageString alias) {
	GroupAlias thisNewObject = new GroupAlias(groupToEdit, alias);

	return thisNewObject;
    }
    
}
