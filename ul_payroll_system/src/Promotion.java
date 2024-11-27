import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Promotion {

    // Static map to store departments and their corresponding job titles
    private static HashMap<String, List<String>> departmentJobTitles;

    static {
        // Initialize the department-job mapping
        departmentJobTitles = new HashMap<>();

        HashMap<Object, Object> departmentJobTitles = new HashMap<>();

// HR Department
        departmentJobTitles.put("HR", Arrays.asList("HR_OFFICER")); // Added HR job title

// Admin Department
        departmentJobTitles.put("Admin", Arrays.asList(
                "ADMIN_ASSISTANT",
                "SENIOR_ADMIN_ASSISTANT",
                "ADMINISTRATOR",
                "SENIOR_ADMINISTRATIVE_OFFICER_III",
                "SENIOR_ADMINISTRATIVE_OFFICER_II",
                "SENIOR_ADMINISTRATIVE_OFFICER_I",
                "SENIOR_EXECUTIVE_ADMINISTRATOR",
                "EXECUTIVE_ADMINISTRATOR",
                "SENIOR_ADMINISTRATOR")); // Added Admin job titles

// Library Department
        departmentJobTitles.put("Library", Arrays.asList(
                "LIBRARY_ASSISTANT",
                "SENIOR_LIBRARY_ASSISTANT",
                "LIBRARY_MANAGER",
                "SUB_LIBRARIAN",
                "ASSISTANT_LIBRARIAN_2",
                "ASSISTANT_LIBRARIAN_1",
                "LIBRARY_ATTENDANT")); // Added Library job titles

// Academic Department
        departmentJobTitles.put("Academic", Arrays.asList(
                "ACADEMIC_COORDINATOR",
                "SENIOR_ACADEMIC_COORDINATOR",
                "ACADEMIC_DIRECTOR",
                "FULL_PROFESSOR",
                "PROFESSOR",
                "ASSOCIATE_PROFESSOR_A",
                "ASSOCIATE_PROFESSOR_B",
                "ASSISTANT_PROFESSOR",
                "TEACHING_ASSISTANT")); // Added Academic job titles

// EPS Department
        departmentJobTitles.put("EPS", Arrays.asList(
                "EPS_OFFICER",
                "SENIOR_EPS_OFFICER",
                "EPS_COORDINATOR",
                "EPS_PORTFOLIO_MANAGER",
                "EPS_CATEGORY_MANAGER",
                "EPS_CATEGORY_SPECIALIST_HIGHER",
                "EPS_CATEGORY_SPECIALIST")); // Added EPS job titles

// IT Department
        departmentJobTitles.put("IT", Arrays.asList(
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
                "TEMPORARY_COMPUTER_ASSISTANT")); // Added IT job titles

// Technical Department
        departmentJobTitles.put("Technical", Arrays.asList(
                "CHIEF_TECHNICAL_OFFICER",
                "TECHNICAL_OFFICER",
                "SENIOR_TECHNICAL_OFFICER",
                "SENIOR_LAB_ATTENDANT",
                "LABORATORY_ATTENDANT")); // No changes here

// Service Department
        departmentJobTitles.put("Service", Arrays.asList(
                "SEN_PORTER_ATTENDANT",
                "PORTER_ATTENDANT",
                "GROUNDS_SUPERVISOR",
                "GROUNDSWORKPERSON",
                "SENIOR_AIDE",
                "MACHINE_ATTENDANT",
                "SERVICE_STAFF",
                "SERVICE_STAFF_SHIFT",
                "PLANT_MAINTENANCE_AIDE",
                "GROUNDS_FOREPERSON")); // No changes here

// Teachers Department
        departmentJobTitles.put("Teachers", Arrays.asList(
                "TEACHING_FELLOW",
                "UNIVERSITY_TEACHER",
                "ASSOCIATE_TEACHER")); // No changes here

// Clinical Department
        departmentJobTitles.put("Clinical", Arrays.asList(
                "TRSRGF",
                "CLINICAL_TUTOR",
                "CLINICAL_FELLOW")); // No changes here

// ULAC Department
        departmentJobTitles.put("ULAC", Arrays.asList(
                "ASSISTANT_SENIOR_INSTRUCTOR",
                "LEAD_INSTRUCTOR",
                "MULTI_ACTIVITY_INSTRUCTOR_GRADE_1",
                "MULTI_ACTIVITY_INSTRUCTOR_GRADE_2",
                "ASSISTANT_INSTRUCTOR",
                "CO_OP_STUDENTS")); // No changes here

// Research Department
        departmentJobTitles.put("Research", Arrays.asList(
                "RESEARCH_ASSISTANT",
                "POST_DOC_RESEARCHER",
                "RESEARCH_FELLOW",
                "SENIOR_RESEARCH_FELLOW")); // No changes here
}

        // Method to promote an employee and add to pendingPromo.csv
    public void promoteEmployee(String employeeIdStr, String newJobTitle) {
        DBController db = new DBController();

        // Convert employeeIdStr to integer
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
}
