import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {

    public static void main(String[] args) {
        // Oracle connection details
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        
        Connection con = null;
        Statement stmt = null;

        try {
            // Step 1: Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Step 2: Establish the connection
            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to Oracle database.");
            } else {
                System.out.println("Failed to connect to Oracle.");
                return;
            }

            // Step 3: Create the statement
            stmt = con.createStatement();

            // Step 4: Create Department table
            String createDeptTable = "CREATE TABLE Department1 (" +
                    "Did INT PRIMARY KEY, " +
                    "Dname VARCHAR(100))";
            stmt.executeUpdate(createDeptTable);
            System.out.println("Department table created.");

            // Step 5: Create Employee table with Foreign Key to Department
            String createEmpTable = "CREATE TABLE Employee1 (" +
                    "Eid INT PRIMARY KEY, " +
                    "Ename VARCHAR(100), " +
                    "Salary NUMBER, " +  // NUMBER used for Oracle instead of DOUBLE
                    "Address VARCHAR(255), " +
                    "Did INT, " +
                    "FOREIGN KEY (Did) REFERENCES Department(Did))";
            stmt.executeUpdate(createEmpTable);
            System.out.println("Employee table created.");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred.");
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
