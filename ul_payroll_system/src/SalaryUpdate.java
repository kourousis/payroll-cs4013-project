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
            
            for (int i = 1; i <= totalRows; i++){
                String roleType = db.GET("employees", i, "RoleType");
                String department = db.GET("employees", i, "Department");
                String jobTitle = db.GET("employees", i, "Jobtitle");
        
                LocalDate roleDate = LocalDate.parse(db.GET("employees", i, "RoleDate"), formatter);
                int years = (int) ChronoUnit.YEARS.between(roleDate, payBump);
        
                if("FULLTIME".equalsIgnoreCase(roleType) || "ADMIN".equalsIgnoreCase(roleType)){

                    years = years + 1;
                    float newSalary = fullCalc.getSalaryData(department, jobTitle, years);
                    db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                }
                else if("PARTTIME".equalsIgnoreCase(roleType)){
                    years++;
                    if(years <= 4){
                    float newSalary = partCalc.getSalaryData(department, jobTitle, years);
                    db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                    }
                } 
                else{
                    db.UPDATE("employees", i, "Salary", "64409");
                }
            }
            payBump = payBump.plusYears(1);
            String dateString = payBump.format(formatter);
            db.UPDATE("control_data", 1, "Data", dateString);
        }
    }   
}