/**
 * Object used to store emplyee's salary, job title and years of service
 */
public abstract class SalaryScale {
    /**
     * Default constructor for SalaryScale object
     */
    public SalaryScale() {
    }

    /**
     * Employee's salary
     */
    protected float salary;
    /**
     * Employee's job title
     */
    protected String jobTitle;
    /**
     * Employee's years of service at UL
     */
    protected int years;

    /**
     * Abstract method to retrieve salary data based on the employee's department,
     * job title, and years of service.
     * 
     * @param department employee's department
     * @param jobTitle   employee's job title
     * @param years      number of years the employee has worked at the university
     * @return calculatated salary for the employee
     */
    public abstract float getSalaryData(String department, String jobTitle, int years);
}
