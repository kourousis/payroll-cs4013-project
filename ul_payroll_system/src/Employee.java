public class Employee extends Staff {
    private String department;
    private float salary;

    public Employee(String name, String phone, String email, Address address, float salary, String department) {
        super(name, phone, email, address);
        this.salary = salary;
        this.department = department;
    }

    // Accessor methods
    public String getDepartment() {
        return department;
    }
    public float getSalary() {
        return salary;
    }

    // toString
    public String toString() {
        return super.toString() + "\nDepartment: " + department + "\nSalary: " + salary;
    }
}
