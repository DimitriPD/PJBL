import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Model.FacilityAssetModel;
import Model.FacilityModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssetsScreen extends JFrame {

    private FacilityModel selectedFacility;
    private JTable assetTable;
    private DefaultTableModel assetTableModel;

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
                }            
                else if (assetTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Não há ativos para editar.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selecione um ativo para editar.");
                }
            }
        });

        deleteAssetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = assetTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Obter o ativo selecionado
                    FacilityAssetModel selectedAsset = selectedFacility.getAssets().get(selectedRow);
                    // Abrir diálogo de edição de ativo
                    deleteAssetDialog(selectedAsset);
                } 
                else if (assetTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Não há ativos para excluir.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selecione um ativo para excluir.");
                }
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

    private void deleteAssetDialog(FacilityAssetModel asset) {

        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este ativo do espaço?", "Editar Ativo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (FacilityAssetModel facilityAsset : selectedFacility.getAssets()) {
                if (asset.getAssetId() == facilityAsset.getAssetId()) {
                    selectedFacility.getAssets().remove(facilityAsset);
                    break;
                }
            }
            updateAssetTable();
        }
    }
}
