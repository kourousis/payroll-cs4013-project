//import java.util.Calendar;
//import java.util.Date;

public class App {
    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.FEBRUARY, 1);
//        Date hireDate = calendar.getTime();
//
//        Employee employee = new Employee(
//                hireDate,
//                "Jane Smith",
//                "217-555-5678",
//                "jane.smith@ul.ie",
//                new Address("5678 Oak St", "Springfield", "IL", "62702", "USA"),
//                75000.0f, "Computer Science");
//
//        calendar.set(2024, Calendar.JANUARY, 2);
//        hireDate = calendar.getTime();
//
//        Admin admin = new Admin(
//                hireDate,
//                "John Doe",
//                "217-553-4534",
//                "john.doe@ul.ie",
//                new Address("45 Jane St", "Springfield", "IL", "54326", "USA"),
//                10000);
//
//        calendar.set(2022, Calendar.NOVEMBER, 14);
//        hireDate = calendar.getTime();
//
//        HumanResources humanResources = new HumanResources(
//                hireDate,
//                "Lucy Smith",
//                "223-432-3845",
//                "lucy.smith@ul.ie",
//                new Address("234 Jane St", "Springfield", "IL", "548456", "USA"),
//                46500.0f);

//        System.out.println(employee + "\n");
//        System.out.println(admin + "\n");
//        System.out.println(humanResources + "\n");

        // DB tester
        DBController db = new DBController("HR");

        String name = db.GET("employees", 2, "Name"); // Get id=2's name(John Doe)
        System.out.println("GET:" + name);

        String update = db.UPDATE("employees", 2, "Email", "john.doe2@ul.ie");
        System.out.println("UPDATE:"+ update);

        System.out.println("GETALL:\n" + db.GET_ALL("employees", 1)); // Get all data for id=1
        System.out.println("GETCSV:\n" + db.GET_CSV("payclaim")); // Get whole table
    }
}
