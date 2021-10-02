package au.edu.unsw.infs3634.covid19tracker;

import java.util.ArrayList;

public class Country {
    public Country(String id, String country, String countryCode, String slug, Integer newConfirmed, Integer totalConfirmed, Integer newDeaths, Integer totalDeaths, Integer newRecovered, Integer totalRecovered, String date) {
        this.id = id;
        this.country = country;
        this.countryCode = countryCode;
        this.slug = slug;
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
        this.date = date;
    }

    private String id;
    private String country;
    private String countryCode;
    private String slug;
    private Integer newConfirmed;
    private Integer totalConfirmed;
    private Integer newDeaths;
    private Integer totalDeaths;
    private Integer newRecovered;
    private Integer totalRecovered;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(Integer newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public Integer getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Integer totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Integer getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(Integer newRecovered) {
        this.newRecovered = newRecovered;
    }

    public Integer getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Integer totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static ArrayList<Country> getCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("40735414-f500-4ba6-a05f-375fb7e93db8", "United States of America", "US", "united-states", 178255, 24434283, 4229, 406001, 32579, 1898159, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("e84c10da-807b-4388-a4f4-fac813b585ba", "India", "IN", "india", 15244, 10610883, 151, 152869, 19965, 10265706, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("96f12c18-4c5a-4cb9-ae2a-aaed8f61817d", "Brazil", "BR", "brazil", 64385, 8638249, 1340, 212831, 52166, 7618080, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("3640be3c-d926-4bbd-a553-b0fae277e7e2", "Russian Federation", "RU", "russia", 20806, 3595136, 582, 66214, 24806, 2995256, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("cae10568-490e-4a55-9118-f4e4734f8453", "United Kingdom", "GB", "united-kingdom", 38992, 3515796, 1826, 93469, 129, 8492, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("d4a3517a-2845-40ea-87e2-980cf9d3a3bd", "France", "FR", "france", 17191, 3023661, 619, 71792, 0, 219354, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("499f4624-8874-4af1-96a6-ab7dc7dde70a", "Italy", "IT", "italy", 13568, 2414166, 524, 83681, 25015, 1806932, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("e6aba492-c283-4261-a96d-6f21e929c883", "Spain", "ES", "spain", 41576, 2412318, 464, 54637, 0, 150376, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("03b982f5-b6f9-4aba-b9c3-499ad036950c", "Turkey", "TR", "turkey", 8917, 2406216, 257, 24487, 0, 2283919, "2021-01-21T05:59:07.941Z"));
        countries.add(new Country("d02f5bc6-16b7-4f50-abd4-ec0ef01f6d42", "Germany", "DE", "germany", 29003, 2100618, 1013, 50010, 20606, 1778319, "2021-01-21T05:59:07.941Z"));

        return countries;
    }

}