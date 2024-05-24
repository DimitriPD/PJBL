import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Controller.FacilityController;
import Model.FacilityModel;
import Model.FacilityTypeModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FacilitiesScreen extends JFrame {
    private List<FacilityModel> facilityList = new ArrayList<>();
    private JTable facilityTable;
    private DefaultTableModel facilityTableModel;
    private FacilityModel selectedFacility;
    private JComboBox<String> typeComboBox;
    private List<FacilityTypeModel> types;

    public FacilitiesScreen() {
        setTitle("PUC Espaços");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria tabela de Espaços
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

        // Botões
        JButton addButton = new JButton("Criar Espaço");
        addButton.setBackground(Color.decode("#31458E"));
        addButton.setForeground(Color.WHITE);
        JButton editButton = new JButton("Editar Espaço");
        editButton.setBackground(Color.decode("#31458E"));
        editButton.setForeground(Color.WHITE);
        JButton openFacilitiesButton = new JButton("Gerenciar Ativos de Espaço");
        openFacilitiesButton.setBackground(Color.decode("#31458E"));
        openFacilitiesButton.setForeground(Color.WHITE);


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

        openFacilitiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = facilityTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedFacility = facilityList.get(selectedRow);
                    
                    // Desabilitando a tela anterior
                    setEnabled(false);
                    
                    AssetsScreen assetsScreen = new AssetsScreen(selectedFacility);
                    assetsScreen.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // Habilitando a tela anterior quando a nova tela for fechada
                            setEnabled(true);
                            updateTheHoleFacility(selectedFacility);
                        }
                    });
                    assetsScreen.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um espaço para gerenciar ativos.");
                }
            }
        });
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(openFacilitiesButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        getContentPane().add(facilityScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private FacilityModel showFacilityDialog(FacilityModel facility) {
        
        JTextField idField = new JTextField(facility != null ? facility.getFacilityId() : UUID.randomUUID().toString());
        JTextField nameField = new JTextField(facility != null ? facility.getFacilityName() : "");
        JCheckBox activeCheckBox = new JCheckBox("Ativo?", facility != null && facility.isActive());
        JTextField capacityField = new JTextField(facility != null ? String.valueOf(facility.getCapacity()) : "");
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

        boolean isValidInput = false;
        while (!isValidInput) {
            String title = (facility != null) ? "Editar Espaço" : "Criar Espaço";
           int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
           if (result == JOptionPane.OK_OPTION) {
               try {
                    int capacity = 0;
                    isValidInput = true;
                    if (nameField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Informe o nome do espaço.");
                        isValidInput = false;
                    }
                    else if (!capacityField.getText().trim().isEmpty()) {
   
                        capacity = Integer.parseInt(capacityField.getText());
                        if (capacity < 1 || capacity > 2000) {
                            JOptionPane.showMessageDialog(null, "A capacidade quando informada, deve ser um número positivo entre 1 e 2000.");
                            isValidInput = false;
                        }
                    } 
                    if (isValidInput) {                        
                        // Obter o facilityTypeId baseado na seleção do usuário
                        String selectedDescription = (String) typeComboBox.getSelectedItem();
                        String selectedTypeId = "";
                        for (FacilityTypeModel type : types) {
                            if (type.getFacilityTypeDescription().equals(selectedDescription)) {
                                selectedTypeId = type.getFacilityTypeId();
                                break;
                            }
                        }
                        isValidInput = true; // Sair do loop se a entrada for válida

                        return createOrUpdateFacility(
                            facility, 
                            idField.getText().trim(), 
                            selectedTypeId, 
                            selectedDescription, 
                            activeCheckBox.isSelected(),
                            nameField.getText().trim(), 
                            capacity, 
                            noteField.getText().trim()
                        );
                   }
               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(null, "A capacidade deve ser um número válido.");
                   isValidInput = false;
                }
           } else {
               // Se o usuário cancelar, sair do loop
               break;
           }
       }
        return null;
    }

    private FacilityModel createOrUpdateFacility(FacilityModel facility, String facilityId, String facilityTypeId, String               facilityTypeDescription,boolean isActive, String facilityName, Integer capacity, String note) {
        
        if (facility == null) {
            FacilityModel newFacility = new FacilityModel(facilityId, facilityTypeId, isActive, facilityName, capacity, note);
            newFacility.setFacilityTypeDescription(facilityTypeDescription);
            FacilityController.create(newFacility);
            return newFacility;
        } else {
            // Atualizando espaço existente
            facility.setFacilityId(facilityId);
            facility.setFacilityTypeId(facilityTypeId);
            facility.setFacilityTypeDescription(facilityTypeDescription);
            facility.setActive(isActive);
            facility.setFacilityName(facilityName);
            if (capacity != 0) {
                facility.setCapacity(capacity);
            }
            facility.setNote(note);

            try {
                FacilityController.update(facility);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return facility;
        }
    }


    private FacilityModel updateTheHoleFacility(FacilityModel facility) {
        try {
            FacilityController.update(facility);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facility;
    }

    private void updateFacilityTable() {
        facilityTableModel.setRowCount(0);
        for (FacilityModel facility : facilityList) {
            facilityTableModel.addRow(new Object[]{
                    facility.getFacilityName(),
                    (facility.getCapacity() != 0) ? facility.getCapacity() : "",
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
                FacilitiesScreen screen = new FacilitiesScreen();

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
