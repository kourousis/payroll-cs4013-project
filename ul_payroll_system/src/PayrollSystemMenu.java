import java.util.Map;
import java.util.Scanner;

public class PayrollSystemMenu {
    private Scanner in;
    private String firstName;
    private String lastName;
    private String department;

    private boolean running = true;
    private boolean loggedIn = false;

    public PayrollSystemMenu() {
        in = new Scanner(System.in);
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
                System.out.print("First Name: ");
                firstName = in.nextLine();

                System.out.print("Last Name: ");
                lastName = in.nextLine();

                boolean choosingDepartment = true;
                System.out.print("Department: ");
                System.out.println("F)ull-Time  P)art-Time  A)dmin  H)uman Resources");

                while (choosingDepartment) {
                    department = in.nextLine().toUpperCase();

                    switch (department) {
                        case "P":
                            department = "PART-TIME";
                            choosingDepartment = false;
                            break;
                        case "F":
                            department = "FULL-TIME";
                            choosingDepartment = false;
                            break;
                        case "A":
                            department = "ADMIN";
                            choosingDepartment = false;
                            break;
                        case "H":
                            department = "HR";
                            choosingDepartment = false;
                            break;
                        default:
                            System.out.print("Please pick one of the above departments: ");
                            break;
                    }
                    loggedIn = true;
                }
            }

            // Actions a user can perform if they log in as an X employee
            if (loggedIn) {
                if (department.equals("PART-TIME"))
                    partTime();
                if (department.equals("FULL-TIME"))
                    fullTime();
                if (department.equals("ADMIN"))
                    Admin();
                else if (department.equals("HR")) {
                    HR();
                }
            }
        }
    }

    private void HR() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Promote-Staff  B)Get Timesheet  C)User-Profile  L)og-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Call promote method
            } else if (command.equals("B")) {
                // Get timesheet
            } else if (command.equals("C")) {
                viewProfile(department, 1);
                break;
            } else if (command.equals("L")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private void Admin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Add User  B)View-Timesheet  C)User-Profile  L)og-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Add user
            } else if (command.equals("B")) {
                // Get timesheet
            } else if (command.equals("C")) {
                viewProfile(department, 1);
                break;
            } else if (command.equals("L")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private void fullTime() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Accept Promotion  B)User-Profile  C)View-Timesheet  L)og-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Accept Promotion
            } else if (command.equals("B")) {
                viewProfile(department, 1);
                break;
            } else if (command.equals("C")) {
                // Historic Timesheets
            } else if (command.equals("L")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private void partTime() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Make-Payclaim  B)User-Profile  C)View-Timesheet  L)og-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Make payclaim
            } else if (command.equals("B")) {
                viewProfile(department, 1);
                break;
            } else if (command.equals("C")) {
                // Historic Timesheets
            } else if (command.equals("L")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private void viewProfile(String department, int id) {
        DBController db = new DBController(department);
        Map<String, String> row;
        try {
            row = db.GET_ROW("employees", id);
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
                System.out.println("G)Go-Back");
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
}
