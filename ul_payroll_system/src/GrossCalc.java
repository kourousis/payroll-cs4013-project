public class GrossCalc {
    private int years;

    public GrossCalc(int yearinput)
    {
        this.years = yearinput;
    }

    //calculate gross based on years worked and job title
    public void calculateGrossIncome(String department, String jobTitle)
    {
        FullScale salaryScale = new FullScale();

        // Gets salary map for department and job title
        years = 1;
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
