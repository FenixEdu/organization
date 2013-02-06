package pt.ist.jpdaplace;

import pt.ist.jpdaplace.util.FileUtil;

public class Planet extends Place<Place<?, Planet>, Country> {

    private static final long serialVersionUID = 1L;

    private static final String RESOURCE_PATH = "/pt/ist/jpdaplace/data/";

    private static Planet earth = null;

    public static Planet getEarth() {
        return earth == null ? initEarth() : earth;
    }

    private synchronized static Planet initEarth() {
        return earth == null ? earth = new Planet() : earth;
    }

    private Planet() {
        super(null, "Earth");
        loadCountryData();
        loadSpecificCountryDate();
    }

    public Country getByAlfa2(final String alfa2) {
        return getPlace(alfa2);
    }

    public Country getByAlfa3(final String alfa3) {
        return getPlace(alfa3);
    }

    public Country getByNumber(final String number) {
        return getPlace(number);
    }

    private void loadCountryData() {
        final String[] content = FileUtil.readResource(RESOURCE_PATH + "countries.txt");
        for (final String line : content) {
            final String[] ss = line.split("\t");
            final String alpha2 = ss[0];
            final String alpha3 = ss[1];
            final String number = ss[2];
            final String name = ss[3];
            new Country(this, name, alpha2, alpha3, number);
        }
    }

    private void loadSpecificCountryDate() {
        loadSpecificCountryDate("PRT");
    }

    private void loadSpecificCountryDate(final String alfa3) {
        final Country country = getPlace(alfa3);
        final String resourcePrefix = RESOURCE_PATH + alfa3.toLowerCase();
        loadDistricts(country, resourcePrefix);
        loadMunicipalities(country, resourcePrefix);
        loadPostalCodes(country, resourcePrefix);
        loadParishes(country, resourcePrefix);
    }

    private void loadDistricts(final Country country, final String resourcePrefix) {
        final String[] content = FileUtil.readResource(resourcePrefix + "/districts.txt");
        for (int i = content.length; i-- > 0;) {
            final String line = content[i];
            final int s = line.indexOf(';');
            if (s > 0) {
                final String code = line.substring(0, s);
                final String name = line.substring(s + 1);
                new District(country, name, code);
            }
        }
    }

    private void loadMunicipalities(final Country country, final String resourcePrefix) {
        final String[] content = FileUtil.readResource(resourcePrefix + "/municipalities.txt");
        for (final String line : content) {
            final int s1 = line.indexOf(';');
            final int s2 = line.indexOf(';', s1 + 1);
            if (s2 > 0) {
                final String districtKey = line.substring(0, s1);
                final String code = line.substring(s1 + 1, s2);
                final String name = line.substring(s2 + 1);
                final District district = country.getPlace(districtKey);
                new Municipality(district, name, code);
            }
        }
    }

    private void loadPostalCodes(final Country country, final String resourcePrefix) {
        final String[] content = FileUtil.readResource(resourcePrefix + "/postalCodes.txt");
        for (final String line : content) {
            final int s1 = line.indexOf(';');
            final int s2 = line.indexOf(';', s1 + 1);
            final int s3 = line.indexOf(';', s2 + 1);
            final int s4 = line.indexOf(';', s3 + 1);
            final int s5 = line.indexOf(';', s4 + 1);
            final int s6 = line.indexOf(';', s5 + 1);
            final int s7 = line.indexOf(';', s6 + 1);
            final int s8 = line.indexOf(';', s7 + 1);
            final int s9 = line.indexOf(';', s8 + 1);
            final int s10 = line.indexOf(';', s9 + 1);
            final int s11 = line.indexOf(';', s10 + 1);
            final int s12 = line.indexOf(';', s11 + 1);
            final int s13 = line.indexOf(';', s12 + 1);
            final int s14 = line.indexOf(';', s13 + 1);
            final int s15 = line.indexOf(';', s14 + 1);
            final int s16 = line.indexOf(';', s15 + 1);

            final String districtKey = line.substring(0, s1);
            final String municipalityKey = line.substring(s1 + 1, s2);
            final String localityKey = line.substring(s2 + 1, s3);
            final String localityName = line.substring(s3 + 1, s4);
            final String postalCodeKey = line.substring(s14 + 1, s15);
            final String extension = line.substring(s15 + 1, s16);
            final String vCodigoPostal = postalCodeKey + "-" + extension;
            final String postalCodeName = line.substring(s16 + 1);

            final District district = country.getPlace(districtKey);
            final Municipality municipality = district.getPlace(municipalityKey);
            Locality locality = municipality.getPlace(localityKey);
            if (locality == null) {
                locality = new Locality(municipality, localityName, localityKey);
            }
            final PostalCode postalCode = locality.getPlace(vCodigoPostal);
            if (postalCode == null) {
                new PostalCode(locality, postalCodeName, vCodigoPostal);
            }
        }
    }

    private void loadParishes(final Country country, final String resourcePrefix) {
        final String[] content = FileUtil.readResource(resourcePrefix + "/parishes.txt");
        for (final String line : content) {
            final int s1 = line.indexOf(';');
            final int s2 = line.indexOf(';', s1 + 1);
            final int s3 = line.indexOf(';', s2 + 1);
            final int s4 = line.indexOf(';', s3 + 1);
            final int s5 = line.indexOf(';', s4 + 1);
            final int s6 = line.indexOf(';', s5 + 1);

            final String districtKey = line.substring(s1 + 1, s2);
            final String municipalityKey = line.substring(s3 + 1, s4);
            final String parishKey = line.substring(s5 + 1, s6);
            final String parishName = line.substring(s6 + 1);

            final District district = country.getPlace(districtKey);
            if (district == null) {
                System.out.println("no district for: " + districtKey);
            } else {
                final Municipality municipality = district.getPlace(municipalityKey);
                if (municipality == null) {
                    System.out.println("no municipality for: " + districtKey + " " + municipalityKey);
                } else {
                    new Parish(municipality, parishName, parishKey);
                }
            }
        }
    }

    @Override
    void exportAsString(final StringBuilder result) {
        // do nothing ...
    }

    public static Place importPlaceFromString(final String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        final Planet earth = Planet.getEarth();
        return earth.importFrom(string);
    }

    public static Parish importParishFromString(final String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        return Parish.importParishFromString(string);
    }

}
