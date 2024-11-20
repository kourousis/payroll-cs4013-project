public class Payslip {
    private String employeeName;
    private float grossPay;
    private float tax;
    private float netPay;

    // Constructor
    public Payslip(float grossPay, float tax, String name) {
        this.grossPay = grossPay;
        this.tax = tax;
        this.netPay = grossPay - tax;
        this.employeeName = name;
    }

    // Accessors
    public String getEmployeeName() {
        return employeeName;
    }
    public float getGrossPay() {
        return grossPay;
    }
    public float getTax() {
        return tax;
    }
    public float getNetPay() {
        return netPay;
    }
}