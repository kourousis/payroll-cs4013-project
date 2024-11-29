import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SalaryUpdate {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Changes String from CSV to LocalDate for
    DBController db = new DBController();
    SalaryScale scale;

    /**
     * Updates the salaries of all employees based on their role type, department,
     * job title, and years of service.
     * 
     * If the salary update date has not yet arrived, the method outputs that the
     * system is up to date.
     */
    public void updateSalaries() {
        LocalDate payBump = LocalDate.parse(db.GET("control_data", 1, "Data"), formatter);
        LocalDate today = LocalDate.now();

        int totalRows = db.GET_ROW_COUNT("employees");

        if (payBump.isBefore(today) || payBump.isEqual(today)) {
            for (int i = 1; i <= totalRows; i++) {
                String roleType = db.GET("employees", i, "RoleType");
                String department = db.GET("employees", i, "Department");
                String jobTitle = db.GET("employees", i, "Jobtitle");

                LocalDate roleDate = LocalDate.parse(db.GET("employees", i, "RoleDate"), formatter);
                int years = (int) ChronoUnit.YEARS.between(roleDate, payBump);
                years++;

                if ("FULLTIME".equalsIgnoreCase(roleType) || "ADMIN".equalsIgnoreCase(roleType)) {
                    scale = new FullScale();
                    years = years + 1;
                    float newSalary = scale.getSalaryData(department, jobTitle, years);
                    db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                } else if ("PARTTIME".equalsIgnoreCase(roleType)) {
                    scale = new PartScale();
                    years++;
                    if (years <= 4) {
                        float newSalary = scale.getSalaryData(department, jobTitle.toUpperCase(), years);
                        db.UPDATE("employees", i, "Salary", String.valueOf(newSalary));
                    }
                } else {
                    db.UPDATE("employees", i, "Salary", "64409");
                }
            }
            payBump = payBump.plusYears(1);
            String dateString = payBump.format(formatter);
            db.UPDATE("control_data", 1, "Data", dateString);
            System.out.println("Salaries have been updated");
        } else {
            System.out.println("System is up to date");
        }
    }
}