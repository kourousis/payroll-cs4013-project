import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    private String CSV_FILE_PATH = "./db/";

    public boolean updateEmployeeSalary(int employeeId, float newSalary, String filename) {
        this.CSV_FILE_PATH = CSV_FILE_PATH + filename;
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String header = reader.readLine(); // Header line
            lines.add(header);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Split by comma
                // Finds the employee by id
                if (Integer.parseInt(data[0]) == employeeId) {
                    data[10] = String.valueOf(newSalary);
                    line = String.join(",", data); // csv format
                    found = true;
                }
                lines.add(line);
            }

            // Write lines to file
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
                    for (String updatedLine : lines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}