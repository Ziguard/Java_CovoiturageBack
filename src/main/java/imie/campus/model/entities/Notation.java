package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notation")
public class Notation extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOT_NOTATION_ID")
    private Integer id;

    @Column(name = "NOT_COMMENT", length = 65536)
    private String comment;

    @Column(name = "NOT_NOTE_VALUE")
    private Float noteValue;

    @Column(name = "NOT_LEFT_AT")
    private LocalDateTime leftAt;

    @ManyToOne
    @JoinColumn(name = "NOT_RANKER_ID")
    private User ranker;

    @ManyToOne
    @JoinColumn(name = "NOT_RANKED_ID")
    private User ranked;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(Float noteValue) {
        this.noteValue = noteValue;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public User getRanker() {
        return ranker;
    }

    public void setRanker(User ranker) {
        this.ranker = ranker;
    }

    public User getRanked() {
        return ranked;
    }

    public void setRanked(User ranked) {
        this.ranked = ranked;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
