import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //Changes String from CSV to LocalDate for use
        DBController db = new DBController();
        // int i = 1;
        // while(db.GET("employees", i, "HireDate") != null){       **Loop for checking every employee**
        //     i++;
        // }

        //getSalaryData(String department, String jobTitle, int years)

        LocalDate hireDate = LocalDate.parse(db.GET("employees", 2, "HireDate"), formatter);

        LocalDate payBump = LocalDate.parse(db.GET("control_data", 1, "Data"), formatter);
        
        LocalDate today = LocalDate.now();
        System.out.println("Today is: " + today);
        
        System.out.println("hireDate/rollStartDate: " + hireDate);
        System.out.println("Next Pay increase date: " + payBump);
        String dateString = payBump.format(formatter);
        
        if(payBump.isBefore(today) || payBump.isEqual(today)){
            payBump = payBump.plusYears(1);
            dateString = payBump.format(formatter);
            db.UPDATE("control_data", 1, "Data", dateString);
            System.out.println("payBump date updated to " + payBump);
        }
        else{
            System.out.println("payBump not updated");
        }

    }
}