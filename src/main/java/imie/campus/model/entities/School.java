package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "school")
public class School extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCH_SCHOOL_ID")
    private Integer id;

    @Column(name = "SCH_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "SCH_LOCATION_ID")
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
