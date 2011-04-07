package module.contacts.domain.task;

import java.util.Collection;

import module.contacts.domain.PartyContact;
import module.organization.domain.Party;
import module.organization.domain.Person;
import myorg.domain.MyOrg;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.contacts.RemoteEmailAddress;
import net.sourceforge.fenixedu.domain.contacts.RemotePartyContact;
import pt.ist.fenixframework.plugins.remote.domain.RemoteHost;
import pt.ist.fenixframework.plugins.remote.domain.RemoteSystem;

public class SyncContactsFromRemote extends SyncContactsFromRemote_Base {
    @Override
    public String getLocalizedName() {
	return getClass().getName();
    }

    @Override
    public void executeTask() {
	final RemoteHost remoteHost = getRemoteHost();
	for (Party party : MyOrg.getInstance().getPartiesSet()) {
	    if (party instanceof Person) {
		Person person = (Person) party;
		RemotePerson remotePerson = person.getRemotePerson();
		if (remotePerson == null) {
		    remotePerson = new RemotePerson();
		    remotePerson.setRemoteHost(remoteHost);
		    remotePerson.setRemoteOid(person.getPartyImportRegister().getRemoteExternalOid());
		    person.setRemotePerson(remotePerson);
		}
		Collection<RemotePartyContact> contacts = remotePerson.getPartyContacts();
		if (contacts != null) {
		    for (RemotePartyContact remoteContact : contacts) {
			if (remoteContact instanceof RemoteEmailAddress) {
			    PartyContact.updatePartyContactFromRemoteReference(party, remoteContact);
			}
		    }
		}
	    }
	}
    }

    private static RemoteHost getRemoteHost() {
	// TODO : This is a hack... it should be selected when the person is
	// first imported.
	for (final RemoteHost remoteHost : RemoteSystem.getInstance().getRemoteHostsSet()) {
	    return remoteHost;
	}
	return null;
    }
}
