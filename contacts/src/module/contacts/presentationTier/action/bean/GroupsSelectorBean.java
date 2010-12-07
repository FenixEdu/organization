/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;

/**
 * 
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class GroupsSelectorBean implements Serializable {

    /**
     * Default version ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The groups that are selected
     */
    private List<PersistentGroup> groups;


    public void setGroups(List<PersistentGroup> groups) {
	this.groups = new ArrayList<PersistentGroup>();
	this.groups.addAll(groups);
    }

    public List<PersistentGroup> getGroups() {
	return groups;
    }

}
