package module.organization.domain.util;

import jvstm.cps.ConsistencyException;
import myorg.util.BundleUtil;

public class OrganizationConsistencyException extends ConsistencyException {
    private static final long serialVersionUID = 1L;

    @Override
    public String getLocalizedMessage() {
	return BundleUtil.getStringFromResourceBundle("resources/OrganizationResources", "error." + getMethodFullname());
    }
}