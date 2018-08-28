package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announce_h")
public class AnnounceH extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANH_ANNOUNCE_H_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ANH_ORIGINAL_ID")
    private Announce original;

    @Column(name = "ANH_CREATED_AT")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "ANH_MODIFIER_ID")
    private User modifier;

    @Column(name = "ANH_DESCRIPTION")
    private String description;

    @Column(name = "ANH_STATUS")
    private String status;

    @Column(name = "ANH_TITLE")
    private String title;

    @Column(name = "ANH_REVISION")
    private Integer revision;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Announce getOriginal() {
        return original;
    }

    public void setOriginal(Announce original) {
        this.original = original;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
