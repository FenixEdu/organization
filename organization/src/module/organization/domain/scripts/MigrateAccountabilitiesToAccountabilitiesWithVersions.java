/**
 * 
 */
package module.organization.domain.scripts;

import java.util.HashSet;
import java.util.Set;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityVersion;
import myorg.domain.MyOrg;
import myorg.domain.scheduler.ReadCustomTask;
import myorg.domain.scheduler.TransactionalThread;

/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt) - 9 de Fev de 2012
 * 
 *         FENIX-337 - Script to migrate the Accountabilities to make them have
 *         AccountabilitiVersion
 * 
 */
public class MigrateAccountabilitiesToAccountabilitiesWithVersions extends ReadCustomTask {

    Set<Accountability> accountabilitiesToMigrate;

    private int migratedAccs = 0;
    /* (non-Javadoc)
     * @see jvstm.TransactionalCommand#doIt()
     */
    @Override
    public void doIt() {
	accountabilitiesToMigrate = new HashSet<Accountability>();

	//let's get the list of all Accountabilities without Versions

	for (Accountability acc : MyOrg.getInstance().getAccountabilities()) {
	    if (acc.getAccountabilityVersion() == null) {
		accountabilitiesToMigrate.add(acc);
	    }
	}

	out.println("Got " + accountabilitiesToMigrate.size() + " accs to migrate");

	HashSet<Accountability> oneKAccsBatch = new HashSet<Accountability>();
	int counter = 0;
	for (Accountability acc : accountabilitiesToMigrate) {
	    counter++;
	    oneKAccsBatch.add(acc);
	    if (counter >= 1000)
	    {
		WorkerMigraterThread workerMigraterThread = new WorkerMigraterThread(oneKAccsBatch);
		workerMigraterThread.start();
		try {
		    workerMigraterThread.join();
		    counter = 0;
		    oneKAccsBatch = new HashSet<Accountability>();
		} catch (InterruptedException e) {
		    throw new Error(e);
		}
	    }
	}

	if (oneKAccsBatch.size() > 0) {
	    //let's do the rest of them
	    WorkerMigraterThread workerMigraterThread = new WorkerMigraterThread(oneKAccsBatch);
	    workerMigraterThread.start();
	    try {
		workerMigraterThread.join();
	    } catch (InterruptedException e) {
		throw new Error(e);
	    }
	}

	out.println("Migrated accs: " + migratedAccs);

    }
    
    class WorkerMigraterThread extends TransactionalThread {
	
	public final Set<Accountability> accsToMigrate;

	public WorkerMigraterThread(Set<Accountability> accsToMigrate) {
	    this.accsToMigrate = accsToMigrate;
	}

	@Override
	public void transactionalRun() {

	    for (Accountability acc : accsToMigrate) {
		if (acc.getAccountabilityVersion() == null) {
		    boolean erased = false;
		    if (acc.getParent() == null && acc.getChild() == null)
			erased = true;
		    new AccountabilityVersion(acc.getBeginDate(), acc.getEndDate(), acc, erased, acc.getCreatorUser(),
			    acc.getCreationDate());
		    migratedAccs++;
		}
	    }

	    	    
	}
	
    }

}
