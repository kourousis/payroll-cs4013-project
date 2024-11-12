import java.util.Date;

public class HumanResources extends Staff {
    private float salary;

    public HumanResources(Date hireDate, String name, String phone, String email, Address address, float salary) {
        super(hireDate, name, phone, email, address);
        this.salary = salary;
    }

    // Accessor methods
    public float getSalary() {
        return salary;
    }

    // toString
    public String toString() {
        return "Role: Human Resources\n" + super.toString() + "\nSalary: " + salary;
    }
}