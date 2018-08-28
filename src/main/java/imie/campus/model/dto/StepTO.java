package imie.campus.model.dto;

import javax.validation.constraints.Pattern;

import static imie.campus.utils.commons.ValidationPatterns.ISO_DATETIME;

public class StepTO {

    private Integer carpoolingId;

    private LocationTO location;

    private Integer order;

    @Pattern(regexp = ISO_DATETIME, message = "Invalid date.")
    private String estimatedDeparture;

    @Pattern(regexp = ISO_DATETIME, message = "Invalid date.")
    private String effectiveDeparture;

    @Pattern(regexp = ISO_DATETIME, message = "Invalid date.")
    private String estimatedArriving;

    @Pattern(regexp = ISO_DATETIME, message = "Invalid date.")
    private String effectiveArriving;

    public Integer getCarpoolingId() {
        return carpoolingId;
    }

    public void setCarpoolingId(Integer carpoolingId) {
        this.carpoolingId = carpoolingId;
    }

    public LocationTO getLocation() {
        return location;
    }

    public void setLocation(LocationTO location) {
        this.location = location;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getEstimatedDeparture() {
        return estimatedDeparture;
    }

    public void setEstimatedDeparture(String estimatedDeparture) {
        this.estimatedDeparture = estimatedDeparture;
    }

    public String getEffectiveDeparture() {
        return effectiveDeparture;
    }

    public void setEffectiveDeparture(String effectiveDeparture) {
        this.effectiveDeparture = effectiveDeparture;
    }

    public String getEstimatedArriving() {
        return estimatedArriving;
    }

    public void setEstimatedArriving(String estimatedArriving) {
        this.estimatedArriving = estimatedArriving;
    }

    public String getEffectiveArriving() {
        return effectiveArriving;
    }

    public void setEffectiveArriving(String effectiveArriving) {
        this.effectiveArriving = effectiveArriving;
    }
}
