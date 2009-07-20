package module.organization.domain.dto;

import java.io.Serializable;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class OrganizationalModelBean implements Serializable {

    private MultiLanguageString name;

    public OrganizationalModelBean() {
    }

    public MultiLanguageString getName() {
        return name;
    }

    public void setName(final MultiLanguageString name) {
        this.name = name;
    }

}
