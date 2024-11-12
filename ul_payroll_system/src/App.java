import java.util.Calendar;
import java.util.Date;

public class App {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.FEBRUARY, 1);
        Date hireDate = calendar.getTime();

        Employee employee = new Employee(
                hireDate,
                "Jane Smith",
                "217-555-5678",
                "jane.smith@ul.ie",
                new Address("5678 Oak St", "Springfield", "IL", "62702", "USA"),
                75000.0f, "Computer Science");

        calendar.set(2024, Calendar.JANUARY, 2);
        hireDate = calendar.getTime();

        Admin admin = new Admin(
                hireDate,
                "John Doe",
                "217-553-4534",
                "john.doe@ul.ie",
                new Address("45 Jane St", "Springfield", "IL", "54326", "USA"),
                10000);

        calendar.set(2022, Calendar.NOVEMBER, 14);
        hireDate = calendar.getTime();

        HumanResources humanResources = new HumanResources(hireDate,
                "Lucy Smith",
                "223-432-3845",
                "lucy.smith@ul.ie",
                new Address("234 Jane St", "Springfield", "IL", "548456", "USA"),
                46500.0f);

        System.out.println(employee);
        System.out.println();
        System.out.println(admin);
        System.out.println();
        System.out.println(humanResources);
    }
}
