package Controller;

import java.sql.SQLException;
import java.util.List;
import Model.AssetModel;
import DAO.AssetDAO;

public class AssetController {
  
    public static void create(AssetModel assetModel) {
        AssetDAO.create(assetModel);
    }

    public static List<AssetModel> getAll() throws SQLException {
        List<AssetModel> assetList = AssetDAO.getAll();
        
        if (assetList.isEmpty()) {
            // não encontrado
            return null;
        }
        return assetList;
    }

    public static AssetModel getById(String id) throws SQLException {
        AssetModel assetModel = AssetDAO.getById(id);
        
        if (assetModel == null) {
            // não encontrado skjfh skafhk
            return null;
        }
        return assetModel;
    }

    public static void update(AssetModel assetModel) throws SQLException {
        AssetModel assetFromDB = AssetDAO.getById(assetModel.getAssetId());

        if (assetFromDB == null) {
            return;
        }
        AssetDAO.update(assetModel);
    }
}