package Controller;

import java.sql.SQLException;
import java.util.List;
import Model.AssetModel;
import DAO.AssetDAO;

public class AssetController {
  
    public static List<AssetModel> getAll() throws SQLException {
        List<AssetModel> assetList = AssetDAO.getAll();
        
        if (assetList.isEmpty()) {
            // não encontrado
        }
        System.out.println(assetList.get(1).getAssetId());
        return assetList;
    }
}