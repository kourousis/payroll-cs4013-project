import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Promotion {


    // Promotion method for an employee and adds to pendingPromo.csv
    public void promoteEmployee(String hrUserId, String EmployeeId)
    {
        // Gives path for csv file
        String filePath = "db/pendingPromo.csv";

        // Creates file if it doesnt exist
        File file = new File(filePath);
        try {

            // Check if the file exists, if not, create it and write headers
            if (!file.exists()) {
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("EmployeeId, newJobTitle\n"); // Adding headers to Promo csv file
                }
            }

            // Append the new promotion details
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(EmployeeId + ", " + newJobTitle + "\n"); // Adding the users info for csv file
            }

            System.out.println("Promotion added to pendingPromo.csv for user " + EmployeeId + " with new title " + newJobTitle);

        } catch (IOException e) {
            System.out.println("Error while writing to pendingPromo.csv");
        }
    }
}

