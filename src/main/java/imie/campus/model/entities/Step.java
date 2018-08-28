package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "step")
public class Step extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STP_STEP_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "STP_CARPOOLING_ID")
    private Carpooling carpooling;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "STP_LOCATION_ID")
    private Location location;

    @Column(name = "STP_ORDER")
    private Integer order;

    @Column(name = "STP_ESTIMATED_DEPARTURE_TIME")
    private LocalDateTime estimatedDeparture;

    @Column(name = "STP_EFFECTIVE_DEPARTURE_TIME")
    private LocalDateTime effectiveDeparture;

    @Column(name = "STP_ESTIMATED_ARRIVING_TIME")
    private LocalDateTime estimatedArriving;

    @Column(name = "STP_EFFECTIVE_ARRIVING_TIME")
    private LocalDateTime effectiveArriving;

    public Carpooling getCarpooling() {
        return carpooling;
    }

    public void setCarpooling(Carpooling carpooling) {
        this.carpooling = carpooling;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LocalDateTime getEstimatedDeparture() {
        return estimatedDeparture;
    }

    public void setEstimatedDeparture(LocalDateTime estimatedDeparture) {
        this.estimatedDeparture = estimatedDeparture;
    }

    public LocalDateTime getEffectiveDeparture() {
        return effectiveDeparture;
    }

    public void setEffectiveDeparture(LocalDateTime effectiveDeparture) {
        this.effectiveDeparture = effectiveDeparture;
    }

    public LocalDateTime getEstimatedArriving() {
        return estimatedArriving;
    }

    public void setEstimatedArriving(LocalDateTime estimatedArriving) {
        this.estimatedArriving = estimatedArriving;
    }

    public LocalDateTime getEffectiveArriving() {
        return effectiveArriving;
    }

    public void setEffectiveArriving(LocalDateTime effectiveArriving) {
        this.effectiveArriving = effectiveArriving;
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
