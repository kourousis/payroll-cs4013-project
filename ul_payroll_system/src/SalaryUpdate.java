import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {


    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //Changes String from CSV to LocalDate for use
        DBController db = new DBController();

        LocalDate roleDate = LocalDate.parse(db.GET("employees", 2, "HireDate"), formatter);

        LocalDate payBump = LocalDate.parse(db.GET("control_data", 1, "Data"), formatter);
        
        LocalDate today = LocalDate.now();
        System.out.println("Today is: " + today);
        
        System.out.println("hireDate/rollStartDate: " + roleDate);
        System.out.println("Next Pay increase date: " + payBump);
        String dateString = payBump.format(formatter);
        
        if(payBump.isBefore(today) || payBump.isEqual(today)){

            int i = 1;
            int staff = Integer.parseInt(db.GET("employees", i, "Salary"));
            
            while(db.GET("employees", i, "EmployeeID") != null){       //**Loop for checking every employee**
                i++;
            }

            payBump = payBump.plusYears(1);
            dateString = payBump.format(formatter);
            db.UPDATE("control_data", 1, "Data", dateString);
            System.out.println("payBump date updated to " + payBump);
        }
        else{
            System.out.println("payBump date not updated");
        }

    }
}