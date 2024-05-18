package Controller;

import java.sql.SQLException;
import java.util.*;
import DAO.FacilityDAO;
import Model.FacilityModel;
import Model.FacilityTypeModel;
import Model.FacilityAssetModel;


public class FacilityController {
    
    public static List<FacilityModel> getAll() throws SQLException {
        
        List<FacilityModel> facilities = FacilityDAO.getAll();
        
        if (facilities.isEmpty()) {
            //vazio
        }
        
        for (FacilityModel facility : facilities) {
            List<FacilityAssetModel> facilityAssets = FacilityDAO.getAllFacilityAssets(facility.getFacilityId());
            
            if (facilityAssets != null) {
                facility.setAssets(facilityAssets);
            }
        }
        return facilities;
    }
    
    public static FacilityModel getById(String id, boolean getAssets) throws SQLException {
        
        FacilityModel facility = FacilityDAO.getById(id);
        if (facility == null) {
            System.out.println('a');
            return null;     
        }   
        System.out.println(facility.getAssets());
        if (getAssets) {
            List<FacilityAssetModel> facilityAssets = FacilityDAO.getAllFacilityAssets(id);
            
            if (facilityAssets != null) {
                facility.setAssets(facilityAssets);
            }
        }
        return facility;
    }
    
    public static String create(FacilityModel facility) {
    
        List<FacilityAssetModel> assets = facility.getAssets();

        FacilityDAO.create(facility);
        
        if (assets != null && !assets.isEmpty()) {
            for (FacilityAssetModel asset : assets) {
                FacilityDAO.createFacilityAsset(asset);
            }
        }
        
        return facility.getFacilityId();
    }

    public static void update(FacilityModel facilityUpdated) throws SQLException {

        FacilityModel facility = getById(facilityUpdated.getFacilityId(), true);

        if (facility == null) {
            return;
        }

        FacilityDAO.update(facilityUpdated);
        
        List<FacilityAssetModel> updatedAssets = facilityUpdated.getAssets();
        List<FacilityAssetModel> existingAssets = facility.getAssets();
        
        for (FacilityAssetModel asset : updatedAssets) {
            FacilityAssetModel existingAsset = existingAssets.stream()
                    .filter(assets -> assets.getAssetId().equals(asset.getAssetId()))
                    .findFirst()
                    .orElse(null);
            
            if (existingAsset != null) {
                FacilityDAO.updateFacilityAsset(existingAsset);
            } else {
                FacilityDAO.createFacilityAsset(asset);
            }
        }
        
        for (FacilityAssetModel originalAsset : existingAssets) {
            if (!updatedAssets.stream().anyMatch(a -> a.getAssetId().equals(originalAsset.getAssetId()))) {
                FacilityDAO.deleteFacilityAsset(originalAsset.getFacilityId(), originalAsset.getAssetId());
            }
        }
    }

    public static void updateStatus(String id, boolean status) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    public static List<FacilityTypeModel> getAllTypes() throws SQLException {
        List<FacilityTypeModel> facilityTypes = FacilityDAO.getAllTypes();
        
        if (facilityTypes.isEmpty()) {
            //nao tem
        }
        return facilityTypes;
    }
}
