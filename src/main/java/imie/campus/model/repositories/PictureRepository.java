package imie.campus.model.repositories;

import imie.campus.core.repositories.EntityRepository;
import imie.campus.model.entities.Picture;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureRepository extends EntityRepository<Picture,Integer> {

    @Query(value = "SELECT PIC_PICTURE_ID FROM picture " +
            "LEFT JOIN exchange_picture ON EXP_PICTURE_ID = PIC_PICTURE_ID " +
            "LEFT JOIN housing_picture ON HSP_PICTURE_ID = PIC_PICTURE_ID " +
            "LEFT JOIN article_picture ON ARP_PICTURE_ID = PIC_PICTURE_ID " +
            "WHERE EXP_PICTURE_ID IS NULL AND HSP_PICTURE_ID IS NULL AND ARP_PICTURE_ID IS NULL",
        nativeQuery = true)
    List<Integer> getUnusedPictures();
}
