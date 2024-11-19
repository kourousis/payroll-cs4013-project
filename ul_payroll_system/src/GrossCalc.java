import java.util.Map;

public class GrossCalc {
    private int years;

    public GrossCalc(int yearinput)
    {
        this.years = yearinput;
    }

    //calculate gross based on years worked and job title
    public void calculateGrossIncome(String department,String jobTitle)
    {
        SalaryScale salaryScale = new SalaryScale();

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
            Map<Integer, Float> salaryData = salaryScale.getSalaryData(department,jobTitle);
            if (salaryData != null && salaryData.containsKey(years)) {
                salary = salaryData.get(years);
            } else {;
                System.out.println("Invalid Title");
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
