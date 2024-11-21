import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GrossCalc {

    private int years;


    public GrossCalc(int yearinput) {
        this.years = yearinput;
    }

    //calculate gross based on years worked and job title
    public void calculateGrossIncome(String department, String jobTitle) {
        SalaryScale salaryScale2 = new SalaryScale();

        // Gets salary map for department and job title
        Map<Integer, Float> salaryMap = (Map<Integer, Float>) salaryScale2.getSalaryData(department, jobTitle);

        // Checks salary map for null
        if (salaryMap != null && salaryMap.containsKey(years)) {
            float salary = salaryMap.get(years);

            // finds gross income
            System.out.println("Gross income for " + jobTitle + " in " + department + " department with "
                    + years + " years of experience is: " + salary);

        } else {
            // salary data for specifed job or years worked dont exist
            System.out.println("Salary data not available");
        }
    }
}




