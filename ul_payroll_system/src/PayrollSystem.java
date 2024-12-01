import java.io.IOException;

public class PayrollSystem {
	/**
	 * Main method to run PayrollSystemMenu
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		PayrollSystemMenu payrollSystemMenu = new PayrollSystemMenu();
		payrollSystemMenu.run();
	}
}