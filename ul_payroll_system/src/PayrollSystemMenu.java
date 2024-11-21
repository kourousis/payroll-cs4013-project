import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class PayrollSystemMenu {
    private Scanner in;
    private String firstName;
    private String lastName;
    private String department;
    private String email;
    private String password;

    private int employeeId;

    private boolean running = true;
    private boolean loggedIn = false;

    DBController db;

    public PayrollSystemMenu() {
        in = new Scanner(System.in);
        db = new DBController();
    }

    public String getDepartment() {
        return department;
    }

    private boolean detectCommas(String string) {
        if (string.contains(",")) {
            return true;
        }
        return false;
    }

    public void run() {
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to the UL Payroll System");
        System.out.println("Please Log-in");
        System.out.println("--------------------------------------------------");

        while (running) {
            while (!loggedIn) {
                System.out.print("Email: ");
                email = in.nextLine();

                System.out.print("Password: ");
                password = in.nextLine();

                employeeId = authenticateAndReturnID(email, password);
                //System.out.println("EMPLOYEE ID: " + employeeId);
                
                if (employeeId > 0) {
                    loggedIn = true;
                }
            }

            // Actions a user can perform if they log in as an X employee
            if (department.equals("PartTime"))
                partTime();
             else if (department.equals("Admin"))
                Admin();
            else if (department.equals("HumanResources")) {
                HR();
            } else {
                fullTime();
            }
        }
    }

    private void HR() {
    while (loggedIn && running) {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Promote-Staff  B)View-Payslips  C)User-Profile  L)og-Out");

        String command = in.nextLine().toUpperCase();

        if (command == null || command.isEmpty()) {
            System.out.println("Invalid input. Please try again.");
            continue;
        }

        switch (command) {
            case "A":
                // Call promote method
                break;
            case "B":
                viewPayslip();
                break;
            case "C":
                viewProfile();
                break;
            case "L":
                loggedIn = false;
                System.out.println("--------------------------------------------------");
                break;
            default:
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void Admin() {
        while (loggedIn && running) {
            System.out.println("--------------------------------------------------");
            System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
            System.out.println("--------------------------------------------------");
            System.out.println("A)Add User  B)View-Payslips  C)User-Profile  L)og-Out");

            String command = in.nextLine().toUpperCase();

            if (command == null || command.isEmpty()) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (command) {
                case "A":
                    // Add user
                    break;
                case "B":
                    viewPayslip();
                    break;
                case "C":
                    viewProfile();
                    break;
                case "L":
                    loggedIn = false;
                    System.out.println("--------------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void fullTime() {
    while (loggedIn && running) {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Accept Promotion  B)User-Profile  C)View-Payslips  L)og-Out");

        String command = in.nextLine().toUpperCase();

        if (command == null || command.isEmpty()) {
            System.out.println("Invalid input. Please try again.");
            continue;
        }

        switch (command) {
            case "A":
                // Accept Promotion
                break;
            case "B":
                viewProfile();
                break;
            case "C":
                viewPayslip();
                break;
            case "L":
                loggedIn = false;
                System.out.println("--------------------------------------------------");
                break;
            default:
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void partTime() {
    while (loggedIn && running) {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Make-Payclaim  B)User-Profile  C)View-Payslips L)og-Out");

        String command = in.nextLine().toUpperCase();

        if (command == null || command.isEmpty()) {
            System.out.println("Invalid input. Please try again.");
            continue;
        }

        switch (command) {
            case "A":
                // Make payclaim
                break;
            case "B":
                viewProfile();
                break;
            case "C":
                viewPayslip();
                break;
            case "L":
                loggedIn = false;
                System.out.println("--------------------------------------------------");
                break;
            default:
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private int authenticateAndReturnID(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Email and password cannot be empty");
            return 0;
        }

        try {
            int id = 1;
            boolean emailFound = false;
            String dbEmail;
            
            // Loop until GET returns empty (-1)
            while ((dbEmail = db.GET("employees", id, "Email")) != null && !dbEmail.isEmpty()) {
                if (dbEmail.equals(email)) {
                    emailFound = true;
                    String dbPassword = db.GET("employees", id, "Password");
                    
                    if (dbPassword != null && dbPassword.equals(password)) {
                        department = db.GET("employees", id, "Department");
                        String name = db.GET("employees", id, "Name");
                        if (name != null && name.contains(" ")) {
                            String[] names = name.split(" ");
                            firstName = names[0];
                            lastName = names[1];
                            return id;
                        } else {
                            System.out.println("Invalid name format in database");
                            return 0;
                        }
                    } else {
                        System.out.println("Invalid password");
                        return 0;
                    }
                }
                id++;
            }
            
            if (!emailFound) {
                System.out.println("Email not found");
            }
            return 0;
            
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return 0;
        }
    }

    private void viewProfile() {
        Map<String, String> row;
        try {
            row = db.GET_ROW("employees", employeeId, "");
            System.out.println("--------------------------------------------------");
            System.out.println("Name: " + row.get("Name"));
            System.out.println("Phone: " + row.get("Phone"));
            System.out.println("Email: " + row.get("Email"));
            System.out.println("Address: " + row.get("Address"));
            System.out.println("City: " + row.get("City"));
            System.out.println("County: " + row.get("County"));
            System.out.println("Postcode: " + row.get("Postcode"));
            System.out.println("Country: " + row.get("Country"));
            System.out.println("Department: " + row.get("Department"));
            System.out.println("Salary: " + row.get("Salary"));
            System.out.println("Hire Date: " + row.get("HireDate"));
            System.out.println("--------------------------------------------------");

            while (true) {
                System.out.println("G)o-Back");
                String input = in.nextLine().toUpperCase();

                if (input.equals("G")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }
    }

    private void viewPayslip() {
        System.out.println("--------------------------------------------------");
        System.out.println("L)atest-Payslip  H)istoric-Payslips  G)o-Back");
        String command = in.nextLine().toUpperCase();
        while(true) {
            if (command.equals("L")) {
                // Get latest payslip logic here
                System.out.println("G)o-Back");
                String input = in.nextLine().toUpperCase();
                if (input.equals("G")) {
                    break;
                }
            } else if (command.equals("H")) {
                historicPayslip();
                break;
            } else if (command.equals("G")) {
                break;
            } else {
                System.out.println("Invalid command, please try again");
                command = in.nextLine().toUpperCase();
            }

        }
    }

    private void historicPayslip() {
        while(true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Input Date (YYYY-MM-DD) or G)o Back");
            String input = in.nextLine().toUpperCase();
            
            if (input.equals("G")) {
                break;
            }
            
            if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD");
                continue;
            }
            
            try {
                HashMap<String, String> payslipData = db.GET_ROW("payslips", employeeId, input);
                if (payslipData != null && !payslipData.isEmpty()) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("Payslip for: " + payslipData.get("EmployeeName"));
                    System.out.println("Date: " + payslipData.get("Date"));
                    System.out.println("--------------------------------------------------");
                    System.out.printf("Gross Pay:   €%.2f\n", Float.parseFloat(payslipData.get("GrossPay")));
                    System.out.printf("USC:         €%.2f\n", Float.parseFloat(payslipData.get("USC")));
                    System.out.printf("PRSI:        €%.2f\n", Float.parseFloat(payslipData.get("PRSI")));
                    System.out.printf("Income Tax:  €%.2f\n", Float.parseFloat(payslipData.get("IncomeTax")));
                    System.out.println("--------------------------------------------------");
                    System.out.printf("Net Pay:     €%.2f\n", Float.parseFloat(payslipData.get("NetPay")));
                    System.out.println("--------------------------------------------------");
                } else {
                    System.out.println("No payslip found for " + input);
                }
                
                System.out.println("Press G to go back");
                String input2 = in.nextLine().toUpperCase();
                if (input2.equals("G")) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error processing payslip data");
            }
        }
    }
}
