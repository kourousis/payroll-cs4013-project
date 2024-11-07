public class App {
    public static void main(String[] args) {
        Employee employee = new Employee(
                "Jane Smith",
                "217-555-5678",
                "jane.smith@ul.ie",
                new Address("5678 Oak St", "Springfield", "IL", "62702", "USA"),
                75000.0f,
                "Computer Science"
        );

        System.out.println(employee);
    }
}
