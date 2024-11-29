import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

    SalaryUpdate salaryUpdate;
    DBController db;
    TaxCalc calc;

    public PayrollSystemMenu() {
        in = new Scanner(System.in);
        db = new DBController();
        calc = new TaxCalc();
        salaryUpdate = new SalaryUpdate();
    }

    public void run() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfMonth() == 25) {
            createPayslipEndOfMonth();
        }

        salaryUpdate.updateSalaries();

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
                System.out.println(employeeId);

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

    private void createPayslipEndOfMonth() {
        ArrayList<HashMap<String, String>> employees_csv = db.GET_CSV("employees");

        for (HashMap<String, String> row : employees_csv) {
            String roleType = row.get("RoleType");

            if (!(roleType.equalsIgnoreCase("PARTTIME"))) {
                createPayslipFullTime(row);
            } else {
                createPayslipPartTime(row);
            }
        }
    }

    private void createPayslipFullTime(HashMap<String, String> row) {
        LocalDate date = LocalDate.now();

        String employeeId = row.get("EmployeeID");
        String employeeName = row.get("Name");
        float gross = Float.parseFloat(row.get("Salary"));
        String grossMonthlySalary = String.valueOf(gross / 12);

        String prsi = String.valueOf(calc.getPRSI(gross));
        String usc = String.valueOf(calc.getUSC(gross));
        String paye = String.valueOf(calc.getIncomeTax(gross));
        String union = String.valueOf(calc.getUnion(gross));
        String insurance = String.valueOf(calc.getInsure(gross));

        String netpay = String
                .valueOf(Float.parseFloat(grossMonthlySalary) - Float.parseFloat(prsi) - Float.parseFloat(usc)
                        - Float.parseFloat(paye) - Float.parseFloat(union) - Float.parseFloat(insurance));

        String[] payslipInfo = {
                employeeId,
                date.toString(),
                employeeName,
                grossMonthlySalary,
                usc,
                prsi,
                paye,
                insurance,
                union,
                netpay,
        };

        db.ADD("payslips", payslipInfo);
    }

    private void createPayslipPartTime(HashMap<String, String> row) {
        // Check for pay claim
        int id = Integer.parseInt(row.get("EmployeeID"));
        Map<String, String> date = db.LATEST_ROW("payclaim_" + row.get("EmployeeID"));

        if (date == null || date.isEmpty()) {
            System.out.println("No pay claim found for employee " + row.get("Name"));
            return;
        }
        if (date.get("Date") == null || date.get("Date").isEmpty()) {
            System.out.println("No pay claim found for employee " + row.get("Name"));
            return;
        }

        LocalDate dateClaimed = LocalDate.parse(date.get("Date"));
        LocalDate today = LocalDate.now();

        // Check if pay claim is for the current month
        if (dateClaimed.getMonthValue() == today.getMonthValue()) {
            String strHours = db.GET("payclaim_" + row.get("EmployeeID"), id, "Hours");
            if (strHours == null || strHours.isEmpty()) {
                System.out.println("No pay claim found for employee " + row.get("Name"));
                return;
            }
            int hours = Integer.parseInt(strHours);

            String salary = db.GET("employees", id, "Salary");
            if (salary == null || salary.isEmpty()) {
                System.out.println("No pay claim found for employee " + row.get("Name"));
                return;
            }

            float gross = Float.parseFloat(salary) * hours;

            String employeeId = row.get("EmployeeID");
            String employeeName = row.get("Name");
            String grossMonthlySalary = String.valueOf(gross);

            String prsi = String.valueOf(calc.getPRSI(gross));
            String usc = String.valueOf(calc.getUSC(gross));
            String paye = String.valueOf(calc.getIncomeTax(gross));
            String union = String.valueOf(calc.getUnion(gross));
            String insurance = String.valueOf(calc.getInsure(gross));

            String netpay = String.valueOf(Float.parseFloat(grossMonthlySalary) -
                    (Float.parseFloat(prsi) + Float.parseFloat(usc) + Float.parseFloat(paye) +
                            Float.parseFloat(union) + Float.parseFloat(insurance)));

            String todayStr = today.toString();

            String[] payslipInfo = {
                    employeeId,
                    todayStr,
                    employeeName,
                    grossMonthlySalary,
                    usc,
                    prsi,
                    paye,
                    insurance,
                    union,
                    netpay,
            };

            db.ADD("payslips", payslipInfo);
        } else {
            System.out.println("No pay claim found for employee " + row.get("Name"));
            return;
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
                    promoteEmployee();
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
            System.out.println("A)Check Promotion  B)User-Profile  C)View-Payslips  L)og-Out");

            String command = in.nextLine().toUpperCase();

            if (command == null || command.isEmpty()) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (command) {
                case "A":
                    acceptPromotion();
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

    /**
     * Returns and employee's id from the employees.csv database and authenticates
     * the login details are correct
     * 
     * It authenticates the login details are correct by looping through the csv
     * database in order to match the email and hashed password to what is stored on
     * the csv
     * 
     * @param email    employees email
     * @param password employees password
     * @return returns the employee's id else returns 0 if login details could not
     *         be authenticated
     */
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
                    jobTitle = db.GET("employees", id, "Jobtitle");
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

    /**
     * Ensures data entered while creating a new employee to add to the database are
     * correct and sanitised
     * 
     * @param data array of data needed to create a new employee
     * @return If the details are sanitised correctly
     */
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

        HashMap<String, List<String>> joblist = createJobInfo();

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
            System.out.println("ERROR First and last names must contain only letters. ERROR");
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

        // Validate Department
        if (joblist.get(departmentString).equals(null)) {
            System.out.println();
            System.out.println("ERROR Please enter a valid department ERROR");
            return false;
        }

        // Validate JobTitle
        if (!joblist.get(departmentString).contains(jobtitle)) {
            System.out.println();
            System.out.println("ERROR Please enter a valid Job Title ERROR");
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
                    String latestRow = db.LATEST_ROW("employees").get("EmployeeID");
                    if (latestRow == null) {
                        System.out.println("Error adding employee");
                        return;
                    }
                    if (db.NEW_PAYSLIP(Integer.parseInt(latestRow))) {
                        if (roletype.equals("PARTTIME")) {
                            if (db.NEW_PAYCLAIM(Integer.parseInt(latestRow))) {
                                System.out.println("Employee added successfully");
                            } else {
                                System.out.println("Error adding employee");
                            }
                        } else {
                            System.out.println("Employee added successfully");
                        }
                    }
                } else {
                    System.out.println("Error adding employee");
                }
            } catch (Exception e) {
                System.out.println("Error adding employee: " + e);
            }
        }
    }

    /**
     * Capitalise the first letter of a string
     * 
     * @param input the string that needs the first letter to be capitalised
     * @return the string output with capitalised first letter
     */
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.toLowerCase().substring(1);
    }

    /**
     * Method used to format the street address
     * 
     * First slipts street into parts by spaces
     * 
     * Keeps the house number intact and capitalises the first letter of each
     * subsequent word
     * 
     * Example, 8 james street -> 8 James Street
     * 
     * @param street street address input
     * @return formatted street address
     */
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
            System.out.println("Role Date: " + row.get("RoleDate"));
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

    /**
     * returns the date of the 2nd friday of the month
     * 
     * @param date date object
     * @return returns a date object of the 2nd friday of the month
     */
    private LocalDate getSecondFriday(LocalDate date) {
        LocalDate firstFriday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        return firstFriday.plusWeeks(1);
    }

    private void createPayclaim() {
        LocalDate today = LocalDate.now();
        LocalDate secondFriday = getSecondFriday(today);

        if (today.isAfter(secondFriday)) {
            System.out.println("Pay claim submission deadline has passed for this month.");
            return;
        }

        String[] data = new String[4];
        data[0] = Integer.toString(employeeId);
        data[2] = jobTitle;
        data[3] = LocalDate.now().toString();

        System.out.println("--------------------------------------------------");
        System.out.println("Input hours worked:");
        int input = in.nextInt();
        in.nextLine();

        if (input > 0 && input <= 24) {
            data[1] = Integer.toString(input);

            System.out.println("--------------------------------------------------");
            System.out.println("Are these details correct? Y)es N)o");
            System.out.println("Employee ID: " + data[0] + ", Hours Worked: " + data[1] + ", Job Title: " + data[2]);
            System.out.println("--------------------------------------------------");
            String command = in.nextLine().toUpperCase();

            if (command.equals("Y")) {
                if (data[0] != null && data[1] != null && data[2] != null) {
                    db.ADD("payclaims", data);
                    System.out.println("Payclaim submitted successfully");
                } else {
                    System.out.println("Error submitting payclaim");
                }
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
                    break;
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

    /**
     * Method to create a hashmap of <String, List> of all job titles
     * 
     * @return HashMap<String, List<String>> of all job titles in the university
     */
    private HashMap<String, List<String>> createJobInfo() {
        HashMap<String, List<String>> departmentJobTitles = new HashMap<>();

        // HR Department
        departmentJobTitles.put("HR", List.of("HR_OFFICER"));

        // Admin Department
        departmentJobTitles.put("ADMIN", List.of(
                "ADMIN_ASSISTANT",
                "SENIOR_ADMIN_ASSISTANT",
                "ADMINISTRATOR",
                "SENIOR_ADMINISTRATIVE_OFFICER_III",
                "SENIOR_ADMINISTRATIVE_OFFICER_II",
                "SENIOR_ADMINISTRATIVE_OFFICER_I",
                "SENIOR_EXECUTIVE_ADMINISTRATOR",
                "EXECUTIVE_ADMINISTRATOR",
                "SENIOR_ADMINISTRATOR"));

        // Library Department
        departmentJobTitles.put("LIBRARY", List.of(
                "LIBRARY_ASSISTANT",
                "SENIOR_LIBRARY_ASSISTANT",
                "LIBRARY_MANAGER",
                "SUB_LIBRARIAN",
                "ASSISTANT_LIBRARIAN_2",
                "ASSISTANT_LIBRARIAN_1",
                "LIBRARY_ATTENDANT"));

        // Academic Department
        departmentJobTitles.put("ACADEMIC", List.of(
                "ACADEMIC_COORDINATOR",
                "SENIOR_ACADEMIC_COORDINATOR",
                "ACADEMIC_DIRECTOR",
                "FULL_PROFESSOR",
                "PROFESSOR",
                "ASSOCIATE_PROFESSOR_A",
                "ASSOCIATE_PROFESSOR_B",
                "ASSISTANT_PROFESSOR",
                "TEACHING_ASSISTANT"));

        // EPS Department
        departmentJobTitles.put("EPS", List.of(
                "EPS_OFFICER",
                "SENIOR_EPS_OFFICER",
                "EPS_COORDINATOR",
                "EPS_PORTFOLIO_MANAGER",
                "EPS_CATEGORY_MANAGER",
                "EPS_CATEGORY_SPECIALIST_HIGHER",
                "EPS_CATEGORY_SPECIALIST"));

        // IT Department
        departmentJobTitles.put("IT", List.of(
                "IT_SUPPORT",
                "SENIOR_IT_SUPPORT",
                "IT_MANAGER",
                "ANALYST_PROGRAMMER_3",
                "ANALYST_PROGRAMMER_2",
                "ANALYST_PROGRAMMER_1",
                "SENIOR_COMPUTER_OPERATOR",
                "COMPUTER_OPERATOR",
                "PRINT_OPERATOR_2",
                "PRINT_OPERATOR_1",
                "COMPUTER_LAB_ATTENDANT",
                "TEMPORARY_COMPUTER_ASSISTANT"));

        // Technical Department
        departmentJobTitles.put("TECHNICAL", List.of(
                "CHIEF_TECHNICAL_OFFICER",
                "TECHNICAL_OFFICER",
                "SENIOR_TECHNICAL_OFFICER",
                "SENIOR_LAB_ATTENDANT",
                "LABORATORY_ATTENDANT"));

        // Service Department
        departmentJobTitles.put("SERVICE", List.of(
                "SEN_PORTER_ATTENDANT",
                "PORTER_ATTENDANT",
                "GROUNDS_SUPERVISOR",
                "GROUNDSWORKPERSON",
                "SENIOR_AIDE",
                "MACHINE_ATTENDANT",
                "SERVICE_STAFF",
                "SERVICE_STAFF_SHIFT",
                "PLANT_MAINTENANCE_AIDE",
                "GROUNDS_FOREPERSON"));

        // Teachers Department
        departmentJobTitles.put("TEACHERS", List.of(
                "TEACHING_FELLOW",
                "UNIVERSITY_TEACHER",
                "ASSOCIATE_TEACHER"));

        // Clinical Department
        departmentJobTitles.put("CLINICAL", List.of(
                "TRSRGF",
                "CLINICAL_TUTOR",
                "CLINICAL_FELLOW"));

        // ULAC Department
        departmentJobTitles.put("ULAC", List.of(
                "ASSISTANT_SENIOR_INSTRUCTOR",
                "LEAD_INSTRUCTOR",
                "MULTI_ACTIVITY_INSTRUCTOR_GRADE_1",
                "MULTI_ACTIVITY_INSTRUCTOR_GRADE_2",
                "ASSISTANT_INSTRUCTOR",
                "CO_OP_STUDENTS"));

        // Research Department
        departmentJobTitles.put("RESEARCH", List.of(
                "RESEARCH_ASSISTANT",
                "POST_DOC_RESEARCHER",
                "RESEARCH_FELLOW",
                "SENIOR_RESEARCH_FELLOW"));

        return departmentJobTitles;
    }

    private void promoteEmployee() {
        DBController db = new DBController();
        HashMap<String, List<String>> departmentJobTitles = createJobInfo();

        System.out.println("--------------------------------------------------");
        System.out.println("Enter Employee ID to promote: ");
        String inputId = in.nextLine().trim();

        if (inputId.isEmpty()) {
            System.out.println("Error: Employee ID cannot be empty");
            return;
        }

        // Fetch employee data from the database
        HashMap<String, String> employeeData = db.GET_ROW("employees", Integer.parseInt(inputId), "EmployeeID");

        // Validate employee exists
        if (employeeData == null || employeeData.isEmpty()) {
            System.out.println("Error: No employee found with ID: " + inputId);
            return;
        }

        String department = employeeData.get("Department");
        String currentJobTitle = employeeData.get("Jobtitle");

        // Validate department exists
        if (department == null || department.isEmpty()) {
            System.out.println("Error: No department found for employee");
            return;
        }

        System.out.println("Enter new job title for promotion: ");
        String newJobTitle = in.nextLine().trim().toUpperCase();

        if (newJobTitle.isEmpty()) {
            System.out.println("Error: Job title cannot be empty");
            return;
        }

        List<String> validJobTitles = departmentJobTitles.getOrDefault(department, null);
        if (validJobTitles == null || !validJobTitles.contains(currentJobTitle)) {
            System.out.println("Error: Invalid current job title for department: " + department);
            return;
        }

        if (!validJobTitles.contains(newJobTitle)) {
            System.out.println("Error: Invalid new job title for department: " + department);
            return;
        }

        // Check for existing promotion
        String pendingPromotion = db.GET("pendingPromo", Integer.parseInt(inputId), "EmployeeID");
        if (pendingPromotion != null && !pendingPromotion.isEmpty()) {
            System.out.println("Error: Employee already has a pending promotion request");
            return;
        }

        // Add promotion request
        if (db.ADD("pendingPromo", new String[] { inputId, newJobTitle, "false" })) {
            System.out.println("--------------------------------------------------");
            System.out.println("Success: Promotion request submitted");
            System.out.println("Employee ID: " + inputId);
            System.out.println("New Position: " + newJobTitle);
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("Error: Failed to submit promotion request");
        }
    }

    private void acceptPromotion() {
        DBController db = new DBController();

        // Check for pending promotion
        HashMap<String, String> pendingPromotionData = db.GET_ROW("pendingPromo", employeeId, "");
        if (pendingPromotionData == null || pendingPromotionData.isEmpty()) {
            System.out.println("--------------------------------------------------");
            System.out.println("No pending promotions found.");
            System.out.println("--------------------------------------------------");
            return;
        }

        String newJobTitle = pendingPromotionData.get("newJobTitle");
        if (newJobTitle == null || newJobTitle.isEmpty()) {
            System.out.println("--------------------------------------------------");
            System.out.println("Error: Invalid promotion data found");
            System.out.println("--------------------------------------------------");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Pending Promotion Details:");
        System.out.println("New Position: " + newJobTitle);
        System.out.println("--------------------------------------------------");
        System.out.println("Do you accept this promotion? (Y/N)");

        String userInput = in.nextLine().toUpperCase();
        while (!userInput.equals("Y") && !userInput.equals("N")) {
            System.out.println("Invalid input. Please enter Y or N:");
            userInput = in.nextLine().toUpperCase();
        }

        boolean accepted = userInput.equals("Y");
        boolean success = false;

        if (accepted) {
            String dbCheck = db.UPDATE("employees", employeeId, "Jobtitle", newJobTitle);
            success = dbCheck != null && !dbCheck.isEmpty();

            if (success) {
                System.out.println("--------------------------------------------------");
                System.out.println("Congratulations! Your new position is: " + newJobTitle);
            } else {
                System.out.println("--------------------------------------------------");
                System.out.println("Error: Failed to update job title");
            }
        }

        // Remove pending promotion regardless of acceptance
        if (db.DELETE_ROW("pendingPromo", employeeId)) {
            System.out.println("Pending promotion data removed");
        } else {
            System.out.println("Warning: Failed to remove pending promotion data");
        }

        if (!accepted) {
            System.out.println("--------------------------------------------------");
            System.out.println("Promotion declined");
        }
        System.out.println("--------------------------------------------------");
    }
}
