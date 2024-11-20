import java.util.Date;

public class PartTimeStaff extends Staff {
    public PartTimeStaff(Date hireDate, String name, String phone, String email, Address address) {
        super(hireDate, name, phone, email, address);
    }

    public String toString() {
        return "Part-Time Staff\n" + super.toString();
    }
}