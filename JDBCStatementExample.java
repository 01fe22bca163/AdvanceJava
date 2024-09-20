import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCStatementExample {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        
        try {
            // Step 1: Load the Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Step 2: Establish the connection
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = con.createStatement();


            // Step 5: Insert data into Department table
            String insertDept = "INSERT INTO Department1 (Did, Dname) VALUES (1, 'HR')";
            stmt.executeUpdate(insertDept);
            System.out.println("Data inserted into Department table using Statement.");

            // Step 6: Insert data into Employee table
            String insertEmp = "INSERT INTO Employee1 (Eid, Ename, Salary, Address, Did) " +
                               "VALUES (101, 'John Doe', 50000, '123 Main St', 1)";
            stmt.executeUpdate(insertEmp);
            System.out.println("Data inserted into Employee table using Statement.");

            // Step 7: Select and display data from Employee table
            String selectEmp = "SELECT * FROM Employee1";
            ResultSet rs = stmt.executeQuery(selectEmp);
            while (rs.next()) {
                System.out.println(rs.getInt("Eid") + " " + rs.getString("Ename") + " " +
                                   rs.getDouble("Salary") + " " + rs.getString("Address") + " " +
                                   rs.getInt("Did"));
            }

            // Close the resources
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
