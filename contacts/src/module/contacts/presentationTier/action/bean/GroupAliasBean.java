/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

import myorg.domain.groups.PersistentGroup;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class GroupAliasBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;

    private MultiLanguageString alias;

    private PersistentGroup groupToEdit;

    public void setAlias(MultiLanguageString alias) {
	this.alias = alias;
    }

    public MultiLanguageString getAlias() {
	return alias;
    }

    public void setGroupToEdit(PersistentGroup groupToEdit) {
	this.groupToEdit = groupToEdit;
    }

    public PersistentGroup getGroupToEdit() {
	return groupToEdit;
    }

}
