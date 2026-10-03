package ui;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** Input form (name, description, quantity, price) shared by the Add and Update screens. */
public class ProductFormPanel extends JPanel {

    private final JTextField nameField = new JTextField(25);
    private final JTextField descriptionField = new JTextField(25);
    private final JTextField quantityField = new JTextField(25);
    private final JTextField priceField = new JTextField(25);

    public ProductFormPanel() {
        setLayout(new GridBagLayout());
        setBackground(UIHelper.BACKGROUND);
        addRow(0, "Product Name:", nameField);
        addRow(1, "Description:", descriptionField);
        addRow(2, "Quantity:", quantityField);
        addRow(3, "Price:", priceField);
    }

    private void addRow(int row, String labelText, JTextField field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(UIHelper.BOLD);
        field.setFont(UIHelper.NORMAL);
        field.setPreferredSize(new Dimension(300, 30));

        gbc.gridx = 0;
        add(label, gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    /** Validates the fields. Returns a Product, or null (after showing an error) if input is invalid. */
    public Product readProduct() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty()) {
            UIHelper.showError(this, "Product Name cannot be empty.");
            nameField.requestFocus();
            return null;
        }
        if (name.length() > 100) {
            UIHelper.showError(this, "Product Name must be 100 characters or less.");
            return null;
        }
        if (description.isEmpty()) {
            UIHelper.showError(this, "Description cannot be empty.");
            descriptionField.requestFocus();
            return null;
        }
        if (description.length() > 255) {
            UIHelper.showError(this, "Description must be 255 characters or less.");
            return null;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Quantity must be a valid whole number (example: 10).");
            quantityField.requestFocus();
            return null;
        }
        if (quantity <= 0) {
            UIHelper.showError(this, "Quantity must be greater than 0.");
            quantityField.requestFocus();
            return null;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceText);
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Price must be a valid number (example: 49.99).");
            priceField.requestFocus();
            return null;
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            UIHelper.showError(this, "Price must be greater than 0.");
            priceField.requestFocus();
            return null;
        }
        if (price.compareTo(new BigDecimal("99999999.99")) > 0) {
            UIHelper.showError(this, "Price is too large.");
            return null;
        }
        price = price.setScale(2, RoundingMode.HALF_UP);

        return new Product(name, description, quantity, price);
    }

    public void setValues(String name, String description, String quantity, String price) {
        nameField.setText(name);
        descriptionField.setText(description);
        quantityField.setText(quantity);
        priceField.setText(price);
    }

    public void clear() {
        setValues("", "", "", "");
        nameField.requestFocus();
    }
}
