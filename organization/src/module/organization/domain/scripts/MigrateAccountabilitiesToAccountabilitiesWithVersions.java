/*
 * @(#)MigrateAccountabilitiesToAccountabilitiesWithVersions.java
 *
 * Copyright 2012 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Organization Module.
 *
 *   The Organization Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Organization Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Organization Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.organization.domain.scripts;

import java.util.HashSet;
import java.util.Set;

import module.organization.domain.Accountability;
import myorg.domain.MyOrg;
import myorg.domain.scheduler.ReadCustomTask;
import myorg.domain.scheduler.TransactionalThread;

/**
 * 
 * FENIX-337 - Script to migrate the Accountabilities to make them have
 * AccountabilitiVersion
 * 
 * 
 * @author João Antunes
 * 
 */
public class MigrateAccountabilitiesToAccountabilitiesWithVersions extends ReadCustomTask {

    Set<Accountability> accountabilitiesToMigrate;

    private final int migratedAccs = 0;

    /*
     * (non-Javadoc)
     * 
     * @see jvstm.TransactionalCommand#doIt()
     */
    @Override
    public void doIt() {
	accountabilitiesToMigrate = new HashSet<Accountability>();

	// let's get the list of all Accountabilities without Versions

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
	    if (counter >= 1000) {
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
	    // let's do the rest of them
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
		    // joantune: uncompilable code ATM as migration FENIX-337
		    // was made, just here for historic purposes only
		    // new AccountabilityVersion(acc.getBeginDate(),
		    // acc.getEndDate(), acc, erased, acc.getCreatorUser(),
		    // acc.getCreationDate());
		    // migratedAccs++;
		}
	    }

	}

    }

}
