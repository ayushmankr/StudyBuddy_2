import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:sqlite:lms.db";

    // Return a connection or throw SQLException. On systems without driver jar,
    // callers should handle exceptions — GUI will still run.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Initialize tables; failures are printed but do not prevent GUI from showing.
    public static void initDatabase() {
        String createStudents = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";
        String createCourses  = "CREATE TABLE IF NOT EXISTS courses (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";
        String createEnroll   = "CREATE TABLE IF NOT EXISTS enrollments (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, course_id INTEGER, "
                              + "FOREIGN KEY(student_id) REFERENCES students(id), FOREIGN KEY(course_id) REFERENCES courses(id));";

        try (Connection conn = getConnection(); Statement s = conn.createStatement()) {
            s.execute(createStudents);
            s.execute(createCourses);
            s.execute(createEnroll);
        } catch (SQLException e) {
            // Print stack trace to help debugging but don't crash the GUI.
            e.printStackTrace();
        }
    }

    public static void addStudent(String name) {
        String sql = "INSERT INTO students(name) VALUES(?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, name);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCourse(String name) {
        String sql = "INSERT INTO courses(name) VALUES(?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, name);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES(?,?)";
        try (Connection conn = getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, studentId);
            p.setInt(2, courseId);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listStudents() {
        List<String> out = new ArrayList<>();
        String sql = "SELECT id, name FROM students";
        try (Connection conn = getConnection(); Statement s = conn.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(rs.getInt("id") + ": " + rs.getString("name"));
            }
        } catch (SQLException e) {
            // If DB not available, return empty list — GUI remains useful for testing visual elements.
            // Print error for debugging.
            e.printStackTrace();
        }
        return out;
    }

    public static List<String> listCourses() {
        List<String> out = new ArrayList<>();
        String sql = "SELECT id, name FROM courses";
        try (Connection conn = getConnection(); Statement s = conn.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(rs.getInt("id") + ": " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<String> listEnrollments() {
        List<String> out = new ArrayList<>();
        String sql = "SELECT e.id, s.name as student, c.name as course FROM enrollments e "
                   + "JOIN students s ON e.student_id = s.id "
                   + "JOIN courses c ON e.course_id = c.id";
        try (Connection conn = getConnection(); Statement s = conn.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(rs.getInt("id") + ": " + rs.getString("student") + " -> " + rs.getString("course"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}
