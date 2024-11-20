import java.time.LocalDate;

public class DBTest {
    public static void main(String[] args) {
        // DB tester
        DBController db = new DBController();

//        if (db.NEW_PAYSLIP(new Payslip(1, 1000, 100, 100, 100, 800, "John Doe"))) {
//            System.out.println("Payslip created");
//        }else {
//            System.out.println("Payslip not created");
//        }

        if (db.ADD("payslip", new String[]{"1",LocalDate.now().toString(),"John","1000","100","100","100","700"})) {
            System.out.println("Payslip added");
        } else {
            System.out.println("Payslip not added");
        }
    }
}

