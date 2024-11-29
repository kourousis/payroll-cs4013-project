import java.util.ArrayList;
import java.util.HashMap;

public class DBTest {
    public static void main(String[] args) {
        DBController db = new DBController();

        ArrayList<HashMap<String, String>> employees_csv = db.GET_CSV("employees");

        for (HashMap<String, String> row : employees_csv) {
            System.out.println(row);
            System.out.println();
        }
        // DB tester
        // DBController db = new DBController();

        // if (db.NEW_PAYSLIP(new Payslip(1, 1000, 100, 100, 100, 800, "John Doe"))) {
        // System.out.println("Payslip created");
        // }else {
        // System.out.println("Payslip not created");
        // }

        // if (db.ADD("payslip", new
        // String[]{"1",LocalDate.now().toString(),"John","1000","100","100","100","700"}))
        // {
        // System.out.println("Payslip added");
        // } else {
        // System.out.println("Payslip not added");
        // }

        // int id = Integer.parseInt(db.LATEST_ROW("employees").get("EmployeeID"));
        // System.out.println(id);
        //
        // HashMap<String, String> row;
        // row = db.GET_ROW("payslips", 1, "2024-11-20");
        // System.out.println(row);
        //
        // System.out.println(db.GET("control_data", 1, "Data"));
        // if (db.UPDATE("control_data", 1, "Data", "2024-11-21") != null ) {
        // System.out.println("Updated");
        // } else {
        // System.out.println("Not updated");
        // }

        String password1 = "password";
        String password2 = "hrpassword";
        String password3 = "adminpassword";
        String password4 = "partpassword";

        String hashedPassword1 = PasswordUtil.hashPassword(password1);
        System.out.println(hashedPassword1);
        System.out.println(PasswordUtil.hashPassword(password2));
        System.out.println(PasswordUtil.hashPassword(password3));
        System.out.println(PasswordUtil.hashPassword(password4));

        System.out.println(PasswordUtil.checkPassword(password1,
                "59b8145a9674e827e4f335bad0d220a322510c78272917d6a9bed4e256d95cc1"));
    }
}
