package imie.campus.model.dto;

import imie.campus.model.enums.AnnounceTypeEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public abstract class AnnounceTO {

    private Integer id;

    private String creationDate;

    @NotNull
    @Length(max = 4096)
    private String description;

    private String status;

    @NotNull
    private String title;

    private UserTO owner;

    private AnnounceTypeEnum announceType;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public UserTO getOwner() {
        return owner;
    }

    public void setOwner(UserTO owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AnnounceTypeEnum getAnnounceType() {
        return announceType;
    }

    public void setAnnounceType(AnnounceTypeEnum announceType) {
        this.announceType = announceType;
    }
}
