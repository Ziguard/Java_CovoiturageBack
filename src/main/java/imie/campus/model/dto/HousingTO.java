package imie.campus.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HousingTO extends AnnounceTO {

    @NotNull @Length(min = 1)
    private String housingType;

    @NotNull
    @DecimalMin("1.0")
    private Float area;

    private Integer rent;

    @Min(1)
    private Integer roomsCount;

    private Set<EquipmentTO> equipments;

    private Set<PictureTO> pictures;

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public Set<EquipmentTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<EquipmentTO> equipments) {
        this.equipments = equipments;
    }

    public Set<PictureTO> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureTO> pictures) {
        this.pictures = pictures;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }
}