import java.util.Scanner;

public class PayrollSystemMenu {
    private Scanner in;
    String firstName;
    String lastName;
    String department;

    boolean running = true;
    boolean loggedIn = false;

    public PayrollSystemMenu() {
        in = new Scanner(System.in);
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
                System.out.println("F)ull-Time P)art-Time  A)dmin  H)uman Resources");

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
                }

                // Input detection to prevent commas - breaks CSV
                if (detectCommas(firstName) || detectCommas(lastName) || detectCommas(department)) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("One of your inputs contained a comma");
                    System.out.println("Please log in again and refrain from using commas");
                    System.out.println("--------------------------------------------------");
                } else {
                    loggedIn = true;
                }
            }

            // Actions a user can perform if they log in as an X employee
            if (loggedIn && department.equals("PART-TIME")) partTime();
            if (loggedIn && department.equals("FULL-TIME")) fullTime();
            if (loggedIn && department.equals("ADMIN")) Admin();

            // Actions a user can perform if they log in as Human Resources
            else if (loggedIn && department.equals("HR")) {
                HR();
            }
        }
    }

    public void HR() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Update Employee  B)Get Employee  C)Add Employee  D)Get Timesheet  E)Log-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {

            } else if (command.equals("B")) {

            } else if (command.equals("C")) {

            } else if (command.equals("D")) {

            } else if (command.equals("E")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public void Admin() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("E)Log-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {

            } else if (command.equals("B")) {

            } else if (command.equals("C")) {

            } else if (command.equals("D")) {

            } else if (command.equals("E")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public void fullTime() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)User-Profile B)Log-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Get user data
            } else if (command.equals("B")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public void partTime() {
        System.out.println("--------------------------------------------------");
        System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
        System.out.println("--------------------------------------------------");
        System.out.println("A)Make-Payclaim B)ser-Profile C)Log-Out");

        String command = in.nextLine().toUpperCase();

        while (loggedIn && running) {
            if (command.equals("A")) {
                // Make payclaim
            } else if (command.equals("B")) {
                // Get user data
            } else if (command.equals("C")) {
                loggedIn = false;
                System.out.println("--------------------------------------------------");
            }
        }
    }
}
