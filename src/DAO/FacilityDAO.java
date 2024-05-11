package DAO;

import java.util.List;
import Model.FacilityAssetModel;
import Model.FacilityModel;
import Model.FacilityTypeModel;

public class FacilityDAO {

    public static void create(FacilityModel facility) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public static List<FacilityModel> getAll(String buildingId, String facilityTypeId) {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    public static FacilityModel getById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    public static List<FacilityAssetModel> getAllFacilityAssets(String facilityId) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllFacilityAssets'");
    }

    public static void createFacilityAsset(FacilityAssetModel asset) {
        throw new UnsupportedOperationException("Unimplemented method 'createFacilityAsset'");
    }

    public static void updateFacilityAsset(FacilityAssetModel asset) {
        throw new UnsupportedOperationException("Unimplemented method 'updateFacilityAsset'");
    }

    public static void deleteFacilityAsset(String facilityId, String assetId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteFacilityAsset'");
    }

    public static List<FacilityTypeModel> getAllTypes() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllTypes'");
    }
}