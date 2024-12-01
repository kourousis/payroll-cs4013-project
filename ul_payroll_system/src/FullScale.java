import java.util.HashMap;
import java.util.Map;

public class FullScale extends SalaryScale {
    /**
     * Default constructor to initialise a FullScale object
     */
    public FullScale() {}

    /**
     * Return salary data given an employee's department, job title and years worked
     * 
     * @param inDepartment employee's department
     * @param inJobTitle   employee's job title
     * @param years        number of years worked
     * @return a salary value of the employee
     */
    public float getSalaryData(String inDepartment, String inJobTitle, int years) {
        this.jobTitle = inJobTitle;
        this.years = years;

        Map<Integer, Float> salaryData = new HashMap<>();
        switch (inDepartment) {
            case "HR":
                salaryData = getHRSalary();
                break;
            case "ADMIN":
                salaryData = getAdminSalary();
                break;
            case "LIBRARY":
                salaryData = getLibrarySalary();
                break;
            case "ACADEMIC":
                salaryData = getAcademicSalary();
                break;
            case "EPS":
                salaryData = getEPSSalary();
                break;
            case "IT":
                salaryData = getITSalary();
                break;
            case "TECHNICAL":
                salaryData = getTechnicalSalary();
                break;
            case "SERVICE":
                salaryData = getServiceSalary();
                break;
            case "TEACHERS":
                salaryData = getTeachersSalary();
                break;
            case "CLINICAL":
                salaryData = getClinicalSalary();
                break;
            case "ULAC":
                salaryData = getULACSalary();
                break;
            case "RESEARCH":
                salaryData = getResearchSalary();
                break;
            case "PRESIDENT":
                salaryData = getPresidentSalary();
                break;
            default:
                System.out.println("Invalid department");
        }

        if (salaryData != null) {
            while (years > 0) {
                if (salaryData.get(years) != null) {
                    return salaryData.get(years);
                }
                years--;
            }
            return salaryData.get(years);
        }

        System.out.println("No salary data found for " + inDepartment);
        return 0f;
    }

