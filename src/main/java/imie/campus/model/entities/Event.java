package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVT_EVENT_ID")
    private Integer id;

    @Column(name = "EVT_TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "EVT_OWNER_ID")
    private User owner;

    @Column(name = "EVT_START_DATE")
    private LocalDateTime startDate;

    @Column(name = "EVT_END_DATE")
    private LocalDateTime endDate;

    @Column(name = "EVT_FREQUENCY", length = 15)
    private String frequency;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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
