import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GrossCalc {

    private int years;
    private SalaryScale salaryScale;

    public GrossCalc(Staff person, int yearinput)
    {
        if (person instanceof FullTimeStaff) {
            salaryScale = new FullScale();
        } else if (person instanceof PartTimeStaff) {
            salaryScale = new PartScale();
        } else {
            System.out.println("Invalid Title");
        }
        this.years = yearinput;
    }

    //calculate gross based on years worked and job title
    public void calculateGrossIncome(String department, String jobTitle) {
        // Gets salary map for department and job title
        float salary = 0;
        if (department.equals("President")) {
                if (jobTitle.equals("PRESIDENT")) {
                    salary = 240716f;
                } else if (jobTitle.equals("VICE-PRESIDENT")) {
                    salary =  184171f;
                } else {
                    System.out.println("Invalid Title");
                }
        } else {
            salary = salaryScale.getSalaryData(department, jobTitle, years);
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
