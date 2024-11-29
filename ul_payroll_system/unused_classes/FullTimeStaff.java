import java.util.Date;

public class FullTimeStaff extends Staff {
    /**
     * Constructor to create FullTimeStaff object
     * 
     * @param hireDate hiredate of the mployee
     * @param name     name of the employee
     * @param phone    phone number of the employee
     * @param email    email of the employee
     * @param address  address of the employee
     */
    public FullTimeStaff(Date hireDate, String name, String phone, String email, Address address) {
        super(hireDate, name, phone, email, address);
    }

    /**
     * toString emthod to display employee info
     * 
     * @return Concatenates employee data into a string
     */
    public String toString() {
        return "Full-Time Staff\n" + super.toString();
    }
}