package imie.campus.model.entities;

import imie.campus.model.enums.AnnounceTypeEnum;
import imie.campus.model.enums.ExchangeTypeEnum;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name="exchange_a")
@PrimaryKeyJoinColumn(name="EXG_EXCHANGE_ID")
@DiscriminatorValue("EXCHANGE")
public class Exchange extends Announce {
    @ManyToOne
    @JoinColumn(name = "EXG_EXCHANGE_CAT_ID")
    private ExchangeCategory category;

    @Column(name = "EXG_EXCHANGE_TYPE")
    @Enumerated(EnumType.STRING)
    private ExchangeTypeEnum exchangeType;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "exchange_picture",
            joinColumns = {
                    @JoinColumn(name = "EXP_EXCHANGE_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "EXP_PICTURE_ID", nullable = false, updatable = false)
            }
    )
    private Set<Picture> pictures;

    @OneToMany(mappedBy = "exchange")
    private Set<Package> packages;

    public Exchange() {
        this.announceType = AnnounceTypeEnum.EXCHANGE;
    }

    public ExchangeCategory getCategory() {
        return category;
    }

    public void setCategory(ExchangeCategory category) {
        this.category = category;
    }

    public ExchangeTypeEnum getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeTypeEnum exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }
}
