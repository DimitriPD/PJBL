package Model;

import java.util.Date;

public class FacilityAssetModel extends AssetModel {
    private String facilityId;
    private int quantity;
    private Date createdDate;
    private Date updatedDate;

    public FacilityAssetModel(String facilityId, String assetId, int quantity, String assetDescription,
                            Date createdDate, Date updatedDate) {

        super(assetId, assetDescription);
        this.facilityId = facilityId;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
