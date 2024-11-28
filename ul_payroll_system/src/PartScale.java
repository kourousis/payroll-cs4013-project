import java.util.HashMap;
import java.util.Map;

public class PartScale extends SalaryScale {
    /**
     * Method
     * @param department
     * @param jobTitle
     * @param years
     * @return
     */
    @Override
    public float getSalaryData(String department, String jobTitle, int years) {
        this.jobTitle = jobTitle;
        this.years = years;
        // Minimum wage: €12.70
        switch (jobTitle) {
            case ("LECTURE"):
                Map<Integer, Float> lecture = new HashMap<>();
                lecture.put(1, 12.70f);
                lecture.put(2, 15.40f);
                lecture.put(3, 25.10f);
                lecture.put(4, 30.80f);
                return lecture.get(years);
            case ("TUTORIAL"):
                Map<Integer, Float> tutorial = new HashMap<>();
                tutorial.put(1, 12.70f);
                tutorial.put(2, 15.40f);
                tutorial.put(3, 25.10f);
                tutorial.put(4, 30.80f);
                return tutorial.get(years);
            case ("LAB"):
                Map<Integer, Float> lab = new HashMap<>();
                lab.put(1, 12.70f);
                lab.put(2, 15.40f);
                lab.put(3, 25.10f);
                lab.put(4, 30.80f);
                return lab.get(years);
            case ("DEMO"):
                Map<Integer, Float> demo = new HashMap<>();
                demo.put(1, 12.70f);
                demo.put(2, 15.40f);
                demo.put(3, 25.10f);
                demo.put(4, 30.80f);
                return demo.get(years);
            case ("EXAM-INVIGILATOR"):
                Map<Integer, Float> invigilator = new HashMap<>();
                invigilator.put(1, 12.70f);
                invigilator.put(2, 15.40f);
                invigilator.put(3, 25.10f);
                invigilator.put(4, 30.80f);
                return invigilator.get(years);
            case ("EXAM-SUPERVISOR"):
                Map<Integer, Float> supervisor = new HashMap<>();
                supervisor.put(1, 12.70f);
                supervisor.put(2, 15.40f);
                supervisor.put(3, 25.10f);
                supervisor.put(4, 30.80f);
                return supervisor.get(years);
            default:
                break;
        }
        return 0;
    }
}
