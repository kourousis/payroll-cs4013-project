public class HumanResources extends Staff{
    private float salary;

    public HumanResources(String name, String phone, String email, Address address, float salary) {
        super(name, phone, email, address);
        this.salary = salary;
    }

    // Accessor methods
    public float getSalary() {
        return salary;
    }

    // toString
    public String toString() {
        return super.toString() + "\nSalary: " + salary;
    }
}