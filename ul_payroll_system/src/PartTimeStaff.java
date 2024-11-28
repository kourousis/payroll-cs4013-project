import java.util.Date;

public class PartTimeStaff extends Staff {
    /**
     * Constructor used to create PartTimeStaff class
     * 
     * @param hireDate hiredate of the employee
     * @param name name of the employee
     * @param phone phone number of the employee
     * @param email email of the employee
     * @param address address of the employee
     */
    public PartTimeStaff(Date hireDate, String name, String phone, String email, Address address) {
        super(hireDate, name, phone, email, address);
    }

    /**
     * Accessor method used to return part time staff employee's information in a formatted way
     * 
     * @return a concatenation of the employees details in a string format
     */
    public String toString() {
        return "Part-Time Staff\n" + super.toString();
    }
}