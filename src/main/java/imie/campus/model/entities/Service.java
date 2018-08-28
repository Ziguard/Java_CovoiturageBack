package imie.campus.model.entities;

import imie.campus.model.enums.AnnounceTypeEnum;

import javax.persistence.*;

@Entity
@Table(name ="service_a")
@PrimaryKeyJoinColumn(name="SVA_SERVICE_ID")
@DiscriminatorValue("SERVICE")
public class Service extends Announce {

    @ManyToOne
    @JoinColumn(name = "SVA_SERVICE_CAT_ID")
    private ServiceCategory category;

    @Column(name= "SVA_PRICE")
    private Float price;

    public Service() {
        this.announceType = AnnounceTypeEnum.SERVICE;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
