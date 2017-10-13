package pl.com.bottega.photostock.sales.misc;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EmployeesJDBCWrite {

    private static final LocalDate COSMIC_DATE = LocalDate.of(9999, 01, 01);

    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GENERATE_EMP_NO = "SELECT max(emp_no) + 1 FROM employees";
    private static final String INSERT_TITLE_SQL = "INSERT INTO titles VALUES (?, ?, ?, ?)";
    private static final String INSERT_DEPARTMENT_SQL = "INSERT INTO dept_emp VALUES (?, ?, ?, ?)";
    private static final String INSERT_SALARY_SQL = "INSERT INTO salaries VALUES (?, ?, ?, ?)";
    private static final String GET_DEPARTMENT_NO = "SELECT dep_no FROM departments where dep_name = ?";
    private static final String GET_MAX_DEPARTMENT_NO = "SELECT max(dep_no) FROM departments";
    private static final String INSERT_NEW_DEPARTMENT = "INSERT INTO departments VALUES (?, ?)";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String firstName = getFirstName(scanner);
        String lastName = getLastName(scanner);
        LocalDate hireDate = LocalDate.now();
        String gender = getGender(scanner);
        LocalDate birthDate = getBirthDate(scanner);
        Integer salary = getSalary(scanner);
        String departmentName = getDepartment(scanner);
        String title = getTitle(scanner);

        Connection connection = getConnection();
        connection.setAutoCommit(false);

        Long empNo = insertEmployee(connection, firstName, lastName, hireDate, gender, birthDate);
        insertSalary(connection, empNo, salary);
        insertDepartment(connection, empNo, departmentName);
        insertTitle(connection, empNo, title);

        connection.commit();
    }

    private static void insertTitle(Connection connection, Long empNo, String title) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(INSERT_TITLE_SQL);
        stmt.setLong(1, empNo);
        stmt.setString(2, title);
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        stmt.setDate(4, Date.valueOf(COSMIC_DATE));
        stmt.executeUpdate();
    }

    private static void insertDepartment(Connection connection, Long empNo, String departmentName) throws SQLException {
        String depNo = getOrCreateDep(connection, departmentName);
        PreparedStatement stmt = connection.prepareStatement(INSERT_DEPARTMENT_SQL);
        stmt.setLong(1, empNo);
        stmt.setString(2, depNo);
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        stmt.setDate(4, Date.valueOf(COSMIC_DATE));
        stmt.executeUpdate();
    }

    private static String getOrCreateDep(Connection connection, String departmentName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(GET_DEPARTMENT_NO);
        stmt.setString(1, departmentName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        String newNumber = generateNewDepNo(connection);
        stmt =connection.prepareStatement(INSERT_NEW_DEPARTMENT);
        stmt.setString(1, newNumber);
        stmt.setString(2, departmentName);
        stmt.executeUpdate();
        return newNumber;

    }

    private static String generateNewDepNo(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(GET_MAX_DEPARTMENT_NO);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            Integer maxDepNo = Integer.valueOf(rs.getString(1).substring(1));
            Integer newMaxDepNo = maxDepNo + 1;
            return String.format("d%03d", newMaxDepNo);
        }
        return "d001";
    }

    private static void insertSalary(Connection connection, Long empNo, Integer salary) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(INSERT_SALARY_SQL);
        stmt.setLong(1, empNo);
        stmt.setInt(2, salary);
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        stmt.setDate(4, Date.valueOf(COSMIC_DATE));
        stmt.executeUpdate();
    }

    private static Long insertEmployee(Connection connection, String firstName, String lastName, LocalDate hireDate, String gender, LocalDate birthDate) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery(GENERATE_EMP_NO);
        rs.next();
        Long id = rs.getLong(1);
        PreparedStatement stmt = connection.prepareStatement(INSERT_EMPLOYEE_SQL, Statement.RETURN_GENERATED_KEYS);
        stmt.setLong(1, id);
        stmt.setDate(2, Date.valueOf(birthDate));
        stmt.setString(3, firstName);
        stmt.setString(4, lastName);
        stmt.setString(5, gender);
        stmt.setDate(6, Date.valueOf(hireDate));
        stmt.executeUpdate();
        return id;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/employees?" +
                "user=root&password=Agent007!");
    }

    private static String getTitle(Scanner scanner) {
        System.out.println("Podaj stanowisko: ");
        return scanner.nextLine();
    }

    private static String getDepartment(Scanner scanner) {
        System.out.println("Podaj nazwę działu: ");
        return scanner.nextLine();
    }

    private static Integer getSalary(Scanner scanner) {
        System.out.println("Podaj zarobki: ");
        return scanner.nextInt();
    }

    private static LocalDate getBirthDate(Scanner scanner) {
        System.out.print("Podaj datę urodzenia: ");
        return LocalDate.parse(scanner.nextLine());
    }

    private static String getGender(Scanner scanner) {
        System.out.println("Podaj płeć");
        return scanner.nextLine();
    }

    private static String getLastName(Scanner scanner) {
        System.out.println("Podaj nazwisko: ");
        return scanner.nextLine();
    }

    private static String getFirstName(Scanner scanner) {
        System.out.println("Podaj imie: ");
        return scanner.nextLine();
    }

}
