package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name="exchange_article")
public class Article extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXA_EXCHANGE_ART_ID")
    private Integer id;

    @Column(name = "EXA_NAME")
    private String name;

    @Column(name = "EXA_UNIT_PRICE")
    private Float price;

    @ManyToOne
    @JoinColumn(name = "EXA_EXCHANGE_PACKAGE_ID")
    private Package pack;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "article_picture",
            joinColumns = {
                    @JoinColumn(name = "ARP_EXCHANGE_ART_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ARP_PICTURE_ID", nullable = false, updatable = false)
            }
    )
    private Set<Picture> pictures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
