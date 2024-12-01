/**
 * The TaxCalc class calculates yearly tax deductions such as PRSI and USC based on an employee's gross salary.
 */
public class TaxCalc {
    /**
     * Default constructor to initialise a TaxCalc object
     */
    public TaxCalc() {}

    /**
     * Method used to calculate the yearly PRSI given the gross salary
     * 
     * @param gross employees gross salary
     * @return value of yearly PRSI
     */
    public float getPRSI(float gross) {
        float total = 0;
        float weekly = gross / 52;
        float credit = 0;
        if (weekly < 424) {
            credit = weekly - 352.01f;
            credit = credit / 6;
            credit = 12 - credit;
        }

        total = (weekly * 0.041f) - credit;
        total = total * 52;

        return total;
    }

    /**
     * Method used to calculate the yearly USC given the gross salary
     * 
     * @param gross employees gross salary
     * @return value of yearly USC
     */
    public float getUSC(float gross) {
        float total = 0.0f;

        if (gross <= 12012) {
            total = 0;
        } else if (gross <= 21295) {
            total = (gross - 12012) * 0.02f;
        } else if (gross <= 70044) {
            total = (gross - 21295) * 0.045f + (21295 - 12012) * 0.02f;
        } else {
            total = (gross - 70044) * 0.08f + (70044 - 21295) * 0.045f + (21295 - 12012) * 0.02f;
        }

        return total;
    }

    /**
     * Method used to the the yearly income tax (PAYE) given the gross salary
     * 
     * @param gross employees gross salary
     * @return value of yearly income tax (PAYE)
     */
    public float getIncomeTax(float gross) {
        float total = 0;
        float taxBand = 42000;

        if (gross <= taxBand) {
            total = gross * 0.02f;
        } else {
            total = ((gross - taxBand) * 0.4f) + (taxBand * 0.2f);
        }
        return total;
    }

    /**
     * Method used to get the yearly union fee given the gross salary
     * 
     * @param gross employees gross salary
     * @return value of yearly union pay
     */
    public float getUnion(float gross) {
        float union = 50;
        float total = union * 12;

        return total;
    }

    /**
     * Method used to get the yearly insurance cost given the gross salary
     * 
     * @param gross employees gross salary
     * @return value of yearly insurance pay
     */
    public float getInsure(float gross) {
        float insure;
        float monthly = gross / 12;

        insure = monthly * 0.05f;
        float total = insure * 12;

        return total;
    }
}
