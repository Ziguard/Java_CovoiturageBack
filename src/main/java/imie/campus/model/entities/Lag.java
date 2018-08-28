package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;
import imie.campus.model.enums.LagCauseEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lag")
public class Lag extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LAG_LAG_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "LAG_CARPOOLING_ID")
    private Carpooling carpooling;

    @Column(name = "LAG_CAUSE", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private LagCauseEnum cause;

    @Column(name = "LAG_HAPPEN_DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "LAG_BROADCAST_MESSAGE", length = 65536)
    private String broadcastMessage;

    public LagCauseEnum getCause() {
        return cause;
    }

    public void setCause(LagCauseEnum cause) {
        this.cause = cause;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public Carpooling getCarpooling() {
        return carpooling;
    }

    public void setCarpooling(Carpooling carpooling) {
        this.carpooling = carpooling;
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
