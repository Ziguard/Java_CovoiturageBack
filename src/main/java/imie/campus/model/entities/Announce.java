package imie.campus.model.entities;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.entities.Assignable;
import imie.campus.core.entities.BaseEntity;
import imie.campus.model.enums.AnnounceStateEnum;
import imie.campus.model.enums.AnnounceTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Table(name = "announce")
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "ANN_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Announce extends BaseEntity<Integer> implements Assignable<Announce> {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ANN_ANNOUNCE_ID")
    protected Integer id;

    @Column(name = "ANN_CREATED_AT")
    private LocalDateTime creationDate;

    @Column(name = "ANN_DESCRIPTION")
    private String description;

    @Column(name = "ANN_STATUS")
    @Enumerated(EnumType.STRING)
    private AnnounceStateEnum status;

    @Column(name = "ANN_TITLE")
    private String title;

    @ManyToOne(cascade = {PERSIST, MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "ANN_OWNER_ID")
    private User owner;

    @Column(name = "ANN_TYPE", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected AnnounceTypeEnum announceType;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnnounceStateEnum getStatus() {
        return status;
    }

    public void setStatus(AnnounceStateEnum status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public final AnnounceTypeEnum getAnnounceType() {
        return announceType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer primaryKey() {
        return id;
    }

    @Override
    public void assign(Announce other, RequestContext context) {
        if (other.getTitle() != null) setTitle(other.getTitle());
        if (other.getDescription() != null) setDescription(other.getDescription());

        // ... (admin can modify other fields)
    }
}
