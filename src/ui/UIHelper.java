package ui;

import dao.ProductDAO;
import database.DatabaseConnection;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/** Small helper methods so every screen looks the same and code is not repeated. */
public class UIHelper {

    public static final Color PRIMARY = new Color(41, 98, 168);
    public static final Color SUCCESS = new Color(39, 147, 84);
    public static final Color DANGER = new Color(200, 60, 60);
    public static final Color NEUTRAL = new Color(110, 120, 130);
    public static final Color BACKGROUND = new Color(244, 246, 249);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BOLD = new Font("SansSerif", Font.BOLD, 14);

    private UIHelper() { }

    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND);
        frame.setLayout(new BorderLayout());
    }

    public static JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BOLD);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 36));
        return button;
    }

    public static JPanel createHeader(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        panel.setBackground(PRIMARY);
        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        panel.add(label);
        return panel;
    }

    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panel.setBackground(BACKGROUND);
        for (JButton b : buttons) {
            panel.add(b);
        }
        return panel;
    }

    /** Table model that cannot be edited by typing in the cells. */
    public static DefaultTableModel createTableModel(boolean showId) {
        String[] columns = showId
                ? new String[]{"ID", "Product Name", "Description", "Quantity", "Price"}
                : new String[]{"Product Name", "Description", "Quantity", "Price"};
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(NORMAL);
        table.setRowHeight(26);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(BOLD);
        table.getTableHeader().setReorderingAllowed(false);
        return table;
    }

    /** Reads all products from MySQL into the table model. Returns number of rows loaded. */
    public static int loadProducts(Component parent, DefaultTableModel model, boolean showId) {
        model.setRowCount(0);
        try {
            java.util.List<Product> products = new ProductDAO().getAllProducts();
            for (Product p : products) {
                if (showId) {
                    model.addRow(new Object[]{p.getId(), p.getProductName(), p.getDescription(),
                            p.getQuantity(), p.getPrice()});
                } else {
                    model.addRow(new Object[]{p.getProductName(), p.getDescription(),
                            p.getQuantity(), p.getPrice()});
                }
            }
            return products.size();
        } catch (SQLException e) {
            showDbError(parent, e);
            return 0;
        }
    }

    public static void showDbError(Component parent, SQLException e) {
        JOptionPane.showMessageDialog(parent, DatabaseConnection.getFriendlyMessage(e),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /** When this frame is closed (Back button or X), the Home page is shown again. */
    public static void returnHomeOnClose(JFrame current, JFrame home) {
        current.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        current.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                home.setVisible(true);
            }
        });
    }
}
