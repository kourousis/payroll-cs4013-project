public class Address {
    private String street;
    private String city;
    private String state;
    private String postcode;
    private String country;

    public Address(String street, String city, String state, String postcode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
    }

    // Accessor methods
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getPostcode() {
        return postcode;
    }
    public String getCountry() {
        return country;
    }

    // toString
    public String toString() {
        return street + ", " + city + ", " + state + ", " + postcode + ", " + country;
    }
}
