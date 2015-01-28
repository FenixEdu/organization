package module.geography.domain.exception;

import javax.ws.rs.core.Response.Status;

import org.fenixedu.bennu.core.domain.exceptions.DomainException;

public class GeographyDomainException extends DomainException {

    private static final long serialVersionUID = -1524575546446233473L;

    public GeographyDomainException(String key, String... args) {
        super(Status.PRECONDITION_FAILED, "resources.GeographyResources", key, args);
    }

    public GeographyDomainException(String key, Throwable e) {
        super(e, Status.PRECONDITION_FAILED, "resources.GeographyResources", key);
    }
}
