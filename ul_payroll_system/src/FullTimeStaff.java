import java.util.Date;

public class FullTimeStaff extends Staff {
    public FullTimeStaff(Date hireDate, String name, String phone, String email, Address address) {
        super(hireDate, name, phone, email, address);
    }

    public String toString() {
        return "Full-Time Staff\n" + super.toString();
    }
}