/*
 * @(#)VerifyAccountabilitiesMigration.java
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


/**
 * 
 *         FENIX-337 - Script to verify the correct migration of the
 *         Accountabilities
 *         {@link MigrateAccountabilitiesToAccountabilitiesWithVersions}
 * 
 *         All commented out because it has methods that stopped working due to
 *         the migration being done
 * 
 * @author João Antunes
 * 
 public class VerifyAccountabilitiesMigration extends ReadCustomTask {

 private int correctAccs;
 private int incorrectAccs;
 private int incorrectCreatorUser;
 private int incorrectCreationDate;
 private int incorrectBeginDate;
 private int incorrectEndDate;

 @Override
 public void doIt() {
 correctAccs = 0;
 incorrectAccs = 0;

 for (Accountability acc : MyOrg.getInstance().getAccountabilities()) {
 if (JavaUtil.isObjectEqualTo(acc.getEndDate(), acc.getEndDate2())
 && JavaUtil.isObjectEqualTo(acc.getBeginDate(), acc.getBeginDate2())
 && JavaUtil.isObjectEqualTo(acc.getCreationDate(), acc.getCreationDate2())
 && JavaUtil.isObjectEqualTo(acc.getCreatorUser(), acc.getCreatorUser2())) {
 correctAccs++;
 } else {
 out.print("ACC: " + acc.getExternalId());
 if (!JavaUtil.isObjectEqualTo(acc.getEndDate(), acc.getEndDate2())) {
 out.print(" end date discrepancy original: " + acc.getEndDate() + " final: " + acc.getEndDate2());
 incorrectEndDate++;
 }
 if (!JavaUtil.isObjectEqualTo(acc.getBeginDate(), acc.getBeginDate2())) {
 out.print("begin date discrepancy original: " + acc.getBeginDate() + " final: " + acc.getBeginDate2());
 incorrectBeginDate++;
 }
 if (!JavaUtil.isObjectEqualTo(acc.getCreationDate(), acc.getCreationDate2())) {
 out.print("creation date discrepancy original: " + acc.getCreationDate() + " final: "
 + acc.getCreationDate2());
 incorrectCreationDate++;
 }
 if (!JavaUtil.isObjectEqualTo(acc.getCreatorUser(), acc.getCreatorUser2())) {
 out.print("user date discrepancy original: " + acc.getCreatorUser() + " final: " + acc.getCreatorUser2());
 incorrectCreatorUser++;
 }
 out.println();
 incorrectAccs++;
 }
 }

 out.println("Got " + correctAccs + " correct accs, and " + incorrectAccs + " incorrect.");
 out.println("incorrect end date: " + incorrectEndDate + " incorrect begin date: " + incorrectBeginDate
 + " incorrect creation date: " + incorrectCreationDate + " incorrect creation user: " + incorrectCreatorUser);

 }

 }
 */
