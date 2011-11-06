package pt.ist.jpdaplace;

public class Municipality extends Place<District, Locality> {

    private static final long serialVersionUID = 1L;

    public Municipality(final District district, final String name, final String code) {
	super(district, name, code);
    }

}
