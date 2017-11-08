package pl.com.bottega.photostock.sales.misc;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by freszczypior on 2017-10-12.
 */
public class ReleaseTheEmpJDBC {

    private static Scanner scanner = new Scanner(System.in);

    private static final String GET_ALL_EMP_SQL =
            "SELECT * FROM employees WHERE first_name = ? AND last_name = ?";
    private static final String UPDATE_SALARIES_SQL =
            "UPDATE salaries SET to_date = current_date WHERE emp_no = ? and to_date > current_date";
    private static final String UPDATE_TITLE_SQL =
            "UPDATE titles SET to_date = current_date WHERE emp_no = ? and to_date > current_date";
    private static final String UPDATE_DEPT_EMP_SQL =
            "UPDATE dept_emp SET to_date = current_date WHERE emp_no = ? and to_date > current_date";
    private static final String UPDATE_DEPT_MANAGER_SQL =
            "UPDATE dept_manager SET to_date = current_date WHERE emp_no = ? and to_date > current_date";


    public static void main(String[] args) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/employees?" +
                "user=root&password=*****");

        String firstName = getFirstName();
        String lastName = getLastName();

        ResultSet rs = getMatchingEmp(firstName, lastName, con);

        if (rs.next()) {
            con.setAutoCommit(false);
            Integer empNo = getEmpNoToRelease(rs);
            updateSalaries(con, empNo);
            updateTitles(con, empNo);
            updateDeptEmp(con, empNo);
            updateDeptManager(con, empNo);
            con.commit();
            System.out.printf("Employee %s %s has been released.", firstName, lastName);
        } else
            System.out.println("There is no such employee in the date base.");
    }


    private static ResultSet getMatchingEmp(String firstName, String lastName, Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(GET_ALL_EMP_SQL);
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        return stmt.executeQuery();
    }

    private static Integer getEmpNoToRelease(ResultSet resultSet) throws SQLException {
        ResultSet rs = resultSet;
        rs.last();
        Integer rowNo = rs.getRow();
        if (rowNo > 1) {
            rs.beforeFirst();
            while (rs.next()) {
                System.out.printf("%d. %s %s %s %s %s %s",
                        rs.getRow(),
                        rs.getString("emp_no"),
                        rs.getString("birth_date"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("hire_date"));
                System.out.println();
            }
            System.out.print("Choose employee number to release: ");
            rs.absolute(scanner.nextInt());
            scanner.nextLine();
        }
            return rs.getInt("emp_no");
    }
    private static void updateSalaries(Connection con, int empNo) throws SQLException {
        PreparedStatement stmt;
        stmt = con.prepareStatement(UPDATE_SALARIES_SQL);
        stmt.setInt(1, empNo);
        stmt.executeUpdate();
    }
    private static void updateTitles(Connection con, int empNo) throws SQLException {
        PreparedStatement stmt;
        stmt = con.prepareStatement(UPDATE_TITLE_SQL);
        stmt.setInt(1, empNo);
        stmt.executeUpdate();
    }
    private static void updateDeptEmp(Connection con, int empNo) throws SQLException {
        PreparedStatement stmt;
        stmt = con.prepareStatement(UPDATE_DEPT_EMP_SQL);
        stmt.setInt(1, empNo);
        stmt.executeUpdate();
    }
    private static void updateDeptManager(Connection con, int empNo) throws SQLException {
        PreparedStatement stmt;
        stmt = con.prepareStatement(UPDATE_DEPT_MANAGER_SQL);
        stmt.setInt(1, empNo);
        stmt.executeUpdate();
    }
    private static String getLastName() {
        System.out.print("Input the employee last name: ");
        return scanner.nextLine();
    }
    private static String getFirstName() {
        System.out.print("Input the employee first name: ");
        return scanner.nextLine();
    }
}
