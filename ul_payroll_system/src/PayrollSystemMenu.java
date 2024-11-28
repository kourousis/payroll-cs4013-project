import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
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
    TaxCalc calc;

    public PayrollSystemMenu() {
        in = new Scanner(System.in);
        db = new DBController();
        calc = new TaxCalc();
    }

    public String getDepartment() {
        return department;
    }

    public void run() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfMonth() == 25) {
            createPayslipEndOfMonth();
        }
        SalaryUpdate salaryUpdate = new SalaryUpdate();

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
        
        String netpay = String.valueOf( Float.parseFloat(grossMonthlySalary) - Float.parseFloat(prsi) - Float.parseFloat(usc) - Float.parseFloat(paye) - Float.parseFloat(union) - Float.parseFloat(insurance));

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

            String netpay = String.valueOf( Float.parseFloat(grossMonthlySalary) -
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
                    String latestRow = db.LATEST_ROW("employees").get("EmployeeID");
                    if (latestRow == null) {
                        System.out.println("Error adding employee");
                        return;
                    }
                    if (db.NEW_PAYSLIP(Integer.parseInt(latestRow))) {
                       if (roletype.equals("PARTTIME")) {
                           if (db.NEW_PAYCLAIM(Integer.parseInt(latestRow))) {
                               System.out.println("Employee added successfully");
                           }  else {
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

    private HashMap<String, List<String>> createJobInfo() {
        HashMap<String, List<String>> departmentJobTitles = new HashMap<>();

        // HR Department
        departmentJobTitles.put("HR", List.of("HR_OFFICER"));

        // Admin Department
        departmentJobTitles.put("Admin", List.of(
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
        departmentJobTitles.put("Library", List.of(
                "LIBRARY_ASSISTANT",
                "SENIOR_LIBRARY_ASSISTANT",
                "LIBRARY_MANAGER",
                "SUB_LIBRARIAN",
                "ASSISTANT_LIBRARIAN_2",
                "ASSISTANT_LIBRARIAN_1",
                "LIBRARY_ATTENDANT"));

        // Academic Department
        departmentJobTitles.put("Academic", List.of(
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
        departmentJobTitles.put("Technical", List.of(
                "CHIEF_TECHNICAL_OFFICER",
                "TECHNICAL_OFFICER",
                "SENIOR_TECHNICAL_OFFICER",
                "SENIOR_LAB_ATTENDANT",
                "LABORATORY_ATTENDANT"));

        // Service Department
        departmentJobTitles.put("Service", List.of(
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
        departmentJobTitles.put("Teachers", List.of(
                "TEACHING_FELLOW",
                "UNIVERSITY_TEACHER",
                "ASSOCIATE_TEACHER"));

        // Clinical Department
        departmentJobTitles.put("Clinical", List.of(
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
        departmentJobTitles.put("Research", List.of(
                "RESEARCH_ASSISTANT",
                "POST_DOC_RESEARCHER",
                "RESEARCH_FELLOW",
                "SENIOR_RESEARCH_FELLOW"));

        return departmentJobTitles;
    }

    private void promoteEmployee(String employeeIdStr, String newJobTitle) {
        DBController db = new DBController();
        HashMap<String, List<String>> departmentJobTitles = createJobInfo();

        int employeeId = Integer.parseInt(employeeIdStr);

        // Fetch employee data from the database (employees.csv)
        HashMap<String, String> employeeData = db.GET_ROW("employees", employeeId, "EmployeeID");

        // Check if employee exists
        if (employeeData == null || employeeData.isEmpty()) {
            System.out.println("No employee found with ID " + employeeIdStr);
            return;
        }

        // Extract department from employee data
        String department = employeeData.get("Department");
        if (department == null || department.isEmpty()) {
            System.out.println("No department found for employee ID " + employeeIdStr);
            return;
        }

        // Fetch the list of valid job titles for the department
        List<String> validJobTitles = departmentJobTitles.getOrDefault(department, null);
        if (validJobTitles == null) {
            System.out.println("No valid job titles found for department " + department);
            return;
        }

        // Check if the new job title exists in the department's job titles
        if (!validJobTitles.contains(newJobTitle)) {
            System.out.println("Can't promote to different department or invalid job title.");
            return;
        }

        // Check if the employee already has a pending promotion
        String dbCheck2 = db.GET("pendingPromo", employeeId, "EmployeeID");
        if (dbCheck2 == null || dbCheck2.isEmpty()) {
            // Add promotion details to the pendingPromo CSV if not found
            if (db.ADD("pendingPromo", new String[]{employeeIdStr, newJobTitle, "false"})) {
                System.out.println("Promotion request sent for employee ID " + employeeIdStr);
            } else {
                System.out.println("Adding promotion failed for employee ID " + employeeIdStr);
            }
        } else {
            // If a pending promotion already exists
            System.out.println("Pending promo found for employee ID " + employeeIdStr);
        }
    }

    private void PromotionPending(String employeeIdStr) {
        DBController db = new DBController();

        int employeeId = Integer.parseInt(employeeIdStr);

        // Check if there is a pending promotion request in pendingPromo.csv
        HashMap<String, String> pendingPromotionData = db.GET_ROW("pendingPromo", employeeId, "EmployeeID");
        if (pendingPromotionData == null || pendingPromotionData.isEmpty()) {
            System.out.println("No pending promotion for employee ID " + employeeIdStr);
            return; // No pending promotion for this employee
        }

        // Ensure that the "Jobtitle" key exists in the map
        if (!pendingPromotionData.containsKey("Jobtitle")) {
            System.out.println("Error: 'Jobtitle' field not found in pending promotion data.");
            return; // Exit if the key doesn't exist
        }

        // The new job title will be assumed to be in the "Jobtitle" field of the pendingPromotion data
        String newJobTitle = pendingPromotionData.get("Jobtitle");

        System.out.println("Do you accept the promotion? (y/n)");
        String userInput = in.nextLine().toLowerCase();

        if (userInput.equals("y")) {
            System.out.println("You have accepted the promotion!");

            // Update the employee's job title in the employee.csv
            String dbCheck = db.UPDATE("employees", employeeId, "Jobtitle", newJobTitle);
            if (dbCheck != null && !dbCheck.isEmpty()) {
                System.out.println("Job title updated to " + newJobTitle + " in employee.csv.");
            } else {
                System.out.println("Failed to update job title in employee.csv.");
            }

            // Remove the pending promotion from the pendingPromo.csv
            String dbCheck2 = db.UPDATE("pendingPromo", employeeId, "EmployeeID", "");
            if (dbCheck2 != null && !dbCheck2.isEmpty()) {
                System.out.println("Promotion data removed from pendingPromo.csv.");
            } else {
                System.out.println("Failed to remove promotion data from pendingPromo.csv.");
            }
            System.out.println("Promotion data removed from pendingPromo.csv.");

        } else if (userInput.equals("n")) {
            // Reject the promotion
            System.out.println("You have rejected the promotion.");

            // Remove the pending promotion from the pendingPromo.csv
            String dbCheck2 = db.UPDATE("pendingPromo", employeeId, "EmployeeID", "");
            if (dbCheck2 != null && !dbCheck2.isEmpty()) {
                System.out.println("Promotion data removed from pendingPromo.csv.");
            } else {
                System.out.println("Failed to remove promotion data from pendingPromo.csv.");
            }

            System.out.println("Promotion data removed from pendingPromo.csv.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }
}
