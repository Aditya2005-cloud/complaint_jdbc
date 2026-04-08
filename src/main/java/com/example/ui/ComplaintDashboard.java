package com.example.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.impl.ComplaintServiceImpl;

public class ComplaintDashboard extends JFrame {

    ComplaintService service = new ComplaintServiceImpl();

    JTextField nameField, descField, statusField;
    JTextArea displayArea;

    public ComplaintDashboard() {

        setTitle("Complaint Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input fields
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);

        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);

        add(panel, BorderLayout.NORTH);

        // Text area for displaying complaints
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton procBtn = new JButton("Update via Procedure");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(procBtn);

        add(btnPanel, BorderLayout.SOUTH);

        loadDummyData();

        // Add button action
        addBtn.addActionListener(e -> {
            service.addComplaint(new Complaint(
                    nameField.getText(),
                    descField.getText(),
                    statusField.getText()
            ));
            showMessage("Added");
        });

        // View button action
        viewBtn.addActionListener(e -> {
            List<Complaint> list = service.getAllComplaints();
            displayArea.setText("");
            list.forEach(c ->
                    displayArea.append(c.getId() + " | "
                            + c.getName() + " | "
                            + c.getDescription() + " | "
                            + c.getStatus() + "\n"));
        });

        // Update button action
        updateBtn.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Complaint ID to update:");
            if (inputId != null) {
                try {
                    int id = Integer.parseInt(inputId);
                    service.updateComplaint(id, statusField.getText());
                    showMessage("Updated");
                } catch (Exception ex) {
                    showMessage(ex.getMessage());
                }
            }
        });

        // Delete button action
        deleteBtn.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Complaint ID to delete:");
            if (inputId != null) {
                try {
                    int id = Integer.parseInt(inputId);
                    service.deleteComplaint(id);
                    showMessage("Deleted");
                } catch (Exception ex) {
                    showMessage(ex.getMessage());
                }
            }
        });

        // Procedure button action
        procBtn.addActionListener(e -> {
            String inputId = JOptionPane.showInputDialog(this, "Enter Complaint ID to update via procedure:");
            if (inputId != null) {
                int id = Integer.parseInt(inputId);
                service.updateStatusUsingProcedure(id, statusField.getText());
                showMessage("Updated via Procedure");
            }
        });
    }

    private void loadDummyData() {
        service.addComplaint(new Complaint("Sony", "TV not working", "OPEN"));
        service.addComplaint(new Complaint("Amit", "Internet slow", "OPEN"));
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComplaintDashboard().setVisible(true));
    }
}