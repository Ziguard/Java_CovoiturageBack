package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exchange_package")
public class Package extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXP_EXCHANGE_PACK_ID")
    private Integer id;

    @Column(name = "EXP_PRICE")
    private Float price;

    @OneToMany(mappedBy = "pack")
    private Set<Article> articles;

    @ManyToOne
    @JoinColumn(name = "EXP_ANNOUNCE_ID")
    private Exchange exchange;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
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
