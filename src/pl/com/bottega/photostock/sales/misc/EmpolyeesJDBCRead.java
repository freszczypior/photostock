package pl.com.bottega.photostock.sales.misc;


import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class EmpolyeesJDBCRead {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imie: ");
        String firstName = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String lastName = scanner.nextLine();

        //String sgl = "SELECT * FROM employees where first_name = ? AND last_name = ?";
        String sgl = "SELECT e.first_name, e.last_name, e.emp_no, s.salary FROM salaries s " +
                "LEFT JOIN employees e ON s.emp_no = e.emp_no " +
                "WHERE e.first_name = ? AND e.last_name = ? " +
                "ORDER BY s.from_date";

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/employees?" +
                "user=root&password=Agent007!");
        PreparedStatement stmt = con.prepareStatement(sgl);
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            System.out.printf("%s %s %s %s %s %s",
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("emp_no"),
                    rs.getString("salary"),
                    rs.getString("from_date"),
                    rs.getString("to_date"));
            System.out.println();
        }
    }
}
