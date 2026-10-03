package ui;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class UpdateItemFrame extends JFrame {

    private final DefaultTableModel tableModel = UIHelper.createTableModel(true);
    private final JTable table = UIHelper.createTable(tableModel);
    private final ProductFormPanel form = new ProductFormPanel();
    private final ProductDAO productDAO = new ProductDAO();

    public UpdateItemFrame(HomeFrame home) {
        UIHelper.setupFrame(this, "Billing System - Update Item", 850, 680);
        UIHelper.returnHomeOnClose(this, home);

        add(UIHelper.createHeader("Update Item"), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = UIHelper.createButton("Update", UIHelper.SUCCESS);
        JButton clearButton = UIHelper.createButton("Clear", UIHelper.NEUTRAL);
        JButton backButton = UIHelper.createButton("Back", UIHelper.PRIMARY);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(UIHelper.BACKGROUND);
        bottom.add(form, BorderLayout.CENTER);
        bottom.add(UIHelper.createButtonPanel(updateButton, clearButton, backButton), BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        // When a row is selected, show it in the form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });

        updateButton.addActionListener(e -> updateProduct());
        clearButton.addActionListener(e -> {
            table.clearSelection();
            form.clear();
        });
        backButton.addActionListener(e -> dispose());

        UIHelper.loadProducts(this, tableModel, true);
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return;
        }
        form.setValues(
                String.valueOf(tableModel.getValueAt(row, 1)),
                String.valueOf(tableModel.getValueAt(row, 2)),
                String.valueOf(tableModel.getValueAt(row, 3)),
                String.valueOf(tableModel.getValueAt(row, 4)));
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            UIHelper.showError(this, "Please select a product from the table first.");
            return;
        }
        Product product = form.readProduct();
        if (product == null) {
            return;
        }
        product.setId((Integer) tableModel.getValueAt(row, 0));

        try {
            productDAO.updateProduct(product);
            UIHelper.loadProducts(this, tableModel, true);
            form.clear();
            UIHelper.showInfo(this, "Product updated successfully.");
        } catch (SQLException ex) {
            UIHelper.showDbError(this, ex);
        }
    }
}
