import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DBController {
    /**
     * filepath to the csv database files
     */
    private static final String CSV_FILE_PATH = new File("").getAbsolutePath() + "/ul_payroll_system/db/";
    /**
     * A map that stores the number of fields (columns) for each table in the
     * database.
     */
    private Map<String, Integer> tableFields = new HashMap<>();

    /**
     * Default constructor for DBController
     * Initilises the table fields
     */
    public DBController() {
        tableFields.put("employees", 15);
        tableFields.put("payslip", 10);
        tableFields.put("control_data", 3);
        tableFields.put("pendingPromo", 3);
    }

    /**
     * Retrive a single entry from a CSV database row.
     * 
     * @param table specified csv table in the csv database
     * @param id    row id
     * @param data  entry in the database, for example "Name" in employees.csv
     * @return For example, GET("employees", 1, "Name") returns the name of the
     *         employee "John Doe"
     */
    public String GET(String table, int id, String data) {
        if (!table.equals("employees") && !table.equals("control_data") && !table.equals("pendingPromo")) {
            // System.out.println("No table found");
            return null;
        }

        String path = CSV_FILE_PATH + table + ".csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // Get header line
            String header = reader.readLine();
            // Split into data fields and put into arraylist
            ArrayList<String> fields = new ArrayList<>(Arrays.asList(header.split(",")));

            // Find the index of the requested data column
            int index = fields.indexOf(data);
            if (index == -1) {
                return "";
            }

            // Read through the file to find the matching ID
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int currentId = Integer.parseInt(values[0]); // EmployeeID is always first column

                if (currentId == id) {
                    return values[index];
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update a fied value in the csv database with a new value
     * 
     * @param table    specified table in the csv database
     * @param id       the id of the row
     * @param field    entry in the database, for example "Name" in employees.csv
     * @param newValue the value to replace field with
     * @return For example, UPDATE("employees", 1, "Name", "Johnathan Doe") updates
     *         the name of the
     *         employee "John Doe" to "Johnathan Doe"
     */
    public String UPDATE(String table, int id, String field, String newValue) {
        if (!table.equals("employees") && !table.equals("control_data") && !table.equals("pendingPromo")) {
            // System.out.println("No table found");
            return null;
        }

        String path = CSV_FILE_PATH + table + ".csv";
        ArrayList<String> lines = new ArrayList<>();

        try {
            // Read header
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String header = reader.readLine();
            lines.add(header);

            // Get fields and find index of the field to update
            ArrayList<String> fields = new ArrayList<>(Arrays.asList(header.split(",")));
            int fieldIndex = fields.indexOf(field);
            if (fieldIndex == -1) {
                System.out.println("Field not found");
                reader.close();
                return null;
            }

            // Read and store data, and update so it can be rewritten
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int currentId = Integer.parseInt(values[0]);

                if (currentId == id) {
                    found = true;
                    values[fieldIndex] = newValue;
                    line = String.join(",", values);
                }
                lines.add(line);
            }
            reader.close();

            if (!found) {
                System.out.println("Employee ID not found");
                return null;
            }

            // Write all lines back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();

            return "Sucess";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a whole row from a specified table in hashmap form
     * 
     * @param table specified table in the csv database
     * @param id    the id of the row
     * @param date  date associated with record only used for payslip retrieval
     * @return A HashMap<String, String> where the keys are column names and
     *         the values are the corresponding data.
     *         Returns null if the record is not found.
     */
    public HashMap<String, String> GET_ROW(String table, int id, String date) {
        String path;
        if (table.equals("employees")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else if (table.equals("payslips")) {
            path = CSV_FILE_PATH + "/payslips/" + "payslip_" + id + ".csv";
        } else if (table.equals("pendingPromo")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else {
            System.out.println("No table found");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // Get header + col names
            String header = reader.readLine();
            String[] columns = header.split(",");

            // Read through the file to find the matching ID
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].isEmpty()) {
                    // Skip lines with empty ID field
                    continue;
                }
                // EmployeeID is always first column
                int currentId = Integer.parseInt(values[0]);

                if (table.equals("employees") || table.equals("pendingPromo")) {
                    if (currentId == id) {
                        HashMap<String, String> row = new HashMap<>();
                        for (int i = 0; i < columns.length; i++) {
                            row.put(columns[i], values[i]);
                        }
                        return row;
                    }
                } else if (table.equals("payslips")) {
                    String currentDate = values[1];
                    if (currentId == id && currentDate.equals(date)) {
                        HashMap<String, String> row = new HashMap<>();
                        for (int i = 0; i < columns.length; i++) {
                            row.put(columns[i], values[i]);
                        }
                        return row;
                    }
                }
            }
            // System.out.println("Record not found");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrives a specific column from a csv file in the database
     * 
     * @param table  specified table in the csv database
     * @param column the column of the csv that is requested
     * @return ArrayList of all data under the specified column
     */
    public ArrayList<String> GET_COL(String table, String column) {
        if (!tableFields.containsKey(table)) {
            System.out.println("No table found");
            return null;
        }

        String path = CSV_FILE_PATH + table + ".csv";
        ArrayList<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            ArrayList<String> fields = new ArrayList<>(Arrays.asList(reader.readLine().split(",")));

            int index = fields.indexOf(column);
            if (index == -1) {
                System.out.println("Data column not found");
                return null;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(",")[index]);
            }
            reader.close();

            if (!data.isEmpty())
                return data;
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns entire csv table in the form of an Arraylist of Hashmaps. Each
     * hashmap represents a row in the csv
     * 
     * @param table specified table in the csv database
     * @return ArrayList<HashMap<String, String>> of the entire csv file, where each
     *         HasMap<String, String> represents a row in key:value pair
     */
    public ArrayList<HashMap<String, String>> GET_CSV(String table) {
        if (!table.equals("employees") && !table.equals("payclaim")) {
            System.out.println("No table found");
            return null;
        }

        String path = CSV_FILE_PATH + table + ".csv";
        ArrayList<HashMap<String, String>> csv = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // Get header + col names
            String header = reader.readLine();
            String[] columns = header.split(",");

            // Read rest
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                HashMap<String, String> row = new HashMap<>();

                for (int i = 0; i < columns.length; i++) {
                    row.put(columns[i], values[i]);
                }
                csv.add(row);
            }
            return csv;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used to add entries to a csv file
     * 
     * @param table specified table in the csv database
     * @param data  data for all columns to add to a row
     * @return If that data has been added successfully to the csv
     */
    public boolean ADD(String table, String[] data) {
        String path = "";

        if (table.startsWith("payclaim")) {
            path = CSV_FILE_PATH + "/payclaims/payclaims_" + data[0] + ".csv";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                writer.newLine();
                writer.write(String.join(",", data));
                writer.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (table.equals("pendingPromo")) {
            path = CSV_FILE_PATH + table + ".csv";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                writer.newLine();
                writer.write(String.join(",", data));
                writer.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (table.equals("payslip")) {
            path = CSV_FILE_PATH + "/payslips/payslip_" + data[0] + ".csv";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                writer.newLine();
                writer.write(String.join(",", data));
                writer.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (data.length + 1 != tableFields.get(table)) {
            return false;
        }

        if (table.equals("employees")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else {
            return false;
        }

        try {
            int id = Integer.parseInt(LATEST_ROW(table).get("EmployeeID")) + 1;
            String[] newData = new String[tableFields.get(table)];
            newData[0] = String.valueOf(id);
            System.arraycopy(data, 0, newData, 1, data.length);

            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.newLine();
            writer.write(String.join(",", newData));
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Database method to create a new payslip and add it to the csv database
     * 
     * @param id payslip's id
     * @return If the payslip has been added successfully to the csv
     */
    public boolean NEW_PAYSLIP(int id) {
        String path = CSV_FILE_PATH + "/payslips/" + "payslip_" + id + ".csv";
        String header = "ID,Date,EmployeeName,GrossPay,USC,PRSI,IncomeTax,Health,Union,NetPay";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(header);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Database method to create a new payclaim and add it to the csv database
     * 
     * @param id payclaim's id
     * @return If the payclaim has been added successfully to the csv
     */
    public boolean NEW_PAYCLAIM(int id) {
        String path = CSV_FILE_PATH + "/payclaims/" + "payclaim_" + id + ".csv";
        String header = "EmployeeId,Hours,Role,Date";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(header);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Database method to retrive the last row of a csv file
     * 
     * @param table specified table in the csv database
     * @return A hashmap of the latest row added to the database in the form of a
     *         key:value pair
     */
    public HashMap<String, String> LATEST_ROW(String table) {
        String path;
        if (table.equals("employees")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else if (table.startsWith("payslip_")) {
            path = CSV_FILE_PATH + "/payslips/" + table + ".csv";
        } else if (table.startsWith("payclaims_")) {
            path = CSV_FILE_PATH + "/payclaims/" + table + ".csv";
        } else {
            // System.out.println("Invalid table name");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            String[] columns = header.split(",");

            HashMap<String, String> defaultRow = new HashMap<>();
            for (String column : columns) {
                defaultRow.put(column, "0");
            }

            String line;
            HashMap<String, String> latestRow = null;
            String latestDate = "";
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                hasData = true;
                String[] values = line.split(",");

                if (table.startsWith("payslip_")) {
                    String currentDate = values[1];
                    if (latestDate.isEmpty() || currentDate.compareTo(latestDate) > 0) {
                        latestDate = currentDate;
                        latestRow = new HashMap<>();
                        for (int i = 0; i < columns.length; i++) {
                            latestRow.put(columns[i], values[i]);
                        }
                    }
                } else {
                    latestRow = new HashMap<>();
                    for (int i = 0; i < columns.length; i++) {
                        latestRow.put(columns[i], values[i]);
                    }
                }
            }

            // Terinary operator to return latest if exists else return default
            return hasData ? latestRow : defaultRow;

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Database method to get the number of rows in a given csv file
     * 
     * @param tableName specified table in the csv database
     * @return Number of rows in the given csv file
     */
    public int GET_ROW_COUNT(String tableName) {
        String filePath = CSV_FILE_PATH + tableName + ".csv";
        int rowCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();

            while (br.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return rowCount;
    }

    /**
     * Database method to delete a specified row in a csv file
     * 
     * @param table specified table in the csv database
     * @param id    row id
     * @return If the row has been deleted successfully
     */
    public boolean DELETE_ROW(String table, int id) {
        String path;
        if (!table.equals("pendingPromo")) {
            System.out.println("No table found");
            return false;
        }
        path = CSV_FILE_PATH + table + ".csv";
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // Get header + col names
            String header = reader.readLine();
            // Keep header
            lines.add(header);

            // Read through the file to find the matching ID
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].isEmpty()) {
                    // Skip lines with empty ID field
                    continue;
                }
                int currentId = Integer.parseInt(values[0]); // EmployeeID is always first column

                if (currentId != id) {
                    // Keep non-matching rows
                    lines.add(line);
                } else {
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Record not found");
                return false;
            }

            // Write back all lines except deleted row
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}