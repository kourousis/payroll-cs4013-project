import java.util.HashMap;
import java.util.Map;

public abstract class SalaryScale {
    protected float salary;
    protected String jobTitle;
    protected int years;
    public abstract float getSalaryData(String department, String jobTitle, int years);
}
