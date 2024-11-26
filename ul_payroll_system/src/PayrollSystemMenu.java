import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PayrollSystemMenu {
    private Scanner in;
    private String firstName;
    private String lastName;
    private String department;
    private String jobTitle;
    private String roleType;
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
                }
            }

            if (roleType.equals("PARTTIME"))
                partTime();
            else if (roleType.equals("ADMIN"))
                Admin();
            else if (roleType.equals("HR")) {
                HR();
            } else if (roleType.equals("FULLTIME")) {
                fullTime();
            } else {
                System.out.println("Invalid role type. Please contact the system administrator.");
                loggedIn = false;
            }
        }
    }

    private void HR() {
        while (loggedIn && running) {
            System.out.println("--------------------------------------------------");
            System.out.println("Logged in as: " + firstName + " " + lastName + " (" + roleType + ")");
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
            System.out.println("Logged in as: " + firstName + " " + lastName + " (" + roleType + ")");
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
                    addUser();
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
            System.out.println("Logged in as: " + firstName + " " + lastName + " (" + roleType + ")");
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
            System.out.println("Logged in as: " + firstName + " " + lastName + " (" + roleType + ")");
            System.out.println("--------------------------------------------------");
            System.out.println("A)Make-Payclaim  B)User-Profile  C)View-Payslips L)og-Out");

            String command = in.nextLine().toUpperCase();

            if (command == null || command.isEmpty()) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (command) {
                case "A":
                    createPayclaim();
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
        boolean emailFound = false;
        department = null;
        int id = 1;

        while (true) {
            String employeeEmail = db.GET("employees", id, "Email");

            // Break if we've reached the end of employees
            if (employeeEmail == null || employeeEmail.equals("")) {
                break;
            }

            // Check if this is the matching email
            if (employeeEmail.equals(email)) {
                emailFound = true;

                // Verify password and get user details
                if (PasswordUtil.checkPassword(password, db.GET("employees", id, "Password"))) {
                    department = db.GET("employees", id, "Department");
                    String name = db.GET("employees", id, "Name");
                    firstName = name.split(" ")[0];
                    lastName = name.split(" ")[1];
                    roleType = db.GET("employees", id, "RoleType");
                    return id; // Return the correct ID immediately when found
                } else {
                    System.out.println("Invalid Password, please try again");
                    return 0;
                }
            }
            id++;
        }

        if (!emailFound) {
            System.out.println("Invalid Email, please try again");
            return 0;
        }

        return 0;
    }

    private boolean sanitiseEmployeeInputData(String[] data) {
        String firstNameString = data[0];
        String lastNameString = data[1];
        String phoneNumberString = data[2];
        String emailString = data[3];
        String passwordString = data[4];
        String streetString = data[5];
        String countyString = data[6];
        String cityString = data[7];
        String postcodeString = data[8];
        String countryString = data[9];
        String salaryString = data[10];
        String departmentString = data[11];
        String jobtitle = data[12];
        String roleType = data[13].toUpperCase();

        // Employee Personal Information Validation

        // Validate if fields contain commas
        for (String field : data) {
            if (field.contains(",")) {
                System.out.println();
                System.out.println("ERROR Fields cannot contain commas. ERROR");
                return false;
            }
        }

        // Validate first name and last name (only letters)
        if (!firstNameString.matches("[A-Za-z]+") || !lastNameString.matches("[A-Za-z]+")) {
            System.out.println();
            System.out.println("ERROR First and last names must contain only letters.");
            return false;
        }

        // Validate phone number (digits only, 9-15 characters)
        if (!phoneNumberString.matches("\\d{9,15}")) {
            System.out.println();
            System.out.println("ERROR Phone number must be 10 to 15 digits. ERROR");
            return false;
        }

        // Validate email format
        if (!emailString.matches("^[\\w.-]+@ul\\.ie$")) {
            System.out.println();
            System.out.println("ERROR Invalid email format. ERROR");
            return false;
        }

        if (passwordString.length() < 6) {
            System.out.println("ERROR Password must be at least 6 characters long. ERROR");
            return false;
        }

        // Employee Address Validation
        if (streetString.isEmpty() || cityString.isEmpty() || postcodeString.isEmpty() || countryString.isEmpty()
                || countyString.isEmpty()) {
            System.out.println();
            System.out.println("ERROR Address fields cannot be empty. ERROR");
            return false;
        }

        // Employee Work Details Validation
        try {
            Float.parseFloat(salaryString);
            if (Float.parseFloat(salaryString) < 1) {
                System.out.println();
                System.out.println("ERROR Salary must be a positive number. ERROR");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("ERROR Salary must be a valid number. ERROR");
            return false;
        }

        // Validate postcode (only letters and numbers, 5-7 characters)
        if (!postcodeString.matches("[A-Za-z0-9]+") || postcodeString.length() < 5 || postcodeString.length() > 7) {
            System.out.println();
            System.out.println("ERROR Postcode must contain only letters and numbers. ERROR");
            return false;
        }

        // Validate department (not empty)
        if (departmentString.isEmpty()) {
            System.out.println();
            System.out.println("ERROR Department cannot be empty. ERROR");
            return false;
        }

        // Validate job title (not empty)
        if (jobtitle.isEmpty()) {
            System.out.println();
            System.out.println("ERROR Job title cannot be empty. ERROR");
            return false;
        }

        // Validate role type (is one of the following: FULLTIME, PARTTIME, ADMIN, HR)
        if (!roleType.equals("FULLTIME") && !roleType.equals("PARTTIME") &&
                !roleType.equals("ADMIN") && !roleType.equals("HR")) {
            System.out.println();
            System.out.println("ERROR Role type cannot be empty. ERROR");
            return false;
        }

        return true;
    }

    private void addUser() {
        System.out.println("--------------------------------------------------");
        System.out.println("Adding New Employee to Payroll");
        System.out.println("Section 1) Employee's Personal Information");
        System.out.println("--------------------------------------------------");

        System.out.print("Employee's First Name (no special characters): ");
        String firstNameString = in.nextLine();

        System.out.print("Employee's Last Name (no special characters): ");
        String lastNameString = in.nextLine();

        System.out.print("Employee's Mobile Phone (10-15 digits): ");
        String phoneNumberString = in.nextLine();

        System.out.print("Employee's Email (must end in @ul.ie): ");
        String emailString = in.nextLine();

        System.out.print("Employee's Password (minimum 6 characters): ");
        String passwordString = in.nextLine();

        System.out.println("--------------------------------------------------");
        System.out.println("Section 2) Employee's Address");
        System.out.println("--------------------------------------------------");

        System.out.print("Street: ");
        String streetString = in.nextLine();

        System.out.print("City: ");
        String cityString = in.nextLine();

        System.out.print("County: ");
        String countyString = in.nextLine();

        System.out.print("Postcode: ");
        String postcodeString = in.nextLine();

        System.out.print("Country: ");
        String countryString = in.nextLine();

        System.out.println("--------------------------------------------------");
        System.out.println("Section 3) Employee's Work Details");
        System.out.println("--------------------------------------------------");

        System.out.print("Employee's Salary: ");
        String salaryString = in.nextLine();

        System.out.print("Employee's Department: ");
        String departmentString = in.nextLine();

        System.out.print("Employee's Job Title: ");
        String jobtitle = in.nextLine();

        System.out.print("Employee's Role Type: ");
        String roletype = in.nextLine();

        String[] employeeData = {
                firstNameString,
                lastNameString,
                phoneNumberString,
                emailString,
                passwordString,
                streetString,
                cityString,
                countyString,
                postcodeString,
                countryString,
                salaryString,
                departmentString,
                jobtitle,
                roletype
        };

        if (sanitiseEmployeeInputData(employeeData)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now();
            String roleDate = date.format(dtf);

            // Format fields that need capitalization
            firstNameString = capitalizeFirstLetter(firstNameString);
            lastNameString = capitalizeFirstLetter(lastNameString);
            cityString = capitalizeFirstLetter(cityString);
            countyString = capitalizeFirstLetter(countyString);
            countryString = capitalizeFirstLetter(countryString);
            departmentString = departmentString.toUpperCase();
            postcodeString = postcodeString.toUpperCase();
            jobtitle = jobtitle.toUpperCase();
            roletype = roletype.toUpperCase();
            streetString = formatStreetAddress(streetString);

            String name = firstNameString + " " + lastNameString;
            String hashedPassword = PasswordUtil.hashPassword(passwordString);

            ArrayList<String> existingEmails = db.GET_COL("employees", "Email");
            if (existingEmails != null && existingEmails.contains(emailString)) {
                System.out.println("Email already exists in the database");
                return;
            }

            String[] employeeDataToAdd = {
                    name,
                    phoneNumberString,
                    emailString,
                    hashedPassword,
                    streetString,
                    cityString,
                    countyString,
                    postcodeString,
                    countryString,
                    salaryString,
                    departmentString,
                    jobtitle,
                    roletype,
                    roleDate,
            };

            try {
                if (db.ADD("employees", employeeDataToAdd)) {
                    if (db.ADD("payslip", new String[Integer.parseInt(Integer.toString(employeeId))])) {
                        System.out.println("Employee added successfully");
                    }
                } else {
                    System.out.println("Error adding employee");
                }
            } catch (Exception e) {

            }
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.toLowerCase().substring(1);
    }

    private String formatStreetAddress(String street) {
        try {
            // Split into parts by spaces
            String[] parts = street.split(" ");
            if (parts.length == 1) {
                return capitalizeFirstLetter(street);
            }

            // Keep house number as is
            StringBuilder formatted = new StringBuilder(parts[0]);

            // Capitalize each remaining word
            for (int i = 1; i < parts.length; i++) {
                formatted.append(" ").append(capitalizeFirstLetter(parts[i]));
            }

            return formatted.toString();

        } catch (Exception e) {
            return capitalizeFirstLetter(street);
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

    private void createPayclaim() {
        String[] data = new String[3];
        data[0] = Integer.toString(employeeId);
        data[2] = jobTitle;

        System.out.println("--------------------------------------------------");
        System.out.println("Input hours worked:");
        int input = in.nextInt();
        in.nextLine(); // Consume the newline char

        if (input > 0 && input <= 24) {
            data[1] = Integer.toString(input);

            System.out.println("--------------------------------------------------");
            System.out.println("Are these details correct? Y)es N)o");
            System.out.println("Employee ID: " + data[0] + ", Hours Worked: " + data[1] + ", Job Title: " + data[2]);
            System.out.println("--------------------------------------------------");
            String command = in.nextLine().toUpperCase();

            if (command.equals("Y")) {
                String table = "payclaim_" + employeeId;
                db.ADD(table, data);
                System.out.println("Payclaim submitted successfully");
            } else if (command.equals("N")) {
                System.out.println("Payclaim cancelled");
            } else {
                System.out.println("Invalid command, payclaim not submitted");
            }
        } else {
            System.out.println("Invalid input, please try again");
        }
    }

    private void viewPayslip() {
        System.out.println("--------------------------------------------------");
        System.out.println("L)atest-Payslip  H)istoric-Payslips  G)o-Back");
        String command = in.nextLine().toUpperCase();
        while (true) {
            if (command.equals("L")) {
                String table = "payslip_" + employeeId;
                HashMap<String, String> payslipData = db.LATEST_ROW(table);
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
                    System.out.println("No payslips found");
                    continue;
                }

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
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Input Date (YYYY-MM-DD) or G)o Back");
            String input = in.nextLine().toUpperCase();

            if (input.equals("G")) {
                break;
            }

            // Regex to check if input is in the format YYYY(d4)-MM(d2)-DD(d2)
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
