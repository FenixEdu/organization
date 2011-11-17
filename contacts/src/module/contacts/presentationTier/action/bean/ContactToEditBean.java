/**
 * 
 */
package module.contacts.presentationTier.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.PartyContact;
import module.contacts.domain.PartyContactType;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.PhysicalAddress;
import myorg.applicationTier.Authenticate.UserView;
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

    private boolean defaultContact;

    private ArrayList<PersistentGroup> visibilityGroups;

    private boolean superEditor;

    private PartyContactType partyContactType;

    private String forwardPath;

    private TreeMap<String, String> parametersMap;
    
    private final static String[] parametersToIgnore = { "forwardToAction", "forwardToMethod", "_request_checksum_", "method" };


    protected ContactToEditBean() {
	setValue("");
	setVisibilityGroups(new ArrayList<PersistentGroup>());
	setSuperEditor(ContactsConfigurator.getInstance().isSuperEditor(UserView.getCurrentUser()));
    }

    public ContactToEditBean(String forwardPath) {
	this();
	setForwardPath(forwardPath);
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

    public void setForwardPathAndParameters(String path, HttpServletRequest request) {
	setForwardPath(path);
	setParametersMap(new TreeMap<String, String>());

	ArrayList<String> toIgnore = new ArrayList<String>(Arrays.asList(parametersToIgnore));

	Iterator<?> entries = request.getParameterMap().entrySet().iterator();
	while (entries.hasNext()) {
	    Entry<String, Object> thisEntry = (Entry<String, Object>) entries.next();
	    String key = thisEntry.getKey();
	    String value = request.getParameter(key);

	    if (!toIgnore.contains(key)) {
		getParametersMap().put(key, value);
	    }
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

    public void setDefaultContact(boolean defaultContact) {
	this.defaultContact = defaultContact;
    }

    public boolean isDefaultContact() {
	return defaultContact;
    }

    public void setForwardPath(String forwardPath) {
	this.forwardPath = forwardPath;
    }

    public String getForwardPath() {
	return forwardPath;
    }

    public String getForwardPathWithParameters() {
	String ret = getForwardPath();

	Iterator<?> entries = getParametersMap().entrySet().iterator();
	while (entries.hasNext()) {
	    Entry<String, String> thisEntry = (Entry<String, String>) entries.next();
	    String key = thisEntry.getKey();
	    Object value = thisEntry.getValue();

	    ret += (!ret.contains("?")) ? "?" : "&";
	    ret += key + "=" + value;
	}

	return ret;
    }

    public void setParametersMap(TreeMap<String, String> treeMap) {
	this.parametersMap = treeMap;
    }

    public Map<String, String> getParametersMap() {
	return parametersMap;
    }

    public static String getForwardPathFor(String path, HttpServletRequest request) {
	String ret = path;
	ArrayList<String> toIgnore = new ArrayList<String>(Arrays.asList(parametersToIgnore));
	
	Iterator<?> entries = request.getParameterMap().entrySet().iterator();
	while (entries.hasNext()) {
	    Entry<String, Object> thisEntry = (Entry<String, Object>) entries.next();
	    String key = thisEntry.getKey();
	    String value = request.getParameter(key);

	    if (!toIgnore.contains(key)) {
		 ret += (!ret.contains("?")) ? "?" : "&";
		 ret += key + "=" + value;
	    }
	}
	
	return ret;
    }

}
