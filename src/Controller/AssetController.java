package Controller;

import java.sql.SQLException;
import java.util.List;
import Model.AssetModel;
import DAO.AssetDAO;
import Exception.AssetDataAccessException;

public class AssetController {

    public static List<AssetModel> getAll() throws SQLException, AssetDataAccessException {
        List<AssetModel> assetList = AssetDAO.getAll();
        
        if (assetList.isEmpty()) {
            // não encontrado
            return null;
        }
        return assetList;
    }
}