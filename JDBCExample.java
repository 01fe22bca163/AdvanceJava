import java.sql.*;
import java.util.Scanner;

public class JDBCExample {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";

        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Register the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Step 2: Establish the connection
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            if (con != null) {
                System.out.println("Connected to Oracle database.");
            }

            // Step 3: Create tables using Statement
            Statement stmt = con.createStatement();

            // Step 4: Insert rows into Department using Scanner

            String insertDept = "INSERT INTO Department1 (Did, Dname) VALUES (?, ?)";
            PreparedStatement pstmtDept = con.prepareStatement(insertDept);

            System.out.println("Enter number of departments to insert:");
            int deptCount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < deptCount; i++) {
                System.out.println("Enter Department ID:");
                int deptId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.println("Enter Department Name:");
                String deptName = scanner.nextLine();

                pstmtDept.setInt(1, deptId);
                pstmtDept.setString(2, deptName);
                pstmtDept.executeUpdate();
            }
            System.out.println(deptCount + " rows inserted into Department table.");

            // Step 5: Insert rows into Employee using Scanner

            String insertEmp = "INSERT INTO Employee1 (Eid, Ename, Salary, Address, Did) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtEmp = con.prepareStatement(insertEmp);

            System.out.println("Enter number of employees to insert:");
            int empCount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < empCount; i++) {
                System.out.println("Enter Employee ID:");
                int empId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.println("Enter Employee Name:");
                String empName = scanner.nextLine();

                System.out.println("Enter Employee Salary:");
                double salary = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                System.out.println("Enter Employee Address:");
                String address = scanner.nextLine();

                System.out.println("Enter Department ID for the Employee:");
                int deptId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Validate if the Department ID exists
                String checkDeptQuery = "SELECT COUNT(*) FROM Department1 WHERE Did = ?";
                PreparedStatement pstmtCheckDept = con.prepareStatement(checkDeptQuery);
                pstmtCheckDept.setInt(1, deptId);
                ResultSet rsCheckDept = pstmtCheckDept.executeQuery();
                rsCheckDept.next();
                if (rsCheckDept.getInt(1) == 0) {
                    System.out.println("Error: Department ID " + deptId + " does not exist. Cannot insert employee.");
                    continue; // Skip this employee
                }

                pstmtEmp.setInt(1, empId);
                pstmtEmp.setString(2, empName);
                pstmtEmp.setDouble(3, salary);
                pstmtEmp.setString(4, address);
                pstmtEmp.setInt(5, deptId);
                pstmtEmp.executeUpdate();
            }
            System.out.println(empCount + " rows inserted into Employee table.");

            // Step 6: Select and display data using Statement for Department
            System.out.println("Selecting data from Department table:");
            ResultSet rsDept = stmt.executeQuery("SELECT * FROM Department1");
            while (rsDept.next()) {
                System.out.println(rsDept.getInt("Did") + " " + rsDept.getString("Dname"));
            }

            // Step 7: Select and display data using PreparedStatement for Employee
            System.out.println("\nSelecting data from Employee table:");
            String selectEmp = "SELECT * FROM Employee1";
            PreparedStatement pstmtSelect = con.prepareStatement(selectEmp);
            ResultSet rsEmp = pstmtSelect.executeQuery();

            while (rsEmp.next()) {
                System.out.println(rsEmp.getInt("Eid") + " " + rsEmp.getString("Ename") + " " +
                        rsEmp.getDouble("Salary") + " " + rsEmp.getString("Address") + " " +
                        rsEmp.getInt("Did"));
            }

            // Step 8: Close connections
            pstmtDept.close();
            pstmtEmp.close();
            rsDept.close();
            rsEmp.close();
            stmt.close();
            con.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
