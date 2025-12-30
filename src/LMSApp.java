import java.awt.FlowLayout;
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
    private static JTextField deleteStudentId;
    private static JTextField deleteCourseId;
    private static JTextField deleteEnrollmentId;

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
        frame.setSize(640, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Student Panel
        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentField = new JTextField(20);
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(e -> {
            try {
                String name = studentField.getText().trim();
                if (!name.isEmpty()) {
                    Database.addStudent(name);
                    studentField.setText("");
                    refreshOutput();
                    JOptionPane.showMessageDialog(frame, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a student name.", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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
            try {
                String name = courseField.getText().trim();
                if (!name.isEmpty()) {
                    Database.addCourse(name);
                    courseField.setText("");
                    refreshOutput();
                    JOptionPane.showMessageDialog(frame, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a course name.", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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
                JOptionPane.showMessageDialog(frame, "Student enrolled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric IDs for student and course.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error enrolling student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        enrollPanel.add(new JLabel("Student ID:"));
        enrollPanel.add(enrollStudentId);
        enrollPanel.add(new JLabel("Course ID:"));
        enrollPanel.add(enrollCourseId);
        enrollPanel.add(enrollBtn);

        // Delete Student Panel
        JPanel deleteStudentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteStudentId = new JTextField(4);
        JButton deleteStudentBtn = new JButton("Delete Student");
        deleteStudentBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(deleteStudentId.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to delete student ID " + studentId + "?\nThis will also delete all their enrollments.", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = Database.deleteStudent(studentId);
                    if (success) {
                        deleteStudentId.setText("");
                        refreshOutput();
                        JOptionPane.showMessageDialog(frame, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student ID not found or could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid numeric student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        deleteStudentPanel.add(new JLabel("Delete Student ID:"));
        deleteStudentPanel.add(deleteStudentId);
        deleteStudentPanel.add(deleteStudentBtn);

        // Delete Course Panel
        JPanel deleteCoursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteCourseId = new JTextField(4);
        JButton deleteCourseBtn = new JButton("Delete Course");
        deleteCourseBtn.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(deleteCourseId.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to delete course ID " + courseId + "?\nThis will also delete all enrollments for this course.", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = Database.deleteCourse(courseId);
                    if (success) {
                        deleteCourseId.setText("");
                        refreshOutput();
                        JOptionPane.showMessageDialog(frame, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Course ID not found or could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid numeric course ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        deleteCoursePanel.add(new JLabel("Delete Course ID:"));
        deleteCoursePanel.add(deleteCourseId);
        deleteCoursePanel.add(deleteCourseBtn);

        // Delete Enrollment Panel
        JPanel deleteEnrollmentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteEnrollmentId = new JTextField(4);
        JButton deleteEnrollmentBtn = new JButton("Delete Enrollment");
        deleteEnrollmentBtn.addActionListener(e -> {
            try {
                int enrollmentId = Integer.parseInt(deleteEnrollmentId.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to delete enrollment ID " + enrollmentId + "?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = Database.deleteEnrollment(enrollmentId);
                    if (success) {
                        deleteEnrollmentId.setText("");
                        refreshOutput();
                        JOptionPane.showMessageDialog(frame, "Enrollment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Enrollment ID not found or could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid numeric enrollment ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting enrollment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        deleteEnrollmentPanel.add(new JLabel("Delete Enrollment ID:"));
        deleteEnrollmentPanel.add(deleteEnrollmentId);
        deleteEnrollmentPanel.add(deleteEnrollmentBtn);

        // Refresh Panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh Lists");
        refreshBtn.addActionListener(e -> {
            try {
                refreshOutput();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error refreshing data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        refreshPanel.add(refreshBtn);

        // Output Area
        outputArea = new JTextArea(14, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        mainPanel.add(studentPanel);
        mainPanel.add(coursePanel);
        mainPanel.add(enrollPanel);
        mainPanel.add(deleteStudentPanel);
        mainPanel.add(deleteCoursePanel);
        mainPanel.add(deleteEnrollmentPanel);
        mainPanel.add(refreshPanel);
        mainPanel.add(scrollPane);

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshOutput();
    }

    private static void refreshOutput() {
        try {
            StringBuilder output = new StringBuilder();
            output.append("Students:\n");
            List<String> students = Database.listStudents();
            if (students.isEmpty()) {
                output.append("(No students)\n");
            } else {
                for (String student : students) {
                    output.append(student).append('\n');
                }
            }

            output.append('\n').append("Courses:\n");
            List<String> courses = Database.listCourses();
            if (courses.isEmpty()) {
                output.append("(No courses)\n");
            } else {
                for (String course : courses) {
                    output.append(course).append('\n');
                }
            }

            output.append('\n').append("Enrollments:\n");
            List<String> enrollments = Database.listEnrollments();
            if (enrollments.isEmpty()) {
                output.append("(No enrollments)\n");
            } else {
                for (String enrollment : enrollments) {
                    output.append(enrollment).append('\n');
                }
            }

            outputArea.setText(output.toString());
        } catch (Exception e) {
            outputArea.setText("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}