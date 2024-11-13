import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVWriter {
    private Map<String, Map<Integer, Float>> salaryData = new HashMap<>();

    public void parse(Map<String, Map<Integer, Float>> input) {
        salaryData = input;
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader("salary.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String name = data[0];
                int year = Integer.parseInt(data[1]);
                float salary = Float.parseFloat(data[2]);
                if (salaryData.containsKey(name)) {
                    salaryData.get(name).put(year, salary);
                } else {
                    Map<Integer, Float> newMap = new HashMap<>();
                    newMap.put(year, salary);
                    salaryData.put(name, newMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}