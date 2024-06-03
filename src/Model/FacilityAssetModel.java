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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void setAssetId(String assetId) {
        if (assetId == null) {
            this.assetId = UUID.randomUUID().toString();
        } else {
            this.assetId = assetId;
        }
    }
}
