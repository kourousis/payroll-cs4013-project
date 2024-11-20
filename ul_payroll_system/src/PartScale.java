import java.util.Map;

public class PartScale extends SalaryScale{
    private int hours;
    public Map<Integer, Float> getSalaryData(String jobTitle, int hours) {
        this.jobTitle = jobTitle;
        this.hours = hours;
        // Minimum wage: €12.70
        switch (jobTitle) {
            case ("Lecture"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            case ("Tutorial"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            case ("Lab"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            case ("Demo"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            case ("Exam-Invigilator"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            case ("Exam-Supervisor"):
                return Map.of(
                        1, 100.0f,
                        2, 200.0f,
                        3, 300.0f,
                        4, 400.0f,
                        5, 500.0f
                );
            default:
                break;
        }
        return null;
    }
}