    /**
     * Get the presidents and vice presidents salary
     * 
     * @return Map<Integer, Float> of salary:jobTitle mapping
     */
    private Map<Integer, Float> getPresidentSalary() {
        switch (jobTitle) {
            case "PRESIDENT":
                Map<Integer, Float> president = new HashMap<>();
                president.put(1, 240716f);
                return president;
            case "VICE_PRESIDENT":
                Map<Integer, Float> vp = new HashMap<>();
                vp.put(1, 184171f);
                return vp;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return a HR employee's salary
     * 
     * @return float amount of HR employee's salary
     */
    private Map<Integer, Float> getHRSalary() {
        return Map.of(1, 64409f);
    }

    /**
     * Method used to return an Admins salary given their job title
     * 
     * @return A map of salary amount to years for a given Admin job title
     */
    private Map<Integer, Float> getAdminSalary() {
        switch (jobTitle) {
            case "SENIOR_ADMINISTRATIVE_OFFICER_III":
                Map<Integer, Float> seniorAdminOfficerIII = new HashMap<>();
                seniorAdminOfficerIII.put(1, 106800f);
                seniorAdminOfficerIII.put(2, 113990f);
                seniorAdminOfficerIII.put(3, 121181f);
                seniorAdminOfficerIII.put(4, 128369f);
                seniorAdminOfficerIII.put(5, 135565f);
                seniorAdminOfficerIII.put(6, 142758f);
                return seniorAdminOfficerIII;
            case "SENIOR_ADMINISTRATIVE_OFFICER_II":
                Map<Integer, Float> seniorAdminOfficerII = new HashMap<>();
                seniorAdminOfficerII.put(1, 94941f);
                seniorAdminOfficerII.put(2, 98703f);
                seniorAdminOfficerII.put(3, 102487f);
                seniorAdminOfficerII.put(4, 106270f);
                seniorAdminOfficerII.put(5, 110030f);
                seniorAdminOfficerII.put(6, 113810f);
                seniorAdminOfficerII.put(7, 117589f);
                seniorAdminOfficerII.put(8, 121368f);
                seniorAdminOfficerII.put(9, 125140f);
                return seniorAdminOfficerII;
            case "SENIOR_ADMINISTRATIVE_OFFICER_I":
                Map<Integer, Float> seniorAdminOfficerI = new HashMap<>();
                seniorAdminOfficerI.put(1, 67132f);
                seniorAdminOfficerI.put(2, 69712f);
                seniorAdminOfficerI.put(3, 72480f);
                seniorAdminOfficerI.put(4, 75725f);
                seniorAdminOfficerI.put(5, 79088f);
                seniorAdminOfficerI.put(6, 82103f);
                seniorAdminOfficerI.put(7, 86389f);
                seniorAdminOfficerI.put(8, 90697f);
                seniorAdminOfficerI.put(9, 95004f);
                seniorAdminOfficerI.put(10, 99296f);
                seniorAdminOfficerI.put(11, 103583f);
                seniorAdminOfficerI.put(12, 107875f);
                return seniorAdminOfficerI;
            case "SENIOR_EXECUTIVE_ADMINISTRATOR":
                Map<Integer, Float> seniorExecAdmin = new HashMap<>();
                seniorExecAdmin.put(1, 58657f);
                seniorExecAdmin.put(2, 60174f);
                seniorExecAdmin.put(3, 61704f);
                seniorExecAdmin.put(4, 63240f);
                seniorExecAdmin.put(5, 64776f);
                seniorExecAdmin.put(6, 66317f);
                seniorExecAdmin.put(7, 68510f);
                seniorExecAdmin.put(8, 72779f);
                seniorExecAdmin.put(9, 77291f);
                seniorExecAdmin.put(10, 79691f);
                seniorExecAdmin.put(11, 83397f);
                return seniorExecAdmin;
            case "EXECUTIVE_ADMINISTRATOR":
                Map<Integer, Float> execAdmin = new HashMap<>();
                execAdmin.put(1, 44752f);
                execAdmin.put(2, 47050f);
                execAdmin.put(3, 49360f);
                execAdmin.put(4, 51268f);
                execAdmin.put(5, 53218f);
                execAdmin.put(6, 55306f);
                execAdmin.put(7, 57462f);
                execAdmin.put(8, 59557f);
                execAdmin.put(9, 61263f);
                execAdmin.put(10, 62840f);
                execAdmin.put(11, 64409f);
                return execAdmin;
            case "SENIOR_ADMINISTRATOR":
                Map<Integer, Float> seniorAdmin = new HashMap<>();
                seniorAdmin.put(1, 39893f);
                seniorAdmin.put(2, 40785f);
                seniorAdmin.put(3, 42037f);
                seniorAdmin.put(4, 43282f);
                seniorAdmin.put(5, 44546f);
                seniorAdmin.put(6, 45799f);
                seniorAdmin.put(7, 47069f);
                seniorAdmin.put(8, 48336f);
                seniorAdmin.put(9, 49613f);
                seniorAdmin.put(10, 50660f);
                seniorAdmin.put(11, 51944f);
                seniorAdmin.put(12, 53265f);
                return seniorAdmin;
            case "ADMINISTRATOR":
                Map<Integer, Float> admin = new HashMap<>();
                admin.put(1, 26572f);
                admin.put(2, 27495f);
                admin.put(3, 28629f);
                admin.put(4, 29397f);
                admin.put(5, 30060f);
                admin.put(6, 30830f);
                admin.put(7, 31616f);
                admin.put(8, 32491f);
                admin.put(9, 33224f);
                admin.put(10, 34378f);
                admin.put(11, 35541f);
                admin.put(12, 36715f);
                admin.put(13, 37403f);
                admin.put(14, 38396f);
                admin.put(15, 39212f);
                admin.put(16, 39926f);
                admin.put(17, 40912f);
                admin.put(18, 41892f);
                return admin;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an Librarians salary given their job title
     * 
     * @return A map of salary amount to years for a given Librarian job title
     */
    private Map<Integer, Float> getLibrarySalary() {
        switch (jobTitle) {
            case "SUB_LIBRARIAN":
                Map<Integer, Float> subLibrarian = new HashMap<>();
                subLibrarian.put(1, 64830f);
                subLibrarian.put(2, 68413f);
                subLibrarian.put(3, 71505f);
                subLibrarian.put(4, 74734f);
                subLibrarian.put(5, 77824f);
                subLibrarian.put(6, 80106f);
                subLibrarian.put(7, 83065f);
                subLibrarian.put(8, 86613f);
                subLibrarian.put(9, 90308f);
                subLibrarian.put(10, 93715f);
                subLibrarian.put(11, 97127f);
                subLibrarian.put(12, 100531f);
                subLibrarian.put(13, 102234f);
                subLibrarian.put(14, 104052f);
                subLibrarian.put(15, 106850f);
                return subLibrarian;
            case "ASSISTANT_LIBRARIAN_2":
                Map<Integer, Float> assistantLibrarian2 = new HashMap<>();
                assistantLibrarian2.put(1, 58657f);
                assistantLibrarian2.put(2, 60174f);
                assistantLibrarian2.put(3, 61704f);
                assistantLibrarian2.put(4, 63240f);
                assistantLibrarian2.put(5, 64776f);
                assistantLibrarian2.put(6, 66317f);
                assistantLibrarian2.put(7, 68510f);
                assistantLibrarian2.put(8, 72779f);
                assistantLibrarian2.put(9, 77291f);
                assistantLibrarian2.put(10, 79691f);
                assistantLibrarian2.put(11, 83397f);
                return assistantLibrarian2;
            case "ASSISTANT_LIBRARIAN_1":
                Map<Integer, Float> assistantLibrarian1 = new HashMap<>();
                assistantLibrarian1.put(1, 44751f);
                assistantLibrarian1.put(2, 47050f);
                assistantLibrarian1.put(3, 49364f);
                assistantLibrarian1.put(4, 51269f);
                assistantLibrarian1.put(5, 53605f);
                assistantLibrarian1.put(6, 55126f);
                assistantLibrarian1.put(7, 56632f);
                assistantLibrarian1.put(8, 57745f);
                assistantLibrarian1.put(9, 59104f);
                assistantLibrarian1.put(10, 60468f);
                assistantLibrarian1.put(11, 61415f);
                assistantLibrarian1.put(12, 62371f);
                assistantLibrarian1.put(13, 64409f);
                assistantLibrarian1.put(14, 66805f);
                assistantLibrarian1.put(15, 69293f);
                return assistantLibrarian1;
            case "SENIOR_LIBRARY_ASSISTANT":
                Map<Integer, Float> seniorLibraryAssistant = new HashMap<>();
                seniorLibraryAssistant.put(1, 39337f);
                seniorLibraryAssistant.put(2, 39808f);
                seniorLibraryAssistant.put(3, 40882f);
                seniorLibraryAssistant.put(4, 41881f);
                seniorLibraryAssistant.put(5, 42439f);
                seniorLibraryAssistant.put(6, 44325f);
                seniorLibraryAssistant.put(7, 45156f);
                seniorLibraryAssistant.put(8, 46242f);
                seniorLibraryAssistant.put(9, 47587f);
                seniorLibraryAssistant.put(10, 48932f);
                seniorLibraryAssistant.put(11, 50539f);
                seniorLibraryAssistant.put(12, 51945f);
                seniorLibraryAssistant.put(13, 53657f);
                return seniorLibraryAssistant;
            case "LIBRARY_ASSISTANT":
                Map<Integer, Float> libraryAssistant = new HashMap<>();
                libraryAssistant.put(1, 33703f);
                libraryAssistant.put(2, 35429f);
                libraryAssistant.put(3, 36934f);
                libraryAssistant.put(4, 37881f);
                libraryAssistant.put(5, 39133f);
                libraryAssistant.put(6, 40055f);
                libraryAssistant.put(7, 41318f);
                libraryAssistant.put(8, 43868f);
                libraryAssistant.put(9, 45138f);
                libraryAssistant.put(10, 45798f);
                return libraryAssistant;
            case "LIBRARY_ATTENDANT":
                Map<Integer, Float> libraryAttendant = new HashMap<>();
                libraryAttendant.put(1, 33403f);
                libraryAttendant.put(2, 34054f);
                libraryAttendant.put(3, 36605f);
                libraryAttendant.put(4, 36966f);
                libraryAttendant.put(5, 36989f);
                libraryAttendant.put(6, 37352f);
                libraryAttendant.put(7, 37714f);
                libraryAttendant.put(8, 38072f);
                libraryAttendant.put(9, 38428f);
                libraryAttendant.put(10, 38443f);
                libraryAttendant.put(11, 38970f);
                libraryAttendant.put(12, 39512f);
                libraryAttendant.put(13, 40039f);
                libraryAttendant.put(14, 40230f);
                return libraryAttendant;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an Academic Staff's salary given their job title
     * 
     * @return A map of salary amount to years for a given Academic Staff's job
     *         title
     */
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

    /**
     * Method used to return an EPS' salary given their job title
     * 
     * @return A map of salary amount to years for a given EPS' job title
     */
    private Map<Integer, Float> getEPSSalary() {
        switch (jobTitle) {
            case "EPS_PORTFOLIO_MANAGER":
                Map<Integer, Float> epsPortfolioManager = new HashMap<>();
                epsPortfolioManager.put(1, 86389f);
                epsPortfolioManager.put(2, 90698f);
                epsPortfolioManager.put(3, 95005f);
                epsPortfolioManager.put(4, 99296f);
                epsPortfolioManager.put(5, 103583f);
                epsPortfolioManager.put(6, 107874f);
                return epsPortfolioManager;
            case "EPS_CATEGORY_MANAGER":
                Map<Integer, Float> epsCategoryManager = new HashMap<>();
                epsCategoryManager.put(1, 67131f);
                epsCategoryManager.put(2, 69712f);
                epsCategoryManager.put(3, 72480f);
                epsCategoryManager.put(4, 75725f);
                epsCategoryManager.put(5, 79087f);
                epsCategoryManager.put(6, 82103f);
                epsCategoryManager.put(7, 86389f);
                epsCategoryManager.put(8, 90698f);
                return epsCategoryManager;
            case "EPS_CATEGORY_SPECIALIST_HIGHER":
                Map<Integer, Float> epsCategorySpecialistHigher = new HashMap<>();
                epsCategorySpecialistHigher.put(1, 58656f);
                epsCategorySpecialistHigher.put(2, 60174f);
                epsCategorySpecialistHigher.put(3, 61704f);
                epsCategorySpecialistHigher.put(4, 63240f);
                epsCategorySpecialistHigher.put(5, 64775f);
                epsCategorySpecialistHigher.put(6, 66318f);
                epsCategorySpecialistHigher.put(7, 68510f);
                epsCategorySpecialistHigher.put(8, 72779f);
                return epsCategorySpecialistHigher;
            case "EPS_CATEGORY_SPECIALIST":
                Map<Integer, Float> epsCategorySpecialist = new HashMap<>();
                epsCategorySpecialist.put(1, 44752f);
                epsCategorySpecialist.put(2, 47047f);
                epsCategorySpecialist.put(3, 49360f);
                epsCategorySpecialist.put(4, 51269f);
                epsCategorySpecialist.put(5, 53219f);
                epsCategorySpecialist.put(6, 55306f);
                epsCategorySpecialist.put(7, 57462f);
                epsCategorySpecialist.put(8, 59558f);
                return epsCategorySpecialist;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an IT Staff's salary given their job title
     * 
     * @return A map of salary amount to years for a given IT Staff's job title
     */
    private Map<Integer, Float> getITSalary() {
        switch (jobTitle) {
            case "ANALYST_PROGRAMMER_3":
                Map<Integer, Float> analystProgrammer3 = new HashMap<>();
                analystProgrammer3.put(1, 67132f);
                analystProgrammer3.put(2, 79088f);
                analystProgrammer3.put(3, 82103f);
                analystProgrammer3.put(4, 86389f);
                analystProgrammer3.put(5, 90697f);
                analystProgrammer3.put(6, 95004f);
                analystProgrammer3.put(7, 99296f);
                analystProgrammer3.put(8, 103583f);
                analystProgrammer3.put(9, 107875f);
                return analystProgrammer3;
            case "ANALYST_PROGRAMMER_2":
                Map<Integer, Float> analystProgrammer2 = new HashMap<>();
                analystProgrammer2.put(1, 53677f);
                analystProgrammer2.put(2, 55287f);
                analystProgrammer2.put(3, 56903f);
                analystProgrammer2.put(4, 58501f);
                analystProgrammer2.put(5, 60116f);
                analystProgrammer2.put(6, 61717f);
                analystProgrammer2.put(7, 64375f);
                analystProgrammer2.put(8, 66805f);
                analystProgrammer2.put(9, 69293f);
                return analystProgrammer2;
            case "ANALYST_PROGRAMMER_1":
                Map<Integer, Float> analystProgrammer1 = new HashMap<>();
                analystProgrammer1.put(1, 42012f);
                analystProgrammer1.put(2, 44629f);
                analystProgrammer1.put(3, 46172f);
                analystProgrammer1.put(4, 47611f);
                analystProgrammer1.put(5, 49079f);
                analystProgrammer1.put(6, 50543f);
                analystProgrammer1.put(7, 52039f);
                analystProgrammer1.put(8, 53562f);
                analystProgrammer1.put(9, 56116f);
                analystProgrammer1.put(10, 58164f);
                return analystProgrammer1;
            case "SENIOR_COMPUTER_OPERATOR":
                Map<Integer, Float> seniorComputerOperator = new HashMap<>();
                seniorComputerOperator.put(1, 42214f);
                seniorComputerOperator.put(2, 43823f);
                seniorComputerOperator.put(3, 45475f);
                seniorComputerOperator.put(4, 46768f);
                seniorComputerOperator.put(5, 48067f);
                seniorComputerOperator.put(6, 49369f);
                seniorComputerOperator.put(7, 50686f);
                seniorComputerOperator.put(8, 51968f);
                seniorComputerOperator.put(9, 53657f);
                seniorComputerOperator.put(10, 55570f);
                return seniorComputerOperator;
            case "COMPUTER_OPERATOR":
                Map<Integer, Float> computerOperator = new HashMap<>();
                computerOperator.put(1, 33403f);
                computerOperator.put(2, 35114f);
                computerOperator.put(3, 36604f);
                computerOperator.put(4, 37881f);
                computerOperator.put(5, 39133f);
                computerOperator.put(6, 40054f);
                computerOperator.put(7, 41318f);
                computerOperator.put(8, 42586f);
                computerOperator.put(9, 43868f);
                computerOperator.put(10, 45138f);
                return computerOperator;
            case "PRINT_OPERATOR_2":
                Map<Integer, Float> printOperator2 = new HashMap<>();
                printOperator2.put(1, 31238f);
                printOperator2.put(2, 32320f);
                printOperator2.put(3, 33844f);
                printOperator2.put(4, 35403f);
                printOperator2.put(5, 36946f);
                printOperator2.put(6, 38331f);
                printOperator2.put(7, 39365f);
                printOperator2.put(8, 40749f);
                printOperator2.put(9, 42093f);
                printOperator2.put(10, 43223f);
                printOperator2.put(11, 44373f);
                return printOperator2;
            case "PRINT_OPERATOR_1":
                Map<Integer, Float> printOperator1 = new HashMap<>();
                printOperator1.put(1, 29021f);
                printOperator1.put(2, 30996f);
                printOperator1.put(3, 31738f);
                printOperator1.put(4, 32768f);
                printOperator1.put(5, 34232f);
                printOperator1.put(6, 35719f);
                printOperator1.put(7, 37208f);
                printOperator1.put(8, 38553f);
                return printOperator1;
            case "COMPUTER_LAB_ATTENDANT":
                Map<Integer, Float> computerLabAttendant = new HashMap<>();
                computerLabAttendant.put(1, 33403f);
                computerLabAttendant.put(2, 34543f);
                computerLabAttendant.put(3, 36605f);
                computerLabAttendant.put(4, 36967f);
                computerLabAttendant.put(5, 36987f);
                computerLabAttendant.put(6, 37353f);
                computerLabAttendant.put(7, 37714f);
                computerLabAttendant.put(8, 38072f);
                computerLabAttendant.put(9, 38428f);
                return computerLabAttendant;
            case "TEMPORARY_COMPUTER_ASSISTANT":
                Map<Integer, Float> temporaryComputerAssistant = new HashMap<>();
                temporaryComputerAssistant.put(1, 33403f);
                temporaryComputerAssistant.put(2, 34543f);
                temporaryComputerAssistant.put(3, 36605f);
                temporaryComputerAssistant.put(4, 36967f);
                temporaryComputerAssistant.put(5, 36987f);
                temporaryComputerAssistant.put(6, 37353f);
                temporaryComputerAssistant.put(7, 37714f);
                temporaryComputerAssistant.put(8, 38072f);
                temporaryComputerAssistant.put(9, 38428f);
                return temporaryComputerAssistant;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an Technician's salary given their job title
     * 
     * @return A map of salary amount to years for a given Technician's job title
     */
    private Map<Integer, Float> getTechnicalSalary() {
        switch (jobTitle) {
            case "CHIEF_TECHNICAL_OFFICER":
                Map<Integer, Float> chiefTechnicalOfficer = new HashMap<>();
                chiefTechnicalOfficer.put(1, 64838f);
                chiefTechnicalOfficer.put(2, 67536f);
                chiefTechnicalOfficer.put(3, 70205f);
                chiefTechnicalOfficer.put(4, 72948f);
                chiefTechnicalOfficer.put(5, 75821f);
                chiefTechnicalOfficer.put(6, 78621f);
                return chiefTechnicalOfficer;
            case "TECHNICAL_OFFICER":
                Map<Integer, Float> technicalOfficer = new HashMap<>();
                technicalOfficer.put(1, 39828f);
                technicalOfficer.put(2, 41606f);
                technicalOfficer.put(3, 43745f);
                technicalOfficer.put(4, 45033f);
                technicalOfficer.put(5, 46408f);
                technicalOfficer.put(6, 49325f);
                technicalOfficer.put(7, 51251f);
                technicalOfficer.put(8, 53228f);
                technicalOfficer.put(9, 55376f);
                technicalOfficer.put(10, 57459f);
                return technicalOfficer;
            case "SENIOR_TECHNICAL_OFFICER":
                Map<Integer, Float> seniorTechnicalOfficer = new HashMap<>();
                seniorTechnicalOfficer.put(11, 60654f);
                seniorTechnicalOfficer.put(12, 62799f);
                seniorTechnicalOfficer.put(13, 65107f);
                seniorTechnicalOfficer.put(14, 67470f);
                seniorTechnicalOfficer.put(15, 69827f);
                seniorTechnicalOfficer.put(16, 71290f); // Long Service Increment - 3 yrs
                return seniorTechnicalOfficer;
            case "SENIOR_LAB_ATTENDANT":
                Map<Integer, Float> seniorLabAttendant = new HashMap<>();
                seniorLabAttendant.put(1, 38443f);
                seniorLabAttendant.put(2, 38970f);
                seniorLabAttendant.put(3, 39512f);
                seniorLabAttendant.put(4, 40040f);
                seniorLabAttendant.put(5, 40229f);
                return seniorLabAttendant;
            case "LABORATORY_ATTENDANT":
                Map<Integer, Float> laboratoryAttendant = new HashMap<>();
                laboratoryAttendant.put(1, 33403f);
                laboratoryAttendant.put(2, 34543f);
                laboratoryAttendant.put(3, 36604f);
                laboratoryAttendant.put(4, 36966f);
                laboratoryAttendant.put(5, 36989f);
                laboratoryAttendant.put(6, 37352f);
                laboratoryAttendant.put(7, 37714f);
                laboratoryAttendant.put(8, 38072f);
                laboratoryAttendant.put(9, 38429f);
                return laboratoryAttendant;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return a Servie worker's salary given their job title
     * 
     * @return A map of salary amount to years for a given Service worker's job
     *         title
     */
    private Map<Integer, Float> getServiceSalary() {
        switch (jobTitle) {
            case "SEN_PORTER_ATTENDANT":
                Map<Integer, Float> senPorterAttendant = new HashMap<>();
                senPorterAttendant.put(1, 41472f);
                senPorterAttendant.put(2, 41678f);
                senPorterAttendant.put(3, 41873f);
                senPorterAttendant.put(4, 41984f);
                senPorterAttendant.put(5, 42103f);
                senPorterAttendant.put(6, 42213f);
                senPorterAttendant.put(7, 42325f);
                senPorterAttendant.put(8, 42445f);
                senPorterAttendant.put(9, 42563f);
                senPorterAttendant.put(10, 42689f);
                senPorterAttendant.put(11, 42813f);
                senPorterAttendant.put(12, 42936f);
                senPorterAttendant.put(13, 43069f);
                return senPorterAttendant;
            case "PORTER_ATTENDANT":
                Map<Integer, Float> porterAttendant = new HashMap<>();
                porterAttendant.put(1, 36456f);
                porterAttendant.put(2, 37227f);
                porterAttendant.put(3, 39454f);
                porterAttendant.put(4, 39644f);
                porterAttendant.put(5, 39821f);
                porterAttendant.put(6, 39919f);
                porterAttendant.put(7, 40032f);
                porterAttendant.put(8, 40032f);
                porterAttendant.put(9, 40032f);
                porterAttendant.put(10, 40032f);
                porterAttendant.put(11, 40099f);
                porterAttendant.put(12, 40214f);
                porterAttendant.put(13, 40327f);
                porterAttendant.put(14, 40440f);
                porterAttendant.put(15, 40552f);
                return porterAttendant;
            case "GROUNDS_SUPERVISOR":
                Map<Integer, Float> groundsSupervisor = new HashMap<>();
                groundsSupervisor.put(1, 40712f);
                groundsSupervisor.put(2, 40895f);
                groundsSupervisor.put(3, 41086f);
                groundsSupervisor.put(4, 41188f);
                groundsSupervisor.put(5, 41295f);
                groundsSupervisor.put(6, 41402f);
                groundsSupervisor.put(7, 41498f);
                groundsSupervisor.put(8, 41609f);
                groundsSupervisor.put(9, 41720f);
                groundsSupervisor.put(10, 41828f);
                groundsSupervisor.put(11, 41944f);
                groundsSupervisor.put(12, 42060f);
                groundsSupervisor.put(13, 42165f);
                return groundsSupervisor;
            case "GROUNDSWORKPERSON":
                Map<Integer, Float> groundsworkperson = new HashMap<>();
                groundsworkperson.put(1, 35063f);
                groundsworkperson.put(2, 35795f);
                groundsworkperson.put(3, 38451f);
                groundsworkperson.put(4, 38637f);
                groundsworkperson.put(5, 38810f);
                groundsworkperson.put(6, 38906f);
                groundsworkperson.put(7, 39009f);
                groundsworkperson.put(8, 39104f);
                groundsworkperson.put(9, 39206f);
                groundsworkperson.put(10, 39304f);
                groundsworkperson.put(11, 39415f);
                groundsworkperson.put(12, 39522f);
                groundsworkperson.put(13, 39632f);
                groundsworkperson.put(14, 39744f);
                groundsworkperson.put(15, 39861f);
                return groundsworkperson;
            case "SENIOR_AIDE":
                Map<Integer, Float> seniorAide = new HashMap<>();
                seniorAide.put(1, 40827f);
                seniorAide.put(2, 41026f);
                seniorAide.put(3, 41209f);
                seniorAide.put(4, 41317f);
                seniorAide.put(5, 41420f);
                seniorAide.put(6, 41534f);
                seniorAide.put(7, 41635f);
                seniorAide.put(8, 41745f);
                seniorAide.put(9, 41859f);
                seniorAide.put(10, 41975f);
                seniorAide.put(11, 42089f);
                seniorAide.put(12, 42206f);
                seniorAide.put(13, 42318f);
                return seniorAide;
            case "MACHINE_ATTENDANT":
                Map<Integer, Float> machineAttendant = new HashMap<>();
                machineAttendant.put(1, 35829f);
                machineAttendant.put(2, 36585f);
                machineAttendant.put(3, 39300f);
                machineAttendant.put(4, 39493f);
                machineAttendant.put(5, 39671f);
                machineAttendant.put(6, 39864f);
                machineAttendant.put(7, 39874f);
                machineAttendant.put(8, 39979f);
                machineAttendant.put(9, 40085f);
                machineAttendant.put(10, 40085f);
                machineAttendant.put(11, 40085f);
                machineAttendant.put(12, 40085f);
                machineAttendant.put(13, 40172f);
                machineAttendant.put(14, 40286f);
                machineAttendant.put(15, 40401f);
                return machineAttendant;
            case "SERVICE_STAFF":
                Map<Integer, Float> serviceStaff = new HashMap<>();
                serviceStaff.put(1, 35974f);
                serviceStaff.put(2, 36736f);
                serviceStaff.put(3, 39461f);
                serviceStaff.put(4, 39657f);
                serviceStaff.put(5, 39836f);
                serviceStaff.put(6, 39933f);
                serviceStaff.put(7, 40040f);
                serviceStaff.put(8, 40040f);
                serviceStaff.put(9, 40040f);
                serviceStaff.put(10, 40040f);
                serviceStaff.put(11, 40113f);
                serviceStaff.put(12, 40226f);
                serviceStaff.put(13, 40336f);
                serviceStaff.put(14, 40451f);
                serviceStaff.put(15, 40566f);
                return serviceStaff;
            case "SERVICE_STAFF_SHIFT":
                Map<Integer, Float> serviceStaffShift = new HashMap<>();
                serviceStaffShift.put(1, 37129f);
                serviceStaffShift.put(2, 37898f);
                serviceStaffShift.put(3, 40388f);
                serviceStaffShift.put(4, 40578f);
                serviceStaffShift.put(5, 40761f);
                serviceStaffShift.put(6, 40861f);
                serviceStaffShift.put(7, 40973f);
                serviceStaffShift.put(8, 41074f);
                serviceStaffShift.put(9, 41179f);
                serviceStaffShift.put(10, 41284f);
                serviceStaffShift.put(11, 41392f);
                serviceStaffShift.put(12, 41506f);
                serviceStaffShift.put(13, 41613f);
                serviceStaffShift.put(14, 41733f);
                serviceStaffShift.put(15, 41846f);
                return serviceStaffShift;
            case "PLANT_MAINTENANCE_AIDE":
                Map<Integer, Float> plantMaintenanceAide = new HashMap<>();
                plantMaintenanceAide.put(1, 40053f);
                plantMaintenanceAide.put(2, 43995f);
                return plantMaintenanceAide;
            case "GROUNDS_FOREPERSON":
                Map<Integer, Float> groundsForeperson = new HashMap<>();
                groundsForeperson.put(1, 49333f);
                groundsForeperson.put(2, 50369f);
                groundsForeperson.put(3, 51204f);
                groundsForeperson.put(4, 52137f);
                groundsForeperson.put(5, 53117f);
                groundsForeperson.put(6, 54078f);
                return groundsForeperson;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an Techedr's salary given their job title
     * 
     * @return A map of salary amount to years for a given Teacher's job title
     */
    private Map<Integer, Float> getTeachersSalary() {
        switch (jobTitle) {
            case "TEACHING_FELLOW":
                Map<Integer, Float> teachingFellow = new HashMap<>();
                teachingFellow.put(1, 62069f);
                teachingFellow.put(2, 73092f);
                teachingFellow.put(3, 77007f);
                teachingFellow.put(4, 79703f);
                teachingFellow.put(5, 83634f);
                teachingFellow.put(6, 87605f);
                teachingFellow.put(7, 91562f);
                teachingFellow.put(8, 95514f);
                teachingFellow.put(9, 99471f);
                return teachingFellow;
            case "UNIVERSITY_TEACHER":
                Map<Integer, Float> universityTeacher = new HashMap<>();
                universityTeacher.put(1, 46955f);
                universityTeacher.put(2, 49543f);
                universityTeacher.put(3, 51676f);
                universityTeacher.put(4, 53644f);
                universityTeacher.put(5, 55689f);
                universityTeacher.put(6, 57323f);
                universityTeacher.put(7, 58990f);
                universityTeacher.put(8, 60654f);
                universityTeacher.put(9, 62314f);
                universityTeacher.put(10, 63965f);
                return universityTeacher;
            case "ASSOCIATE_TEACHER":
                Map<Integer, Float> associateTeacher = new HashMap<>();
                associateTeacher.put(1, 37008f);
                associateTeacher.put(2, 39126f);
                associateTeacher.put(3, 40060f);
                associateTeacher.put(4, 41945f);
                associateTeacher.put(5, 43130f);
                associateTeacher.put(6, 44464f);
                associateTeacher.put(7, 45716f);
                associateTeacher.put(8, 46978f);
                associateTeacher.put(9, 47948f);
                associateTeacher.put(10, 50419f);
                return associateTeacher;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an Clinician's salary given their job title
     * 
     * @return A map of salary amount to years for a given Clinician's job title
     */
    private Map<Integer, Float> getClinicalSalary() {
        switch (jobTitle) {
            case "TRSRGF":
                Map<Integer, Float> trsgrf = new HashMap<>();
                trsgrf.put(1, 62038f);
                trsgrf.put(2, 63360f);
                trsgrf.put(3, 64724f);
                trsgrf.put(4, 66076f);
                trsgrf.put(5, 67430f);
                trsgrf.put(6, 68853f);
                trsgrf.put(7, 70352f);
                trsgrf.put(8, 71848f);
                trsgrf.put(9, 73048f);
                return trsgrf;
            case "CLINICAL_TUTOR":
                Map<Integer, Float> clinicalTutor = new HashMap<>();
                clinicalTutor.put(1, 66452f);
                clinicalTutor.put(2, 69009f);
                clinicalTutor.put(3, 71509f);
                clinicalTutor.put(4, 73344f);
                clinicalTutor.put(5, 75794f);
                clinicalTutor.put(6, 78252f);
                return clinicalTutor;
            case "CLINICAL_FELLOW":
                Map<Integer, Float> clinicalFellow = new HashMap<>();
                clinicalFellow.put(1, 74499f);
                clinicalFellow.put(2, 76258f);
                clinicalFellow.put(3, 78804f);
                clinicalFellow.put(4, 81095f);
                clinicalFellow.put(5, 84824f);
                clinicalFellow.put(6, 88562f);
                clinicalFellow.put(7, 92294f);
                return clinicalFellow;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return an ULAC's salary given their job title
     * 
     * @return A map of salary amount to years for a given ULAC's job title
     */
    private Map<Integer, Float> getULACSalary() {
        switch (jobTitle) {
            case "ASSISTANT_SENIOR_INSTRUCTOR":
                Map<Integer, Float> assistantSeniorInstructor = new HashMap<>();
                assistantSeniorInstructor.put(1, 31099f);
                return assistantSeniorInstructor;
            case "LEAD_INSTRUCTOR":
                Map<Integer, Float> leadInstructor = new HashMap<>();
                leadInstructor.put(1, 29370f);
                return leadInstructor;
            case "MULTI_ACTIVITY_INSTRUCTOR_GRADE_1":
                Map<Integer, Float> multiActivityInstructorGrade1 = new HashMap<>();
                multiActivityInstructorGrade1.put(1, 25334f);
                multiActivityInstructorGrade1.put(2, 27639f);
                return multiActivityInstructorGrade1;
            case "MULTI_ACTIVITY_INSTRUCTOR_GRADE_2":
                Map<Integer, Float> multiActivityInstructorGrade2 = new HashMap<>();
                multiActivityInstructorGrade2.put(1, 25910f);
                return multiActivityInstructorGrade2;
            case "ASSISTANT_INSTRUCTOR":
                Map<Integer, Float> assistantInstructor = new HashMap<>();
                assistantInstructor.put(1, 23363f);
                assistantInstructor.put(2, 25576f);
                return assistantInstructor;
            case "CO_OP_STUDENTS":
                Map<Integer, Float> coOpStudents = new HashMap<>();
                coOpStudents.put(1, 25306f);
                coOpStudents.put(2, 25350f);
                return coOpStudents;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }

    /**
     * Method used to return a Researcher's salary given their job title
     * 
     * @return A map of salary amount to years for a given Researcher's job title
     */
    private Map<Integer, Float> getResearchSalary() {
        switch (jobTitle) {
            case "RESEARCH_ASSISTANT":
                Map<Integer, Float> researchFellow = new HashMap<>();
                researchFellow.put(1, 31962f);
                researchFellow.put(2, 32782f);
                researchFellow.put(3, 33203f);
                researchFellow.put(4, 34962f);
                researchFellow.put(5, 34945f);
                researchFellow.put(6, 35856f);
                researchFellow.put(7, 36796f);
                researchFellow.put(8, 37419f);
                researchFellow.put(9, 38379f);
                researchFellow.put(10, 39188f);
                researchFellow.put(11, 39857f);
                researchFellow.put(12, 40880f);
                researchFellow.put(13, 41943f);
                return researchFellow;
            case "POST_DOC_RESEARCHER":
                Map<Integer, Float> researchAssistant = new HashMap<>();
                // PD1
                researchAssistant.put(1, 44847f);
                researchAssistant.put(2, 45441f);
                researchAssistant.put(3, 47412f);
                researchAssistant.put(4, 48671f);
                researchAssistant.put(5, 49968f);
                researchAssistant.put(6, 51313f);
                // PD2
                researchAssistant.put(7, 52715f);
                researchAssistant.put(8, 54198f);
                researchAssistant.put(9, 55740f);
                researchAssistant.put(10, 57332f);
                return researchAssistant;
            case "RESEARCH_FELLOW":
                Map<Integer, Float> researchScientist = new HashMap<>();
                researchScientist.put(1, 63958f);
                researchScientist.put(2, 65813f);
                researchScientist.put(3, 67724f);
                researchScientist.put(4, 69692f);
                return researchScientist;
            case "SENIOR_RESEARCH_FELLOW":
                Map<Integer, Float> seniorResearchFellow = new HashMap<>();
                seniorResearchFellow.put(1, 77800f);
                seniorResearchFellow.put(2, 80070f);
                seniorResearchFellow.put(3, 81148f);
                seniorResearchFellow.put(4, 83492f);
                return seniorResearchFellow;
            default:
                System.out.println("Invalid job title");
                return null;
        }
    }
}
