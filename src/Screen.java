import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.NumberFormatter;

import Controller.FacilityController;
import Model.FacilityModel;
import Model.FacilityTypeModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Screen extends JFrame {
    private List<FacilityModel> facilityList = new ArrayList<>();
    private JTable facilityTable;
    private DefaultTableModel facilityTableModel;
    private FacilityModel selectedFacility;
    private JComboBox<String> typeComboBox;
    private List<FacilityTypeModel> types;

    public Screen() {
        setTitle("PUC Espaços");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup the facility table
        String[] facilityColumnNames = {"Nome", "Capacidade", "Observações", "Tipo", "Está ativo?"};
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
        header.setBackground(Color.decode("#610127"));
        header.setForeground(Color.WHITE);

        // Combobox para o campo "Tipo"
        typeComboBox = new JComboBox<>();
        typeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // Buttons
        JButton addButton = new JButton("Criar Espaço");
        addButton.setBackground(Color.decode("#31458E"));
        addButton.setForeground(Color.WHITE);
        JButton editButton = new JButton("Editar Espaço");
        editButton.setBackground(Color.decode("#31458E"));
        editButton.setForeground(Color.WHITE);

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
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        getContentPane().add(facilityScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private NumberFormatter createNumberFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        return formatter;
    }

    private FacilityModel showFacilityDialog(FacilityModel facility) {
        
        JTextField idField = new JTextField(facility != null ? facility.getFacilityId() : UUID.randomUUID().toString());
        JTextField nameField = new JTextField(facility != null ? facility.getFacilityName() : "");
        JCheckBox activeCheckBox = new JCheckBox("Ativo?", facility != null && facility.isActive());
        
        JFormattedTextField capacityField = new JFormattedTextField(createNumberFormatter());
        if (facility != null) {
            capacityField.setValue(facility.getCapacity());
        }
        
        JTextField noteField = new JTextField(facility != null ? facility.getNote() : "");

        // Preencher combobox com os tipos de espaços
        typeComboBox.removeAllItems();
        try {
            types = FacilityController.getAllTypes();
            for (FacilityTypeModel type : types) {
                typeComboBox.addItem(type.getFacilityTypeDescription());
            }

            // Preenche com o valor do ativo caso tenha
            if (facility != null) {
                for (int i = 0; i < typeComboBox.getItemCount(); i++) {
                    FacilityTypeModel type = types.get(i);
                    if (type.getFacilityTypeId().equals(facility.getFacilityTypeId())) {
                        typeComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Tipo:"));
        panel.add(typeComboBox);
        panel.add(activeCheckBox);
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Capacidade:"));
        panel.add(capacityField);
        panel.add(new JLabel("Observações:"));
        panel.add(noteField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Espaço", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            // Obter o facilityTypeId baseado na seleção do usuário
            String selectedDescription = (String) typeComboBox.getSelectedItem();
            String selectedTypeId = "";
            for (FacilityTypeModel type : types) {
                if (type.getFacilityTypeDescription().equals(selectedDescription)) {
                    selectedTypeId = type.getFacilityTypeId();
                    break;
                }
            }

            return createOrUpdateFacility(
                facility, 
                idField.getText().trim(), 
                selectedTypeId, 
                activeCheckBox.isSelected(), 
                nameField.getText().trim(), 
                Integer.parseInt(capacityField.getText()), 
                noteField.getText().trim()
            );
        }
        return null;
    }

    private FacilityModel createOrUpdateFacility(FacilityModel facility, String facilityId, String facilityTypeId,
                                                 boolean isActive, String facilityName, Integer capacity, String note) {
        if (facility == null) {
            FacilityModel newFacility = new FacilityModel(facilityId, facilityTypeId, isActive, facilityName, capacity, note);
            FacilityController.create(newFacility);
            return newFacility;
        } else {
            // Atualizando espaço existente
            facility.setFacilityId(facilityId);
            facility.setFacilityTypeId(facilityTypeId);
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
                    facility.getFacilityName(),
                    facility.getCapacity(),
                    facility.getNote(),
                    facility.getFacilityTypeDescription(),
                    facility.isActive()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Screen screen = new Screen();

                try {
                    screen.facilityList = FacilityController.getAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Atualizando a tabela de espaços após adicionar os dados iniciais
                screen.updateFacilityTable();

                // Exibindo a tela
                screen.setVisible(true);
            }
        });
    }
}
