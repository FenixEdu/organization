package pt.ist.jpdaplace;

public class PostalCode extends Place<Locality, Place<PostalCode, ?>> {

    private static final long serialVersionUID = 1L;

    public PostalCode(final Locality locality, final String name, final String code) {
        super(locality, name, code);
    }

}
