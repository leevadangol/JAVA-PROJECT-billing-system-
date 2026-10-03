package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Read-only report of all products stored in MySQL. */
public class ReportFrame extends JFrame {

    private final DefaultTableModel tableModel = UIHelper.createTableModel(false);
    private final JTable table = UIHelper.createTable(tableModel);
    private final JLabel totalLabel = new JLabel();

    public ReportFrame(HomeFrame home) {
        UIHelper.setupFrame(this, "Billing System - Report", 850, 520);
        UIHelper.returnHomeOnClose(this, home);

        add(UIHelper.createHeader("Product Report"), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        totalLabel.setFont(UIHelper.BOLD);
        JButton refreshButton = UIHelper.createButton("Refresh", UIHelper.SUCCESS);
        JButton backButton = UIHelper.createButton("Back", UIHelper.PRIMARY);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(UIHelper.BACKGROUND);
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 14, 0, 14));
        bottom.add(totalLabel, BorderLayout.WEST);
        bottom.add(UIHelper.createButtonPanel(refreshButton, backButton), BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadReport());
        backButton.addActionListener(e -> dispose());

        loadReport();
    }

    private void loadReport() {
        int count = UIHelper.loadProducts(this, tableModel, false);
        totalLabel.setText("Total products: " + count);
    }
}
