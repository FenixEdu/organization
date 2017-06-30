package org.fenixedu.bennu;

import org.fenixedu.bennu.spring.BennuSpringModule;
import org.fenixedu.commons.configuration.ConfigurationInvocationHandler;
import org.fenixedu.commons.configuration.ConfigurationManager;

@BennuSpringModule(basePackages = "module.organization.ui", bundles = "OrganizationResources")
public class OrganizationConfiguration {

    @ConfigurationManager(description = "Organization Configuration")
    public interface ConfigurationProperties {
    }
    
    public static ConfigurationProperties getConfiguration() {
        return ConfigurationInvocationHandler.getConfiguration(ConfigurationProperties.class);
    }

}
