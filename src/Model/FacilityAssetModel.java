package Model;

import java.util.UUID;

public class FacilityAssetModel extends AssetModel {
    private String facilityId;
    private int quantity;

    public FacilityAssetModel(String facilityId, String assetId, int quantity, String assetDescription) {

        super(assetId, assetDescription);
        this.facilityId = facilityId;
        this.quantity = quantity;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getAssetId() {
        return this.assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }
}
