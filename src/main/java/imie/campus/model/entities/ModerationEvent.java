package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;
import imie.campus.model.enums.ModEventTypeEnum;
import imie.campus.model.enums.ModRejectionCauseEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_event")
public class ModerationEvent extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEV_MOD_EVENT_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MEV_MODERATOR_ID")
    private User moderator;

    @Column(name = "MEV_EVENT_DATE", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "MEV_COMMENT", length = 65536)
    private String comment;

    @Column(name = "MEV_EVENT_TYPE")
    @Enumerated(EnumType.STRING)
    private ModEventTypeEnum type;

    @Column(name = "MEV_REJECTION_CAUSE")
    @Enumerated(EnumType.STRING)
    private ModRejectionCauseEnum rejectionCause;

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ModEventTypeEnum getType() {
        return type;
    }

    public void setType(ModEventTypeEnum type) {
        this.type = type;
    }

    public ModRejectionCauseEnum getRejectionCause() {
        return rejectionCause;
    }

    public void setRejectionCause(ModRejectionCauseEnum rejectionCause) {
        this.rejectionCause = rejectionCause;
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
