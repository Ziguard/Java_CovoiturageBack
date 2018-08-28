package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HEQ_HOUSING_EQP_ID")
    private Integer id;

    @Column(name = "HEQ_EQUIPMENT_KEY", length = 30, nullable = false)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
