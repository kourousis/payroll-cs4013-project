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

                System.out.print("Department: ");
                department = in.nextLine().toUpperCase();

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

            if (loggedIn && department.equals("HR")) {
                System.out.println("--------------------------------------------------");
                System.out.println("Logged in as: " + firstName + " " + lastName + " (" + department + ")");
                System.out.println("--------------------------------------------------");
                System.out.println("A)Update  B)Get Employee  C)Add Employee  D)Get Timesheet  E)Log-Out");

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
        }
    }
}
