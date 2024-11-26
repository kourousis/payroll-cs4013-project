import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //Changes String from CSV to LocalDate for use
    DBController db = new DBController();

    FullScale fullCalc= new FullScale();
    PartScale partCalc = new PartScale();

    public static void main(String[] args) {
        SalaryUpdate salaryUpdate = new SalaryUpdate();
        salaryUpdate.updateSalaries();
    }

    public void updateSalaries() {

        LocalDate payBump = LocalDate.parse(db.GET("control_data", 1, "Data"), formatter);
            
        LocalDate today = LocalDate.now();

        int totalRows = db.getRowCount("employees");

        if(payBump.isBefore(today) || payBump.isEqual(today)){
            
            for (int i = 1; i <= totalRows; i++){       //**Loop for checking every employee**
                String roleType = db.GET("employees", i, "RoleType");
                String department = db.GET("employees", i, "Department");
                String jobTitle = db.GET("employees", i, "Jobtitle");
                
                //String name = db.GET("employees", i, "Name");
                
                //float salary = Float.parseFloat(db.GET("employees", i, "Salary"));
        
                LocalDate roleDate = LocalDate.parse(db.GET("employees", i, "RoleDate"), formatter);
                int years = (int) ChronoUnit.YEARS.between(roleDate, payBump);
                //System.out.println(years);
        
                if("FULLTIME".equalsIgnoreCase(roleType) || "ADMIN".equalsIgnoreCase(roleType)){
                    //System.out.println(roleType);
                    
                    //System.out.println("Salary for " + name + ": " + salary);
                    years = years + 1;
                    float newSalary = fullCalc.getSalaryData(department, jobTitle, years);
                    //System.out.println("NEW Salary for " + name + ": " + newSalary);
                    db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                }
                else if("PARTTIME".equalsIgnoreCase(roleType)){
                    // System.out.println(roleType);
                    // System.out.println("Salary for " + name + ": " + salary);
                    years++;
                    if(years <= 4){
                    // Get salary
                    float newSalary = partCalc.getSalaryData(department, jobTitle, years);
                    //System.out.println("New Salary for " + name + ": " + newSalary);
                    db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                    }
                } 
                else{
                    db.UPDATE("employees", i, "Salary", "64409");
                    //System.out.println(roleType);
                    //System.out.println(salary);
                }
            }
            payBump = payBump.plusYears(1);
            String dateString = payBump.format(formatter);
            db.UPDATE("control_data", 1, "Data", dateString);
            //System.out.println("payBump date updated to " + payBump);
        }
    }   
}