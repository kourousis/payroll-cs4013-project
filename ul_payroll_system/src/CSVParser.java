import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVParser {
    public void parse(String csvFile) {
        String line;
        String csvSplitBy = ",";
        Map<Integer, String[]> csvData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy);
                csvData.put(row++, fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}