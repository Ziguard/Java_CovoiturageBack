package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;
import imie.campus.model.enums.MessageStateEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MES_MESSAGE_ID")
    private Integer id;

    @Column(name = "MES_SENT_AT")
    private LocalDateTime sentAt;

    @Column(name = "MES_STATE")
    @Enumerated(EnumType.STRING)
    private MessageStateEnum state;

    @ManyToOne
    @JoinColumn(name = "MES_SENDER_ID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "MES_RECEIVER_ID")
    private User receiver;

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public MessageStateEnum getState() {
        return state;
    }

    public void setState(MessageStateEnum state) {
        this.state = state;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User recever) {
        this.receiver = recever;
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
