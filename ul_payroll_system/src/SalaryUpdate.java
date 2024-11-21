import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Changes String from CSV to LocalDate for use
        DBController db = new DBController();
        LocalDate date = LocalDate.parse(db.GET("employees", 2, "HireDate"), formatter);

        LocalDate payBump = LocalDate.parse(db.GET("control_data", 2, "Data"), formatter);
        //if(payBump )

        LocalDate today = LocalDate.now();
        LocalDate date2 = LocalDate.of(2024, 5, 20);
        System.out.println("Date: " + today);
        System.out.println("Date: " + date2);
        System.out.println("Date: " + date);
        System.out.println("Pay increase date: " + payBump);

        if (today.isBefore(date2)) {
            System.out.println("Date1 is before Date2");
        } else if (today.isAfter(date2)) {
            System.out.println("Date1 is after Date2");
        } else {
            System.out.println("Date1 is equal to Date2");
        }

        long daysBetween = ChronoUnit.DAYS.between(date, today);
        System.out.println("Days since HireDate: " + daysBetween);
    }
}