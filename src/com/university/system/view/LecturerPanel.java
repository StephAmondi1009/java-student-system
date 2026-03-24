package com.university.system.view;

import com.university.system.controller.LecturerController;
import com.university.system.model.Lecturer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class LecturerPanel extends JPanel {
    private LecturerController lecturerController;
    private JTable lecturerTable;
    private DefaultTableModel tableModel;

    private JTextField firstNameField, lastNameField, emailField, phoneField, staffNumField, departmentField, specializationField;

    public LecturerPanel() {
        lecturerController = new LecturerController();
        setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"ID", "Staff Number", "First Name", "Last Name", "Email", "Department", "Specialization"};
        tableModel = new DefaultTableModel(columnNames, 0);
        lecturerTable = new JTable(tableModel);
        add(new JScrollPane(lecturerTable), BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneField = new JTextField(15);
        staffNumField = new JTextField(15);
        departmentField = new JTextField(15);
        specializationField = new JTextField(15);

        addFormField(formPanel, "First Name:", firstNameField, gbc, 0);
        addFormField(formPanel, "Last Name:", lastNameField, gbc, 1);
        addFormField(formPanel, "Email:", emailField, gbc, 2);
        addFormField(formPanel, "Phone:", phoneField, gbc, 3);
        addFormField(formPanel, "Staff Number:", staffNumField, gbc, 4);
        addFormField(formPanel, "Department:", departmentField, gbc, 5);
        addFormField(formPanel, "Specialization:", specializationField, gbc, 6);

        JButton addButton = new JButton("Add Lecturer");
        addButton.addActionListener(e -> addLecturer());
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        add(formPanel, BorderLayout.WEST);

        refreshData();
    }

    private void addFormField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addLecturer() {
        Lecturer lecturer = new Lecturer();
        lecturer.setFirstName(firstNameField.getText());
        lecturer.setLastName(lastNameField.getText());
        lecturer.setEmail(emailField.getText());
        lecturer.setPhone(phoneField.getText());
        lecturer.setStaffNumber(staffNumField.getText());
        lecturer.setDepartment(departmentField.getText());
        lecturer.setSpecialization(specializationField.getText());
        lecturer.setHireDate(LocalDate.now());
        lecturer.setActive(true);

        if (lecturerController.addLecturer(lecturer)) {
            JOptionPane.showMessageDialog(this, "Lecturer added successfully!");
            refreshData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add lecturer.");
        }
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Lecturer> lecturers = lecturerController.getAllLecturers();
        for (Lecturer l : lecturers) {
            tableModel.addRow(new Object[]{
                l.getId(), l.getStaffNumber(), l.getFirstName(), l.getLastName(),
                l.getEmail(), l.getDepartment(), l.getSpecialization()
            });
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        staffNumField.setText("");
        departmentField.setText("");
        specializationField.setText("");
    }
}
