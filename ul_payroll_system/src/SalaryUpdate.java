import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {


    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //Changes String from CSV to LocalDate for use
        DBController db = new DBController();

        FullScale fullCalc= new FullScale();
        PartScale partCalc = new PartScale();

        LocalDate roleDate = LocalDate.parse(db.GET("employees", 2, "RoleDate"), formatter);

        LocalDate payBump = LocalDate.parse(db.GET("control_data", 1, "Data"), formatter);
        
        LocalDate today = LocalDate.now();
        System.out.println("Today is: " + today);
        
        System.out.println("hireDate/rollStartDate: " + roleDate);
        // System.out.println("Next Pay increase date: " + payBump);
        // String dateString = payBump.format(formatter);
        
        int totalRows = db.getRowCount("employees");
        for (int i = 1; i <= totalRows; i++){       //**Loop for checking every employee**
            String roleType = db.GET("employees", i, "RoleType");
            System.out.println(roleType);
            String department = db.GET("employees", i, "Department");
            String jobTitle = db.GET("employees", i, "Jobtitle");
            int years = (int) ChronoUnit.YEARS.between(roleDate, today);

            if("FULLTIME".equalsIgnoreCase(roleType)){
                // Get salary
                float salary = fullCalc.getSalaryData(department, jobTitle, years);
                System.out.println("Salary for employee " + i + ": " + salary);
            }
            else if("PARTTIME".equalsIgnoreCase(roleType)){
                // Get salary
                float salary = partCalc.getSalaryData(department, jobTitle, years);
                System.out.println("Salary for employee " + i + ": " + salary);
            } 
            // else{
            //     System.out.println("HR/ADMIN");
            // }
            
            
        }
        // if(payBump.isBefore(today) || payBump.isEqual(today)){

        //     int i = 1;
        //     int staff = Integer.parseInt(db.GET("employees", i, "Salary"));
            
        //     while(db.GET("employees", i, "EmployeeID") != null){       //**Loop for checking every employee**
                
        //         String department = db.GET("employees", i, "Department");
        //         String jobTitle = db.GET("employees", i, "JobTitle");
        //         int years = (int) ChronoUnit.YEARS.between(roleDate, today);

        //         // Get salary
        //         float salary = salaryCalculator.getSalaryData(department, jobTitle, years);
        //         System.out.println("Salary for employee " + i + ": " + salary);

        //         // Update salary in the database
        //         db.UPDATE("employees", i, "Salary", String.valueOf(salary));

        //         i++;
        //     }

        //     payBump = payBump.plusYears(1);
        //     dateString = payBump.format(formatter);
        //     db.UPDATE("control_data", 1, "Data", dateString);
        //     System.out.println("payBump date updated to " + payBump);
        // }
        // else{
        //     System.out.println("payBump date not updated");
        // }
        
    }
}