package ui;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddItemFrame extends JFrame {

    private final ProductFormPanel form = new ProductFormPanel();
    private final ProductDAO productDAO = new ProductDAO();

    public AddItemFrame(HomeFrame home) {
        UIHelper.setupFrame(this, "Billing System - Add Item", 600, 460);
        UIHelper.returnHomeOnClose(this, home);

        add(UIHelper.createHeader("Add Item"), BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        center.setBackground(UIHelper.BACKGROUND);
        center.add(form);
        add(center, BorderLayout.CENTER);

        JButton addButton = UIHelper.createButton("Add", UIHelper.SUCCESS);
        JButton clearButton = UIHelper.createButton("Clear", UIHelper.NEUTRAL);
        JButton backButton = UIHelper.createButton("Back", UIHelper.PRIMARY);
        add(UIHelper.createButtonPanel(addButton, clearButton, backButton), BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        clearButton.addActionListener(e -> form.clear());
        backButton.addActionListener(e -> dispose());
    }

    private void addProduct() {
        Product product = form.readProduct();
        if (product == null) {
            return;
        }
        try {
            productDAO.addProduct(product);
            UIHelper.showInfo(this, "Product added successfully.");
            form.clear();
        } catch (SQLException ex) {
            UIHelper.showDbError(this, ex);
        }
    }
}
