public class Address {
    private String street;
    private String city;
    private String county;
    private String postcode;
    private String country;

    /**
     * Param constructor to create an Address object
     * 
     * @param street
     * @param city
     * @param county
     * @param postcode
     * @param country
     */
    public Address(String street, String city, String county, String postcode, String country) {
        this.street = street;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
    }

    /**
     * Accessor method to get the street name
     * 
     * @return Returns street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Accessor method to get the city name
     * 
     * @return Returns the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Accessor method to get the country name
     * 
     * @return Returns the country name
     */
    public String getCounty() {
        return county;
    }

    /**
     * Accessor method to get the postcode
     * 
     * @return Returns teh postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Acessor method to get the country name
     * 
     * @return Returns the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Accessor method to get the Address object as a string in a readable format
     * 
     * @return Concatenates the street, city county, postcode and country into a
     *         readable address
     */
    public String toString() {
        return street + ", " + city + ", " + county + ", " + postcode + ", " + country;
    }
}
