import java.util.HashMap;
import java.util.Map;

public class SalaryScale {
    private Map<Integer, Float> salaryData = new HashMap<>();
    private String jobTitle;

    public Object getSalaryData(String inDepartment, String inJobTitle) {
        this.jobTitle = inJobTitle;
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
                salaryData = getAcademicSalary();
                break;
            case "EPS":
                salaryData = getEPSSalary();
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
            case "President":
                if (jobTitle.equals("PRESIDENT")) {
                    return 240716f;
                } else if (jobTitle.equals("VICE-PRESIDENT")) {
                    return 184171f;
                } else {
                    System.out.println("Invalid job title");
                    return null;
                }
            default:
                System.out.println("Invalid department");
        }
        if (salaryData != null && !salaryData.isEmpty()) {
            return salaryData;
        }
        System.out.println("No salary data found for " + inDepartment);
        return null;
    }

    private Map<Integer, Float> getHRSalary() {
        return null;
    }

    private Map<Integer, Float> getAdminSalary() {
        return null;
    }

    private Map<Integer, Float> getLibrarySalary() {
        return null;
    }

    private Map<Integer, Float> getAcademicSalary() {
        switch (jobTitle) {
            case "FULL_PROFESSOR":
                Map<Integer, Float> fullProfessor = new HashMap<>();
                fullProfessor.put(1, 140068f);
                fullProfessor.put(2, 148055f);
                fullProfessor.put(3, 156042f);
                fullProfessor.put(4, 164028f);
                fullProfessor.put(5, 172016f);
                fullProfessor.put(6, 177078f);
                return fullProfessor;
            case "PROFESSOR":
                Map<Integer, Float> professor = new HashMap<>();
                professor.put(1, 101447f);
                professor.put(2, 108276f);
                professor.put(3, 115107f);
                professor.put(4, 121935f);
                professor.put(5, 128770f);
                professor.put(6, 135598f);
                return professor;
            case "ASSOCIATE_PROFESSOR_A":
                Map<Integer, Float> associateProfA = new HashMap<>();
                associateProfA.put(1, 87548f);
                associateProfA.put(2, 91018f);
                associateProfA.put(3, 94492f);
                associateProfA.put(4, 97988f);
                associateProfA.put(5, 101461f);
                associateProfA.put(6, 104948f);
                associateProfA.put(7, 108430f);
                associateProfA.put(8, 111916f);
                associateProfA.put(9, 115395f);
                return associateProfA;
            case "ASSOCIATE_PROFESSOR_B":
                Map<Integer, Float> associateProfB = new HashMap<>();
                associateProfB.put(1, 62069f);
                associateProfB.put(2, 73093f);
                associateProfB.put(3, 77006f);
                associateProfB.put(4, 79703f);
                associateProfB.put(5, 83634f);
                associateProfB.put(6, 87604f);
                associateProfB.put(7, 91563f);
                associateProfB.put(8, 95514f);
                associateProfB.put(9, 99471f);
                return associateProfB;
            case "ASSISTANT_PROFESSOR":
                Map<Integer, Float> assistantProf = new HashMap<>();
                assistantProf.put(1, 46955f);
                assistantProf.put(2, 49543f);
                assistantProf.put(3, 51676f);
                assistantProf.put(4, 53644f);
                assistantProf.put(5, 55689f);
                assistantProf.put(6, 57323f);
                assistantProf.put(7, 58990f);
                assistantProf.put(8, 60654f);
                assistantProf.put(9, 62314f);
                assistantProf.put(10, 63965f);
                return assistantProf;
            case "TEACHING_ASSISTANT":
                Map<Integer, Float> teachingAssistant = new HashMap<>();
                teachingAssistant.put(1, 37010f);
                teachingAssistant.put(2, 40061f);
                return teachingAssistant;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    private Map<Integer, Float> getEPSSalary() {
        return null;
    }

    private Map<Integer, Float> getITSalary() {
        return null;
    }

    private Map<Integer, Float> getTechnicalSalary() {
        return null;
    }

    private Map<Integer, Float> getServiceSalary() {
        return null;
    }

    private Map<Integer, Float> getTeachersSalary() {
        return null;
    }

    private Map<Integer, Float> getClinicalSalary() {
        return null;
    }

    private Map<Integer, Float> getULACSalary() {
        return null;
    }
}
