import java.util.Date;

public class Staff {
    protected Date hireDate;
    protected String name;
    protected String phone;
    protected String email;
    protected Address address;

    /**
     * Param constructor to create a Staff object
     * 
     * @param hireDate
     * @param name
     * @param phone
     * @param email
     * @param address The address of the staff member represented as an object
     */
    public Staff(Date hireDate, String name, String phone, String email, Address address) {
        this.hireDate = hireDate;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Accessor method to get the Staff object as a string in a readable format
     * 
     * @return Concatenates the name, phone, email, postcode and address into a
     *         readable address
     */
    public String toString() {
        return "Hire Date: " + hireDate.toString() + "\nName: " + name + "\nPhone: " + phone + "\nEmail: " + email + "\nAddress: " + address;
    }
}
