import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DBController {
    private static final String CSV_FILE_PATH = new File("").getAbsolutePath() + "/ul_payroll_system/db/";
    private Map<String, Integer> tableFields = new HashMap<>();

    public DBController() {
        tableFields.put("employees", 13);
        tableFields.put("payslips", 8);
        tableFields.put("control_data", 3);
    }

    public String GET(String table, int id, String data) {
        if (!table.equals("employees") && !table.equals("control_data")) {
            System.out.println("No table found");
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

    public String UPDATE(String table, int id, String field, String newValue) {
        if (!table.equals("employees") && !table.equals("control_data")) {
            System.out.println("No table found");
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

    public HashMap<String, String> GET_ROW(String table, int id, String date) {
        String path;
        if (table.equals("employees")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else if (table.equals("payslips")) {
            path = CSV_FILE_PATH + "/payslips/" + "payslip_" + id + ".csv";
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
                    continue; // Skip lines with empty ID field
                }
                int currentId = Integer.parseInt(values[0]); // EmployeeID is always first column

                if (table.equals("employees")) {
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
            System.out.println("Record not found");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

            if (!data.isEmpty()) return data;
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public boolean ADD(String table, String[] data) {
        if (data.length != tableFields.get(table)) {
            System.out.println("Incorrect number of fields");
            return false;
        }

        String path = "";
        if (table.equals("employees")) {
            path = CSV_FILE_PATH + table + ".csv";
        } else if (table.equals("payslip")) {
            path = CSV_FILE_PATH + "/payslips/" + "payslip_" + data[0] + ".csv";
        } else {
            System.out.println("No table found");
            return false;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.newLine();
            if (table.equals("payslip")) writer.newLine();
            writer.write(String.join(",", data));
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean NEW_PAYSLIP(Payslip ps) {
        String path = CSV_FILE_PATH + "/payslips/" +  "payslip_" + ps.getId() + ".csv";
        String header = "ID,Date,EmployeeName,GrossPay,USC,PRSI,IncomeTax,NetPay";
        String[] data = {Integer.toString(ps.getId()), ps.getDate().toString(), ps.getEmployeeName(), Float.toString(ps.getGrossPay()),
                Float.toString(ps.getUSC()), Float.toString(ps.getPRSI()), Float.toString(ps.getIncomeTax()), Float.toString(ps.getNetPay())};

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(header);
            writer.newLine();
            writer.write(String.join(",", data));
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}