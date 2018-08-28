package imie.campus.model.entities;

import imie.campus.model.enums.AnnounceTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "carpooling_a")
@PrimaryKeyJoinColumn(name="CAR_CARPOOLING_ID")
@DiscriminatorValue("CARPOOLING")
public class Carpooling extends Announce {

    @ManyToOne
    @JoinColumn(name = "CAR_USER_ID")
    private User driver;

    @Column(name = "CAR_CREATED_AT")
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "carpooling", cascade = ALL)
    private Set<Lag> lags;

    @OneToMany(mappedBy = "carpooling", cascade = ALL)
    private Set<Step> steps;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "passenger",
            joinColumns = {
                    @JoinColumn(name = "PAS_CARPOOLING_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PAS_USER_ID", nullable = false, updatable = false)
            }
    )
    private Set<User> passengers;

    public Carpooling() {
        this.announceType = AnnounceTypeEnum.CARPOOLING;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Lag> getLags() {
        return lags;
    }

    public void setLags(Set<Lag> lags) {
        this.lags = lags;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    /**
     * Renvoie les étapes du trajet dans l'ordre
     * @return Renvoi une liste ordonnée contenant les étapes,
     *   triées par ordre croissant de leur numéro d'ordre (order)
     */
    public List<Step> getSortedSteps() {
        List<Step> steps = new ArrayList<>(getSteps());
        steps.sort(Comparator.comparingInt(Step::getOrder));
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public Set<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<User> passengers) {
        this.passengers = passengers;
    }

}
