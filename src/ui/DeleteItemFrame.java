package ui;

import dao.ProductDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class DeleteItemFrame extends JFrame {

    private final DefaultTableModel tableModel = UIHelper.createTableModel(true);
    private final JTable table = UIHelper.createTable(tableModel);
    private final ProductDAO productDAO = new ProductDAO();

    public DeleteItemFrame(HomeFrame home) {
        UIHelper.setupFrame(this, "Billing System - Delete Item", 850, 520);
        UIHelper.returnHomeOnClose(this, home);

        add(UIHelper.createHeader("Delete Item"), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = UIHelper.createButton("Delete", UIHelper.DANGER);
        JButton backButton = UIHelper.createButton("Back", UIHelper.PRIMARY);
        add(UIHelper.createButtonPanel(deleteButton, backButton), BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> deleteProduct());
        backButton.addActionListener(e -> dispose());

        UIHelper.loadProducts(this, tableModel, true);
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            UIHelper.showError(this, "Please select a product to delete.");
            return;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        String name = String.valueOf(tableModel.getValueAt(row, 1));

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete \"" + name + "\"?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            productDAO.deleteProduct(id);
            UIHelper.loadProducts(this, tableModel, true);
            UIHelper.showInfo(this, "Product deleted successfully.");
        } catch (SQLException ex) {
            UIHelper.showDbError(this, ex);
        }
    }
}
