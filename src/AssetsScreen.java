import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Controller.AssetController;
import Model.AssetModel;
import Model.FacilityAssetModel;
import Model.FacilityModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssetsScreen extends JFrame {

    private FacilityModel selectedFacility;
    private JTable assetTable;
    private DefaultTableModel assetTableModel;
    private JComboBox<String> assetComboBox;
    private ArrayList<AssetModel> assets;

    public AssetsScreen(FacilityModel selectedFacility) {

        this.selectedFacility = selectedFacility;
        setTitle("Gerenciar Ativos de Espaço");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar a tabela de ativos
        String[] assetColumnNames = { "Descrição", "Quantidade" };
        assetTableModel = new DefaultTableModel(assetColumnNames, 0);
        assetTable = new JTable(assetTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane assetScrollPane = new JScrollPane(assetTable);

        // Estilizando tabela
        assetTable.setFont(new Font("Arial", Font.PLAIN, 14));
        assetTable.setRowHeight(30);

        JTableHeader header = assetTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(Color.decode("#610127"));
        header.setForeground(Color.WHITE);

        // Combobox para o campo "Tipo"
        assetComboBox = new JComboBox<>();
        assetComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botões
        JButton addAssetButton = new JButton("Adicionar Ativo");
        addAssetButton.setBackground(Color.decode("#31458E"));
        addAssetButton.setForeground(Color.WHITE);
        JButton editAssetButton = new JButton("Editar Ativo");
        editAssetButton.setBackground(Color.decode("#31458E"));
        editAssetButton.setForeground(Color.WHITE);
        JButton deleteAssetButton = new JButton("Excluir Ativo");
        deleteAssetButton.setBackground(Color.decode("#31458E"));
        deleteAssetButton.setForeground(Color.WHITE);

        addAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAssetDialog();
                 /* 
                 Lógica para adicionar um novo ativo
                 Será necessário buscar todos os ativos, filtrar os que ainda não estão no selectedFacility e inserir estes numa combox para seleção
                 */
            }
        });

        editAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = assetTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Obter o ativo selecionado
                    FacilityAssetModel selectedAsset = selectedFacility.getAssets().get(selectedRow);
                    // Abrir diálogo de edição de ativo
                    editAssetDialog(selectedAsset);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um ativo para editar.");
                }
            }
        });

        deleteAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para excluir um ativo
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAssetButton);
        buttonPanel.add(editAssetButton);
        buttonPanel.add(deleteAssetButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(assetScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        updateAssetTable();
    }

    // Método para atualizar a tabela de ativos
    private void updateAssetTable() {
        assetTableModel.setRowCount(0);
        for (FacilityAssetModel asset : selectedFacility.getAssets()) {
            assetTableModel.addRow(new Object[] {
                    asset.getAssetDescription(),
                    asset.getQuantity()
            });
        }
    }

    private void showAssetDialog() {
        assetComboBox.removeAllItems();
        JTextField quantityField = new JTextField(String.valueOf(0));
        boolean addAsset = true;
        try {
            assets = (ArrayList<AssetModel>) AssetController.getAll();
            ArrayList<AssetModel> allAssets = (ArrayList<AssetModel>) AssetController.getAll();
            ArrayList<FacilityAssetModel> existingAssets = (ArrayList<FacilityAssetModel>) selectedFacility.getAssets();

            for (AssetModel assetFromAll : allAssets) {
                for (AssetModel asset : existingAssets) {
                    if (asset.getAssetId().equals(assetFromAll.getAssetId())) {
                        addAsset = false;
                        break;
                    } else {
                        addAsset = true;
                    }
                }
                if (addAsset) {
                    assetComboBox.addItem(assetFromAll.getAssetDescription());
                } 
            }
            
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Ativos:"));
        panel.add(assetComboBox);
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantityField);        
        boolean isValidInput = false;

        while (!isValidInput) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Ativo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String assetDescription = (String) assetComboBox.getSelectedItem();
                    String assetId = "";
                    for (AssetModel asset : assets) {
                        if (asset.getAssetDescription().equals(assetDescription)) {
                            assetId = asset.getAssetId();
                            break;
                        }
                    }
                    // Obter a quantidade digitada pelo usuário
                    int quantity = Integer.parseInt(quantityField.getText());
                    // Validar se o valor está dentro do intervalo permitido
                    if (quantity < 1 || quantity > 2000) {
                        JOptionPane.showMessageDialog(null, "A quantidade deve ser um número positivo entre 1 e 2000.");
                    } else {
                        FacilityAssetModel newAsset = new FacilityAssetModel(selectedFacility.getFacilityId(), assetId, quantity, assetDescription);
                        selectedFacility.addAsset(newAsset);
                        updateAssetTable();
                        isValidInput = true; // Sair do loop se a entrada for válida
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "A quantidade deve ser um número válido.");
                }
            } else {
                // Se o usuário cancelar, sair do loop
                break;
            }
        }
    }

    private void editAssetDialog(FacilityAssetModel asset) {
        JTextField descriptionField = new JTextField(asset.getAssetDescription());
        descriptionField.setEditable(false); // Para tornar o campo de descrição não editável
        JTextField quantityField = new JTextField(String.valueOf(asset.getQuantity()));
    
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Descrição:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Quantidade (1-2000):"));
        panel.add(quantityField);
    
        boolean isValidInput = false;
         while (!isValidInput) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Ativo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Obter a quantidade digitada pelo usuário
                    int quantity = Integer.parseInt(quantityField.getText());
                    // Validar se o valor está dentro do intervalo permitido
                    if (quantity < 1 || quantity > 2000) {
                        JOptionPane.showMessageDialog(null, "A quantidade deve ser um número positivo entre 1 e 2000.");
                    } else {
                        // Atualizar a quantidade do ativo
                        asset.setQuantity(quantity);
                        // Atualizar a tabela de ativos
                        updateAssetTable();
                        isValidInput = true; // Sair do loop se a entrada for válida
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "A quantidade deve ser um número válido.");
                }
            } else {
                // Se o usuário cancelar, sair do loop
                break;
            }
        }
    }
}
