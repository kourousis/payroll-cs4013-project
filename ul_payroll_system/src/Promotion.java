import java.io.*;

public class Promotion {

    protected static boolean PendingState = false;
    protected boolean AcceptanceState = false;

    // Method to promote an employee and add to pendingPromo.csv
    public void promoteEmployee(String EmployeeId, String newJobTitle) {
        // Path for the employee CSV and pendingPromo CSV files
        String employeeFilePath = "db/employees.csv";
        String promoFilePath = "db/pendingPromo.csv";



        // Check if EmployeeId exists in the employee file
        boolean foundEmployee = false;

        // Read employee file to find the employee ID
        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFilePath))) {
            String line;

            // Skip the header
            reader.readLine();

            // Read through the employee CSV and match the EmployeeId
            while ((line = reader.readLine()) != null) {
                String[] employeeDetails = line.split(",");

                // Check if EmployeeID matches
                if (employeeDetails[0].trim().equals(EmployeeId)) {
                    foundEmployee = true;
                    break;
                }
            }

            if (foundEmployee == true) {
                // Add promotion details to the pendingPromo CSV
                File promoFile = new File(promoFilePath);
                try {
                    // If the file doesn't exist, create it and add headers
                    if (!promoFile.exists()) {
                        promoFile.createNewFile();
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(promoFile))) {
                            writer.write("EmployeeId, newJobTitle\n"); // Adding headers
                        }
                    }

                    // Append the promotion details for the employee
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(promoFile, true))) {
                        writer.write(EmployeeId + ", " + newJobTitle + "\n");
                    }

                    System.out.println("Promotion is Pending " + EmployeeId + " for new title " + newJobTitle);
                    PendingState = true;
                } catch (IOException e) {
                    System.out.println("Error while writing to pendingPromo.csv");
                }
            } else {
                System.out.println("Employee with ID " + EmployeeId + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error while reading the employee file.");
        }
    }
}

