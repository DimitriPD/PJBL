package Model;

public class FacilityTypeModel {

    private String facilityTypeId;
    private String facilityTypeDescription;

    public FacilityTypeModel(String _facilityTypeId, String _facilityTypeDescription) {
        this.facilityTypeId = _facilityTypeId;
        this.facilityTypeDescription = _facilityTypeDescription;
    }

    public String getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(String _facilityTypeId) {
        this.facilityTypeId = _facilityTypeId;
    }

    public String getFacilityTypeDescription() {
        return facilityTypeDescription;
    }

    public void setFacilityTypeDescription(String _facilityTypeDescription) {
        this.facilityTypeDescription = _facilityTypeDescription;
    }
}
