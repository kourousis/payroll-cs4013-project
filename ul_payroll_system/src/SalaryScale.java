import java.util.HashMap;
import java.util.Map;

public class SalaryScale {
    private Map<String, Map<Integer, Float>> salaryData = new HashMap<>();

    public Map<String, Map<Integer, Float>> getSalaryData(String inDepartment) {
        switch (inDepartment) {
            case "HR":
                salaryData = getHRSalary();
                break;
            case "Admin":
                salaryData = getAdminSalary();
                break;
            case "Library":
                salaryData = getLibrarySalary();
                break;
            case "Academic":
                salaryData = getEPSSalary();
                break;
            case "EPS":
                salaryData = getAcademicSalary();
                break;
            case "IT":
                salaryData = getITSalary();
                break;
            case "Technical":
                salaryData = getTechnicalSalary();
                break;
            case "Service":
                salaryData = getServiceSalary();
                break;
            case "Teachers":
                salaryData = getTeachersSalary();
                break;
            case "Clinical":
                salaryData = getClinicalSalary();
                break;
            case "ULAC":
                salaryData = getULACSalary();
                break;
            default:
                System.out.println("Invalid department");
        }
        if (salaryData != null && !salaryData.isEmpty()) {
            return salaryData;
        }
        System.out.println("No salary data found for " + inDepartment);
        return null;
    }

    private Map<String, Map<Integer, Float>> getHRSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getAdminSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getLibrarySalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getAcademicSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getEPSSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getITSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getTechnicalSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getServiceSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getTeachersSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getClinicalSalary() {
        return null;
    }

    private Map<String, Map<Integer, Float>> getULACSalary() {
        return null;
    }
}
