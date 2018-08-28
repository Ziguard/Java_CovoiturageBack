package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.model.dto.PictureTO;
import imie.campus.model.entities.Picture;
import org.springframework.stereotype.Service;

@Service
public class PictureMapper extends AbstractEntityMapper<Picture, PictureTO> {

    public PictureMapper() {
        super(Picture.class, PictureTO.class);
    }

    @Override
    public void configure() {}
}
