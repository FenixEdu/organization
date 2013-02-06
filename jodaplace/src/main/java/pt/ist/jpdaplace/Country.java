package pt.ist.jpdaplace;

import java.util.Locale;
import java.util.ResourceBundle;

public class Country extends Place<Planet, District> {

    private static final long serialVersionUID = 1L;

    public final String alpha2;
    public final String alpha3;
    public final String number;

    public Country(final Planet planet, final String name, final String alpha2, final String alpha3, final String number) {
        super(planet, name, alpha2, alpha3, number);
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.number = number;
    }

    @Override
    public String getLocalizedName(final Locale locale) {
        return getResource("pt.ist.jpdaplace.data.CountryResources", locale);
    }

    public String getNationality(final Locale locale) {
        return getResource("pt.ist.jpdaplace.data.NationalityResources", locale);
    }

    private String getResource(final String bundleName, final Locale locale) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale);
        return resourceBundle.getString(alpha3);
    }

}
