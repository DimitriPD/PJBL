import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Controller.FacilityController;
import Model.FacilityModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Screen extends JFrame {
    private List<FacilityModel> facilityList = new ArrayList<>();
    private JTable facilityTable;
    private DefaultTableModel facilityTableModel;
    private FacilityModel selectedFacility;

    public Screen() {
        setTitle("PUC Espaços");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup the facility table
        String[] facilityColumnNames = {"Tipo", "Está ativo?", "Nome", "Capacidade", "Observações"};
        facilityTableModel = new DefaultTableModel(facilityColumnNames, 0);
        facilityTable = new JTable(facilityTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        facilityTable.setColumnSelectionAllowed(false);
        facilityTable.setRowSelectionAllowed(true);
        facilityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane facilityScrollPane = new JScrollPane(facilityTable);

        // Estilizando tabela
        facilityTable.setFont(new Font("Arial", Font.PLAIN, 14));
        facilityTable.setRowHeight(30);

        JTableHeader header = facilityTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(Color.BLUE);
        header.setForeground(Color.WHITE);

        // Buttons
        JButton addButton = new JButton("Criar Espaço");
        JButton editButton = new JButton("Editar Espaço");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacilityModel newFacility = showFacilityDialog(null);
                if (newFacility != null) {
                    facilityList.add(newFacility);
                    updateFacilityTable();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = facilityTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedFacility = facilityList.get(selectedRow);
                    FacilityModel updatedFacility = showFacilityDialog(selectedFacility);
                    if (updatedFacility != null) {
                        facilityList.set(selectedRow, updatedFacility);
                        updateFacilityTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um espaço para edição.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(facilityScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private FacilityModel showFacilityDialog(FacilityModel facility) {
        JTextField idField = new JTextField(facility != null ? facility.getFacilityId() : "");
        JTextField typeIdField = new JTextField(facility != null ? facility.getFacilityTypeId() : "");
        JTextField typeDescriptionField = new JTextField(facility != null ? facility.getFacilityTypeDescription() : "");
        JCheckBox activeCheckBox = new JCheckBox("Ativo?", facility != null && facility.isActive());
        JTextField nameField = new JTextField(facility != null ? facility.getFacilityName() : "");
        JTextField capacityField = new JTextField(facility != null ? facility.getCapacity() : "");
        JTextField noteField = new JTextField(facility != null ? facility.getNote() : "");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Tipo:"));
        panel.add(typeDescriptionField);
        panel.add(activeCheckBox);
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Capacidade:"));
        panel.add(capacityField);
        panel.add(new JLabel("Observações:"));
        panel.add(noteField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Espaço", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return createOrUpdateFacility(facility, idField.getText().trim(), typeIdField.getText().trim(), typeDescriptionField.getText().trim(),
                    activeCheckBox.isSelected(), nameField.getText().trim(), capacityField.getText().trim(), noteField.getText().trim());
        }
        return null;
    }

    private FacilityModel createOrUpdateFacility(FacilityModel facility, String facilityId, String facilityTypeId, String facilityTypeDescription,
                                                 boolean isActive, String facilityName, String capacity, String note) {
        if (facility == null) {
            // Creating a new facility
            FacilityModel newFacility = new FacilityModel(facilityId, facilityTypeId, isActive, facilityName, capacity, note, new ArrayList<>());
            FacilityController.create(newFacility);
            return newFacility;

        } else {
            // Updating an existing facility
            facility.setFacilityId(facilityId);
            facility.setFacilityTypeId(facilityTypeId);
            facility.setFacilityTypeDescription(facilityTypeDescription);
            facility.setActive(isActive);
            facility.setFacilityName(facilityName);
            facility.setCapacity(capacity);
            facility.setNote(note);

            try {
                FacilityController.update(facility);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return facility;
        }
    }

    private void updateFacilityTable() {
        facilityTableModel.setRowCount(0);
        for (FacilityModel facility : facilityList) {
            facilityTableModel.addRow(new Object[]{
                    facility.getFacilityTypeDescription(),
                    facility.isActive(),
                    facility.getFacilityName(),
                    facility.getCapacity(),
                    facility.getNote()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Screen facilityManagementApp = new Screen();

                try {
                    facilityManagementApp.facilityList = FacilityController.getAll();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Atualizando a tabela de instalações após adicionar os dados iniciais
                facilityManagementApp.updateFacilityTable();

                // Exibindo o aplicativo
                facilityManagementApp.setVisible(true);
            }
        });
    }
}
