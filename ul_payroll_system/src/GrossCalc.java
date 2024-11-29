
public class GrossCalc {

    private int years;
    private int hours;
    private SalaryScale salaryScale;

    /**
     * Constructor used to create GrossCalc object
     * 
     * @param person
     * @param yearinput
     * @param hours
     */
    public GrossCalc(Staff person, int yearinput, int hours) {
        if (person instanceof FullTimeStaff) {
            salaryScale = new FullScale();
        } else if (person instanceof PartTimeStaff) {
            salaryScale = new PartScale();
            this.hours = hours;
        } else {
            System.out.println("Invalid Title");
        }
        this.years = yearinput;
    }

    /**
     * Calculates the gross income for an emplyee based on department and job title
     * 
     * @param department employees department
     * @param jobTitle   employees job title
     */
    public void calculateGrossIncome(String department, String jobTitle) {
        // Gets salary map for department and job title
        float salary = 0;
        if (department.equals("President")) {
            if (jobTitle.equals("PRESIDENT")) {
                salary = 240716f;
            } else if (jobTitle.equals("VICE-PRESIDENT")) {
                salary = 184171f;
            } else {
                System.out.println("Invalid Title");
            }
        } else {
            salary = salaryScale.getSalaryData(department, jobTitle, years);
            if (salaryScale instanceof PartScale) {
                salary = salary * hours;
            }
        }
        if (salary == 0) {
            System.out.println("Invalid Title");
            return;
        }

        // finds gross income
        System.out.println("Gross income for " + jobTitle + " in " + department + " department with "
                + years + " years of experience is: " + salary);
    }
}
