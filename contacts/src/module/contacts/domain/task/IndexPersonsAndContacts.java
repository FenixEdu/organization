/**
 * 
 */
package module.contacts.domain.task;

import pt.ist.fenixWebFramework.services.Service;
import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.PartyContact;
import module.organization.domain.Person;
import myorg.domain.MyOrg;
import myorg.domain.scheduler.Task;
import myorg.util.BundleUtil;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 *
 */
public class IndexPersonsAndContacts extends Task {

    private static int personsTouched = 0;

    private static int contactsTouched = 0;

    /* (non-Javadoc)
     * @see myorg.domain.scheduler.Task#getLocalizedName()
     */
    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/ContactsResources", "label.task.index.persons.and.contacts");
    }

    /* (non-Javadoc)
     * @see myorg.domain.scheduler.Task#executeTask()
     */
    @Override
    @Service
    public void executeTask() {
	for (Person person : MyOrg.getInstance().getPersons()) {
	    person.setPartyName(person.getPartyName());
	    personsTouched++;
	}

	for (PartyContact contact : ContactsConfigurator.getInstance().getPartyContact()) {
	    contact.setContactValue(contact.getValue());
	    contactsTouched++;
	}

	logInfo("Touched " + personsTouched + " persons");
	logInfo("Touched " + contactsTouched + " contacts");

    }

}
