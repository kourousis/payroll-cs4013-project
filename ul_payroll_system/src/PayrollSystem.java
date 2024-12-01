import java.io.IOException;

/**
 * PayrollSystem used to run the PayrollSystemMenu
 */
public class PayrollSystem {
	/**
	 * Default constructor for PayrollSystem object.
	 * Not needed anywhere
	 */
	public PayrollSystem() {
	}

	/**
	 * Main method to run PayrollSystemMenu
	 * 
	 * @param args args
	 * @throws IOException exception
	 */
	public static void main(String[] args) throws IOException {
		PayrollSystemMenu payrollSystemMenu = new PayrollSystemMenu();
		payrollSystemMenu.run();
	}
}