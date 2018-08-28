package imie.campus.model.entities;

import imie.campus.model.enums.AnnounceTypeEnum;
import imie.campus.model.enums.HousingTypeEnum;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "housing_a")
@PrimaryKeyJoinColumn(name="HSA_HOUSING_ID")
@DiscriminatorValue("HOUSING")
public class Housing extends Announce {

    @Column(name = "HSA_HOUSING_TYPE")
    @Enumerated(EnumType.STRING)
    private HousingTypeEnum housingType;

    @Column(name = "HSA_AREA")
    private Float area;

    @Column(name = "HSA_RENT")
    private Integer rent;

    @Column(name = "HSA_ROOMS_NB")
    private Integer roomsCount;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "housing_eqpt",
            joinColumns = {
                    @JoinColumn(name = "HSE_HOUSING_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "HSE_HOUSING_EQP_ID", nullable = false, updatable = false)
            }
    )
    private Set<Equipment> equipments;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "housing_picture",
            joinColumns = {
                    @JoinColumn(name = "HSP_HOUSING_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "HSP_PICTURE_ID", nullable = false, updatable = false)
            }
    )
    private Set<Picture> pictures;

    public Housing() {
        this.announceType = AnnounceTypeEnum.HOUSING;
    }

    public HousingTypeEnum getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingTypeEnum housingType) {
        this.housingType = housingType;
    }

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

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public Integer getId() {
        return id;
    }

}
