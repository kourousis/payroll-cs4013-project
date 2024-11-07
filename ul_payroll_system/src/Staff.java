public class Staff {
    protected String name;
    protected String phone;
    protected String email;
    protected Address address;

    public Staff(String name, String phone, String email, Address address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // toString
    public String toString() {
        return "Name: " + name + "\nPhone: " + phone + "\nEmail: " + email + "\nAddress: " + address;
    }
}
