import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LMSApp {
    private static JTextArea outputArea;
    private static JTextField studentField;
    private static JTextField courseField;
    private static JTextField enrollStudentId;
    private static JTextField enrollCourseId;

    public LMSApp() {
    }

    public static void main(String[] args) {
        try {
            Database.initDatabase();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Simple LMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 420);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Student Panel
        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentField = new JTextField(20);
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(e -> {
            String name = studentField.getText().trim();
            if (!name.isEmpty()) {
                Database.addStudent(name);
                studentField.setText("");
                refreshOutput();
            }
        });
        studentPanel.add(new JLabel("Student name:"));
        studentPanel.add(studentField);
        studentPanel.add(addStudentBtn);

        // Course Panel
        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        courseField = new JTextField(20);
        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.addActionListener(e -> {
            String name = courseField.getText().trim();
            if (!name.isEmpty()) {
                Database.addCourse(name);
                courseField.setText("");
                refreshOutput();
            }
        });
        coursePanel.add(new JLabel("Course name:"));
        coursePanel.add(courseField);
        coursePanel.add(addCourseBtn);

        // Enrollment Panel
        JPanel enrollPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enrollStudentId = new JTextField(4);
        enrollCourseId = new JTextField(4);
        JButton enrollBtn = new JButton("Enroll");
        enrollBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(enrollStudentId.getText().trim());
                int courseId = Integer.parseInt(enrollCourseId.getText().trim());
                Database.enrollStudent(studentId, courseId);
                enrollStudentId.setText("");
                enrollCourseId.setText("");
                refreshOutput();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric IDs for student and course.");
            }
        });
        enrollPanel.add(new JLabel("Student ID:"));
        enrollPanel.add(enrollStudentId);
        enrollPanel.add(new JLabel("Course ID:"));
        enrollPanel.add(enrollCourseId);
        enrollPanel.add(enrollBtn);

        // Refresh Panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh Lists");
        refreshBtn.addActionListener(e -> {
            refreshOutput();
        });
        refreshPanel.add(refreshBtn);

        // Output Area
        outputArea = new JTextArea(14, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        mainPanel.add(studentPanel);
        mainPanel.add(coursePanel);
        mainPanel.add(enrollPanel);
        mainPanel.add(refreshPanel);
        mainPanel.add(scrollPane);

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshOutput();
    }

    private static void refreshOutput() {
        StringBuilder output = new StringBuilder();
        output.append("Students:\n");
        List<String> students = Database.listStudents();
        for (String student : students) {
            output.append(student).append('\n');
        }

        output.append('\n').append("Courses:\n");
        List<String> courses = Database.listCourses();
        for (String course : courses) {
            output.append(course).append('\n');
        }

        output.append('\n').append("Enrollments:\n");
        List<String> enrollments = Database.listEnrollments();
        for (String enrollment : enrollments) {
            output.append(enrollment).append('\n');
        }

        outputArea.setText(output.toString());
    }
}