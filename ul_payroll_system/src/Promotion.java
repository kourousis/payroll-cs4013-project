import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Promotion {


    // Promotion method for an employee and adds to pendingPromo.csv
    public void promoteEmployee(String userId, String newJobTitle) {
        // Gives path for csv file
        String filePath = "db/pendingPromo.csv";

        // Creates file if it doesnt exist
        File file = new File(filePath);

        // Check if the file exists, if not, create it and write headers
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error while creating pendingPromo.csv file");
            }
        }
    }
}

