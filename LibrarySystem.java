/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarysystem;

/**
 *
 * @author faith
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LibrarySystem {
    private JFrame frame;
    private JTextField BookIDField, TitleField, AuthorField, YearField;
    private JButton AddButton, DeleteButton, RefreshButton;
    private JTable bookTable;
    private DefaultTableModel model;

    public LibrarySystem() {
        // Initialize the GUI components here
        // Connect to the database
        // Add action listeners to the buttons
    }

    private Connection connectDatabase() {
        Connection conn = null;
    try {
        String url = "jdbc:ucanaccess:\"D:\\My Projects\\LibrarySystem.accdb\"";
        conn = DriverManager.getConnection(url);
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return conn;
    }

    private void add() {
        AddButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String BookID = BookIDField.getText();
        String Title = TitleField.getText();
        String Author = AuthorField.getText();
        int Year = Integer.parseInt(YearField.getText());

        String sql = "INSERT INTO Books(BookID, Title, Author, Year) VALUES(?, ?, ?, ?)";

        try (Connection conn = connectDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, BookID);
            pstmt.setString(2, Title);
            pstmt.setString(3, Author);
            pstmt.setInt(4, Year);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
});
    }

    private void delete() {
       DeleteButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        String bookID = bookTable.getValueAt(selectedRow, 0).toString();

        String sql = "DELETE FROM Books WHERE BookID = ?";

        try (Connection conn = connectDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
});

    }
    
    private void refresh() {
       RefreshButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String sql = "SELECT * FROM Books";

        try (Connection conn = connectDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Clear the existing data from the table
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            model.setRowCount(0);

            // Add the rows to the table
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("BookID"), rs.getString("Title"), rs.getString("Author"), rs.getInt("Year")});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
});

    }

    private void viewBooks() {
        // Retrieve and display all book records in the table
    }

    public static void main(String[] args) {
        // Create and display the GUI
    }
}
