import java.util.Scanner;
import java.sql.*;

public class LibraryApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Db.init(); // create tables if not exist
        while (true) {
            System.out.println("\n=== Library ===");
            System.out.println("1) Add book  2) List books  3) Add member  4) List members  5) Exit");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            try {
                switch (ch) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        listBooks();
                        break;
                    case "3":
                        addMember();
                        break;
                    case "4":
                        listMembers();
                        break;
                    case "5":
                        System.out.println("Bye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addBook() throws SQLException {
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        String sql = "INSERT INTO books(title, author) VALUES(?,?)";
        try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            System.out.println("Book added.");
        }
    }

    private static void listBooks() throws SQLException {
        String sql = "SELECT id, title, author FROM books ORDER BY id";
        try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            System.out.println("ID | Title - Author");
            while (rs.next()) {
                System.out.printf("%d | %s - %s%n", rs.getInt("id"), rs.getString("title"), rs.getString("author"));
            }
        }
    }

    private static void addMember() throws SQLException {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        String sql = "INSERT INTO members(name, email) VALUES(?,?)";
        try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Member added.");
        }
    }

    private static void listMembers() throws SQLException {
        String sql = "SELECT id, name, email FROM members ORDER BY id";
        try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            System.out.println("ID | Name - Email");
            while (rs.next()) {
                System.out.printf("%d | %s - %s%n", rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }
    }
}
