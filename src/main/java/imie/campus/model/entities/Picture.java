package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "picture")
public class Picture extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIC_PICTURE_ID")
    private Integer id;

    @Column(name = "PIC_STORAGE_PATH", nullable = false)
    private String storagePath;

    @Column(name = "PIC_ORIGINAL_NAME", nullable = false)
    private String originalName;

    @Column(name = "PIC_MIME_TYPE", nullable = false)
    private String mimeType;

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
