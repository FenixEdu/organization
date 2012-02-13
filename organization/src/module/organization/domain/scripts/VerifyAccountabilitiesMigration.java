/**
 * 
 */
package module.organization.domain.scripts;


/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt) - 9 de Fev de 2012
 * 
 *         FENIX-337 - Script to verify the correct migration of the
 *         Accountabilities
 *         {@link MigrateAccountabilitiesToAccountabilitiesWithVersions}
 * 
 *         All commented out because it has methods that stopped working due to
 *         the migration being done
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
