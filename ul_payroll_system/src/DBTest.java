import java.time.LocalDate;
import java.util.HashMap;

public class DBTest {
    public static void main(String[] args) {
        // DB tester
        DBController db = new DBController();

//        if (db.NEW_PAYSLIP(new Payslip(1, 1000, 100, 100, 100, 800, "John Doe"))) {
//            System.out.println("Payslip created");
//        }else {
//            System.out.println("Payslip not created");
//        }

//        if (db.ADD("payslip", new String[]{"1",LocalDate.now().toString(),"John","1000","100","100","100","700"})) {
//            System.out.println("Payslip added");
//        } else {
//            System.out.println("Payslip not added");
//        }

        HashMap<String, String> row;
        row = db.GET_ROW("payslips", 1, "2024-11-20");
        System.out.println(row);

        System.out.println(db.GET("control_data", 1, "Data"));
        if (db.UPDATE("control_data", 1, "Data", "2024-11-21") != null ) {
            System.out.println("Updated");
        } else {
            System.out.println("Not updated");
        }
    }
}

