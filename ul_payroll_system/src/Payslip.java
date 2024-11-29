import java.time.LocalDate;

public class Payslip {
    private int id;
    private LocalDate date;
    private String employeeName;
    private float grossPay;
    private float usc;
    private float prsi;
    private float incomeTax;
    private float health;
    private float union;
    private float netPay;

    /**
     * Constructor to create a new payslip
     * 
     * @param id       Payslip id
     * @param grossPay Gross pay
     * @param usc      USC paid
     * @param prsi     PRSI paid
     * @param income   Income tax (PAYE) paid
     * @param health   Insurance fees paid
     * @param union    Union fees paid
     * @param netPay   Net pay (Gross pay after all deductions)
     * @param name     Employee's name
     */
    public Payslip(int id, float grossPay, float usc, float prsi, float income, float health, float union, float netPay,
            String name) {
        this.id = id;
        this.grossPay = grossPay;
        this.incomeTax = income;
        this.employeeName = name;
        this.usc = usc;
        this.prsi = prsi;
        this.health = health;
        this.union = union;
        this.netPay = netPay;
        date = LocalDate.now();
    }

    public Payslip() {

    }

    /**
     * Accessor method to retrieve the payslips id
     * 
     * @return payslip id
     */
    public int getId() {
        return id;
    }

    /**
     * Accessor method to return the emplyee's name on the payslip
     * 
     * @return Employe's name on the payslip
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Accessor method to retrieve the gross pay from the payslip
     * 
     * @return gross pay
     */
    public float getGrossPay() {
        return grossPay;
    }

    /**
     * Accessor method to retrieve the net pay from the payslip
     * 
     * @return net pay
     */
    public float getNetPay() {
        return netPay;
    }

    /**
     * Accessor method to retrieve the USC paid from the payslip
     * 
     * @return usc paid
     */
    public float getUSC() {
        return usc;
    }

    /**
     * Accessor method to retrieve the PRSI paid from the payslip
     * 
     * @return PRSI paid
     */
    public float getPRSI() {
        return prsi;
    }

    /**
     * Accessor method to retrieve the Income tax (PAYE) paid from the payslip
     * 
     * @return Income tax (PAYE) paid
     */
    public float getIncomeTax() {
        return incomeTax;
    }

    /**
     * Accessor method to retrieve the Insurance fee paid from the payslip
     * 
     * @return Insurance paid
     */
    public float getHealth() {
        return health;
    }

    /**
     * Accessor method to retrive the Union fees paid from the payslipi
     * 
     * @return Union fees paid
     */
    public float getUnion() {
        return union;
    }

    /**
     * Accessor method to retrive the date the payslip was created on
     * 
     * @return Payslip's date of creation
     */
    public LocalDate getDate() {
        return date;
    }
}