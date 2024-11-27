import java.util.Scanner;
import java.util.HashMap;

public class Acceptance {

    public void PromotionPending(String employeeIdStr) {
        DBController db = new DBController();

        int employeeId = Integer.parseInt(employeeIdStr);

        // Check if there is a pending promotion request in pendingPromo.csv
        HashMap<String, String> pendingPromotionData = db.GET_ROW("pendingPromo", employeeId, "EmployeeID");

        // Ensure that the data is not null or empty
        if (pendingPromotionData == null || pendingPromotionData.isEmpty()) {
            System.out.println("No pending promotion for employee ID " + employeeIdStr);
            return; // No pending promotion for this employee
        }

        // Ensure that the "Jobtitle" key exists in the map
        if (!pendingPromotionData.containsKey("Jobtitle")) {
            System.out.println("Error: 'Jobtitle' field not found in pending promotion data.");
            return; // Exit if the key doesn't exist
        }

        // The new job title will be assumed to be in the "Jobtitle" field of the pendingPromotion data
        String newJobTitle = pendingPromotionData.get("Jobtitle"); // Assuming the job title is in the "Jobtitle" field

        // Create a Scanner object to take input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you accept the promotion? (y/n)");

        // Process the user's input
        String userInput = scanner.nextLine().toLowerCase();

        if (userInput.equals("y") || userInput.equals("Y")) {
            // Accept the promotion
            System.out.println("You have accepted the promotion!");

            // Update the employee's job title in the employee.csv
            boolean updateSuccess = db.UPDATE("employees", employeeId, "Jobtitle", newJobTitle).equals("Success");
            if (updateSuccess) {
                System.out.println("Job title updated to " + newJobTitle + " in employee.csv.");
            } else {
                System.out.println("Failed to update job title in employee.csv.");
            }

            // Remove the pending promotion from the pendingPromo.csv
            db.UPDATE("pendingPromo", employeeId, "EmployeeId", "");
            System.out.println("Promotion data removed from pendingPromo.csv.");

        } else if (userInput.equals("n") || userInput.equals("N")) {
            // Reject the promotion
            System.out.println("You have rejected the promotion.");

            // Remove the pending promotion from the pendingPromo.csv
            db.UPDATE("pendingPromo", employeeId, "EmployeeId", "");
            System.out.println("Promotion data removed from pendingPromo.csv.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }
}
