
public class App {
    public static void main(String[] args) {
        // DB tester
        DBController db = new DBController("HR");

        String name = db.GET("employees", 2, "Name"); // Get id=2's name(John Doe)
        System.out.println("GET:" + name);

        String update = db.UPDATE("employees", 2, "Email", "john.doe2@ul.ie");
        System.out.println("UPDATE:"+ update);

        System.out.println("GETALL:\n" + db.GET_ROW("employees", 1)); // Get all data for id=1
        System.out.println("GETCSV:\n" + db.GET_CSV("payclaim")); // Get whole table

        System.out.println(db.ADD("employees", new String[]
                {"5", "2023-02-01", "Liam Cleary", "217-555-5678", "liam.cleary@ul.ie", "password456", "City"}));
    }
}

