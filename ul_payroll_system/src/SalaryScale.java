public abstract class SalaryScale {
    /**
     * Employee's salary
     */
    protected float salary;
    /*
     * Employee's job title
     */
    protected String jobTitle;
    /**
     * Employee's years of service at UL
     */
    protected int years;

    public abstract float getSalaryData(String department, String jobTitle, int years);
}
