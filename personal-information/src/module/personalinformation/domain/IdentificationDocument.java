package module.personalinformation.domain;

import java.io.Serializable;

import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import pt.ist.fenixframework.pstm.Transaction;

public class IdentificationDocument extends IdentificationDocument_Base {

    static public class IdentificationDocumentBean implements Serializable {

	static private final long serialVersionUID = -6239097801967805232L;

	private IDDocumentType type;
	private String number;

	public IDDocumentType getType() {
	    return type;
	}

	public void setType(IDDocumentType type) {
	    this.type = type;
	}

	public String getNumber() {
	    return number;
	}

	public void setNumber(String number) {
	    this.number = number;
	}

    }

    protected IdentificationDocument() {
	super();
	setOjbConcreteClass(getClass().getName());
	setMyOrg(MyOrg.getInstance());
    }

    private IdentificationDocument(final PersonalInformation information, final IdentificationDocumentBean bean) {
	this();
	init(information, bean);
    }

    protected void init(final PersonalInformation information, final IdentificationDocumentBean bean) {
	if (information == null) {
	    throw new DomainException("error.IdentificationDocument.invalid.personal.information");
	}
	if (bean.getType() == null) {
	    throw new DomainException("error.IdentificationDocument.invalid.type");
	}
	if (bean.getNumber() == null || bean.getNumber().isEmpty()) {
	    throw new DomainException("error.IdentificationDocument.invalid.number");
	}
	setPersonalInformation(information);
	setType(bean.getType());
	setNumber(bean.getNumber());
    }

    public void delete() {
	disconnect();
	Transaction.deleteObject(this);
    }

    protected void disconnect() {
	removeMyOrg();
	removePersonalInformation();
    }

    static IdentificationDocument create(final PersonalInformation information, final IdentificationDocumentBean bean) {
	switch (bean.getType()) {
	case IDENTITY_CARD:
	    return new IdentityCard(information, bean);
	default:
	    return new IdentificationDocument(information, bean);
	}
    }
}
