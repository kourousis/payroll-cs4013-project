import java.util.Date;

public abstract class Staff {
    protected int id;
    protected Date hireDate;
    protected String name;
    protected String phone;
    protected String email;
    protected Address address;

    /**
     * Param constructor to create a Staff object
     * 
     * @param hireDate staff's hire date
     * @param name     staff's name
     * @param phone    staff's phone
     * @param email    staff's email
     * @param address  The address of the staff member represented as an object
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
     * @return A string containing the name, phone, email, postcode and address into
     *         a
     *         readable address
     */
    public String toString() {
        return "Hire Date: " + hireDate.toString() + "\nName: " + name + "\nPhone: " + phone + "\nEmail: " + email
                + "\nAddress: " + address;
    }

    /**
     * Method to set the Staff's id
     * 
     * @param id Staff's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor method to retrieve the Staff's id
     * 
     * @return Staff's id
     */
    public int getId() {
        return id;
    }
}
