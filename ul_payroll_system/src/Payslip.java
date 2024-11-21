import java.time.LocalDate;

public class Payslip {
    private int id;
    private LocalDate date;
    private String employeeName;
    private float grossPay;
    private float usc;
    private float prsi;
    private float incomeTax;
    private float netPay;

    // Constructor
    public Payslip(int id, float grossPay, float usc, float prsi, float income, float netPay, String name) {
        this.id = id;
        this.grossPay = grossPay;
        this.incomeTax = income;
        this.employeeName = name;
        this.usc = usc;
        this.prsi = prsi;
        this.netPay = netPay;
        date = LocalDate.now();
    }

    // Accessors
    public int getId() {return id;}
    public String getEmployeeName() {
        return employeeName;
    }
    public float getGrossPay() {
        return grossPay;
    }
    public float getNetPay() {
        return netPay;
    }
    public float getUSC() {return usc;}
    public float getPRSI() {return prsi;}
    public float getIncomeTax() {return incomeTax;}
    public LocalDate getDate() {return date;}
}