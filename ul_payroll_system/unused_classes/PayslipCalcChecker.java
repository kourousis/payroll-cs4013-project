// public class PayslipCalcChecker {
//     public static void main(String[] args) {
//         float annualGross = 28828.8f;
//         float monthlyGross = annualGross/12;

//         float monthlyUnion = 50;
//         float annualInsure = annualGross*0.05f;
//         float monthlyInsure = annualInsure/12;

//         float annualPRSI = getPRSI(annualGross);
//         float monthlyPRSI = annualPRSI/12;

//         float annualUSC = getUSC(annualGross);
//         float monthlyUSC = annualUSC/12;

//         float annualIT = getIncomeTax(annualGross);
//         float monthlyIT = annualIT/12;

//         float annualNet = annualGross - (annualInsure + annualIT + annualPRSI + annualUSC + 600);
//         float monthlyNetCalc = annualNet/12;

//         float monthlyNet = monthlyGross - (monthlyInsure + monthlyIT + monthlyPRSI + monthlyUnion + monthlyUSC);

//         System.out.println("Annual Gross: " + annualGross);
//         System.out.println("Annual PRSI: " + annualPRSI);
//         System.out.println("Annual USC: " + annualUSC);
//         System.out.println("Annual IT: " + annualIT);
//         System.out.println("Annual Insure: " + annualInsure);
//         System.out.println("Annual Union: " + monthlyUnion*12);
//         System.out.println("Annual Net: " + annualNet);

//         System.out.println("\nMonthly Gross: " + monthlyGross);
//         System.out.println("Monthly USC: " + monthlyUSC);
//         System.out.println("Monthly PRSI: " + monthlyPRSI);
//         System.out.println("Monthly IT: " + monthlyIT);
//         System.out.println("Monthly Insure: " + monthlyInsure);
//         System.out.println("Monthly Union: " + monthlyUnion);
//         System.out.println("Monhtly Net Calc: " + monthlyNetCalc);
//         System.out.println("Monthly Net: " + monthlyNet);

//         System.out.println("\n" + monthlyGross + "," + monthlyUSC + "," + monthlyPRSI + "," + monthlyIT + "," + monthlyInsure + "," + monthlyUnion + "," + monthlyNet);

//         }
        
//         public static float getPRSI(float gross) {
//             float total = 0;
//             float weekly = gross / 52f;
//             float credit = 0;
//             if (weekly < 424f) {
//                 credit = weekly - 352.01f;
//                 credit = credit / 6f;
//                 credit = 12f - credit;
//             }
    
//             total = (weekly * 0.041f) - credit;
//             total = total * 52;
    
//             return total;
//         }

//         public static float getUSC(float gross) {
//             float total = 0.0f;
    
//             if (gross <= 12012) {
//                 total = 0;
//             } else if (gross <= 21295) {
//                 total = (gross - 12012) * 0.02f;
//             } else if (gross <= 70044) {
//                 total = (gross - 21295) * 0.045f + (21295 - 12012) * 0.02f;
//             } else {
//                 total = (gross - 70044) * 0.08f + (70044 - 21295) * 0.045f + (21295 - 12012) * 0.02f;
//             }
    
//             return total;
//         }
//         public static float getIncomeTax(float gross) {
//             float total = 0;
//             float taxBand = 42000;
    
//             if (gross <= taxBand) {
//                 total = gross * 0.02f;
//             } else {
//                 total = ((gross - taxBand) * 0.4f) + (taxBand * 0.2f);
//             }
//             return total;
//         }
//     }

