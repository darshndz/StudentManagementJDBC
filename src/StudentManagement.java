import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n====== Student Management ======");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> deleteStudent();
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while(choice != 4);
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Connection failed!"); return; }
            String sql = "INSERT INTO students VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("Student inserted successfully!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void viewStudents() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Connection failed!"); return; }
            String sql = "SELECT * FROM students";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\nID\tName\tAge");
            System.out.println("------------------------");
            while(rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getInt("age"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Connection failed!"); return; }
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if(rows > 0) System.out.println("Student deleted successfully!");
            else System.out.println("No student found with ID " + id);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
