/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.PartyContact;
import module.contacts.domain.PartyContactType;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.PhysicalAddress;
import module.geography.domain.Country;
import module.geography.domain.GeographicLocation;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.MyOrg;
import myorg.domain.groups.PersistentGroup;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class ContactToEditBean implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    private PartyContact wrappedContact;

    private PhysicalAddressBean physicalAddressBean = new PhysicalAddressBean();

    private PhoneType phoneType;

    protected String value;

    private ArrayList<PersistentGroup> visibilityGroups;

    private boolean superEditor;

    private PartyContactType partyContactType;

    protected ContactToEditBean() {
	setValue("");
	setVisibilityGroups(new ArrayList<PersistentGroup>());
	superEditor = ContactsConfigurator.getInstance().isSuperEditor(UserView.getCurrentUser());
    }

    public ContactToEditBean(PartyContact contactToWrap) {
	wrappedContact = contactToWrap;
	value = getWrappedContact().getValue();
	partyContactType = getWrappedContact().getType();
	superEditor = ContactsConfigurator.getInstance().isSuperEditor(UserView.getCurrentUser());

	setVisibilityGroups(new ArrayList<PersistentGroup>());
	getVisibilityGroups().addAll(getWrappedContact().getVisibilityGroups());
	if (getWrappedContact() instanceof Phone)
	    phoneType = ((Phone) getWrappedContact()).getPhoneType();
	if (getWrappedContact() instanceof PhysicalAddress) {
	    PhysicalAddress physicalAddress = (PhysicalAddress) getWrappedContact();
	    // TODO make this actually get the country - talk with pedro
	}
    }

    public String getClassName() {
	return getWrappedContact().getClass().getName();
    }

    public void setPhoneType(PhoneType phoneType) {
	this.phoneType = phoneType;
    }

    public PhoneType getPhoneType() {
	return phoneType;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

    public PartyContact getWrappedContact() {
	return wrappedContact;
    }

    public void setPartyContactType(PartyContactType partyContactType) {
	this.partyContactType = partyContactType;
    }

    public PartyContactType getPartyContactType() {
	return partyContactType;
    }

    public void setVisibilityGroups(ArrayList<PersistentGroup> visibilityGroups) {
	this.visibilityGroups = visibilityGroups;
    }

    public ArrayList<PersistentGroup> getVisibilityGroups() {
	return visibilityGroups;
    }

    public void setSuperEditor(boolean superEditor) {
	this.superEditor = superEditor;
    }

    public boolean getSuperEditor() {
	return superEditor;
    }

    public void setPhysicalAddressBean(PhysicalAddressBean physicalAddressBean) {
	this.physicalAddressBean = physicalAddressBean;
    }

    public PhysicalAddressBean getPhysicalAddressBean() {
	return physicalAddressBean;
    }

}
