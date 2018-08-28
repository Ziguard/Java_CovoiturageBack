package imie.campus.controllers;

import imie.campus.core.controllers.AbstractController;
import imie.campus.dao.PictureService;
import imie.campus.errors.exceptions._500.FailedUploadException;
import imie.campus.errors.exceptions._501.NotImplementedException;
import imie.campus.model.dto.PictureTO;
import imie.campus.model.entities.Picture;
import imie.campus.model.mappers.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import java.io.IOException;

import static imie.campus.controllers.PictureController.RESOURCE_PATH;

@RestController
@RequestMapping(RESOURCE_PATH)
public class PictureController extends AbstractController<Picture, PictureTO, Integer> {

    static final String RESOURCE_PATH = "/pictures";

    @Autowired
    public PictureController(PictureService service, PictureMapper mapper) {
        super(service, mapper);
    }

    @PostMapping("/upload")
    @ResponseBody
    public PictureTO uploadPicture(
            @RequestParam("file") MultipartFile file)
    {
        try {
            return mapper.map(getService().upload(file));
        } catch (IOException ex) {
            throw new FailedUploadException(file.getOriginalFilename(), ex);
        }
    }

    @RequestMapping("/{id:[\\d]+}/download")
    public ResponseEntity<byte[]> download(
            @PathVariable Integer id) throws IOException
    {
        Picture picture = service.find(id);
        return getService().download(picture, false);
    }

    public PictureService getService() {
        return (PictureService) service;
    }

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }

    /*
     * Standard resource controller operations are forbidden for pictures
     */
    @Override public PictureTO get(Integer i) { throw new NotImplementedException(); }

    @Override public ResponseEntity create(PictureTO t, UriComponentsBuilder b, ServletRequest r) {
        throw new NotImplementedException();
    }

    @Override public PictureTO update(PictureTO t, Integer i, ServletRequest r) { throw new NotImplementedException(); }
}
