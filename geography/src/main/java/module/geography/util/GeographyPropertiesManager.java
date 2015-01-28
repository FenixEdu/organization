package module.geography.util;

import org.fenixedu.commons.configuration.ConfigurationInvocationHandler;
import org.fenixedu.commons.configuration.ConfigurationManager;
import org.fenixedu.commons.configuration.ConfigurationProperty;

public class GeographyPropertiesManager {

    @ConfigurationManager(description = "Geography Properties")
    public interface ConfigurationProperties {

        @ConfigurationProperty(key = "modules.geography.file.import.location", defaultValue = "/")
        public String getGeographyImportFilesLocation();

    }

    public static ConfigurationProperties getConfiguration() {
        return ConfigurationInvocationHandler.getConfiguration(ConfigurationProperties.class);
    }

}
