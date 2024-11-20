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
                if (employeeId > 0) {
                    loggedIn = true;
                    System.out.println(department);
                }
            }

            // Actions a user can perform if they log in as an X employee
            if (loggedIn) {
                if (department.equals("Part Time"))
                    partTime();
                if (department.equals("Full Time"))
                    fullTime();
                if (department.equals("Admin"))
                    Admin();
                else if (department.equals("Human Resources")) {
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

    private int authenticateAndReturnID(String email, String password) {
        boolean emailFound = false;
        department = null;

        int id = 1;
        while (true) {
            if (Objects.equals(db.GET("employees", id, "Email"), "")
                    || db.GET("employees", id, "Email") == null) break;
            if (db.GET("employees", id, "Email").equals(email)) {
                emailFound = true;

                if (db.GET("employees", id, "Password").equals(password)) {
                    department = db.GET("employees", id, "Department");
                } else {
                    System.out.print("Invalid Password, please try again\n");
                    return 0;
                }
            }
            id++;
        }

        if (!emailFound && department == null) {
            System.out.println("Invalid Email, please try again");
            return 0;
        }
        return id;
    }

    private void viewProfile(String department, int id) {
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
