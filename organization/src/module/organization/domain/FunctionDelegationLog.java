package module.organization.domain;

import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.joda.time.DateTime;

public class FunctionDelegationLog extends FunctionDelegationLog_Base {

    public FunctionDelegationLog(final FunctionDelegation functionDelegation, String operation) {
	super();
	setMyOrg(functionDelegation.getMyOrg());
	setFunctionDelegation(functionDelegation);
	final User user = UserView.getCurrentUser();
	setExecutor(user == null ? null : user.getUsername());
	setOperationInstant(new DateTime());
	setOperation(operation);
    }

}
