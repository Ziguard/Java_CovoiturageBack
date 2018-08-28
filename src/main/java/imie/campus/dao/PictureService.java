package imie.campus.dao;

import imie.campus.core.services.AbstractEntityService;
import imie.campus.errors.exceptions._400.IncorrectFileException;
import imie.campus.model.entities.Picture;
import imie.campus.model.listeners.PictureListener;
import imie.campus.model.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static imie.campus.utils.commons.GeneralUtils.format;
import static java.awt.RenderingHints.*;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.util.Arrays.asList;

@Service
public class PictureService extends AbstractEntityService<Picture, Integer> {

    private static final List<String> AUTHORIZED_EXT = asList(
        ".jpg", ".jpeg", ".png", ".gif"
    );

    private static final List<String> AUTHORIZED_MIME_TYPES = asList(
        "image/jpeg", "image/png", "image/gif"
    );

    private static final float RATIO = 0.5f;

    @Value("${campus.upload.folder}")
    private String uploadFolder;

    @Autowired
    public PictureService(PictureRepository repository,
                          PictureListener listener) {
        super(repository, listener);
    }

    public Picture upload(MultipartFile file) throws IOException {
        String extension = getFileExtension(file.getOriginalFilename());

        if (!AUTHORIZED_EXT.contains(extension))
            throw new IncorrectFileException("Uploaded files must have following extension : " + AUTHORIZED_EXT);

        if (!AUTHORIZED_MIME_TYPES.contains(file.getContentType()))
            throw new IncorrectFileException("Uploaded files msut have following mime types : " + AUTHORIZED_MIME_TYPES);

        String storedName = randomName(extension);
        Picture picture = new Picture();
        picture.setOriginalName(file.getOriginalFilename());
        picture.setStoragePath(storedName);
        picture.setMimeType(file.getContentType());

        createUploadDirIfNotExists();
        Path storedFile = Paths.get(uploadFolder, storedName);

        Files.write(storedFile, resize(file.getBytes(), extension, RATIO), CREATE_NEW);
        return this.create(picture);
    }

    private byte[] resize(final byte[] image, String extension, float ratio) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(bais);

        int width = Math.round(originalImage.getWidth() * ratio);
        int height = Math.round(originalImage.getHeight() * ratio);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

        String format = extension.substring(1).toUpperCase();
        if (format.equals("JPEG")) format = "JPG";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, format, baos);

        return baos.toByteArray();
    }

    private String randomName(String extension) {
        return UUID.randomUUID().toString()
                .replaceAll("-", "") + extension;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    private void createUploadDirIfNotExists() throws IOException {
        Path uploadDir = Paths.get(uploadFolder);
        if (!Files.exists(uploadDir))
            Files.createDirectory(uploadDir);
    }

    public List<Integer> getUnusedPictures() {
        return ((PictureRepository) repository).getUnusedPictures();
    }

    public long cleanUploadFolder() {
        List<String> storage = findAll().stream()
                .map(Picture::getStoragePath)
                .collect(Collectors.toList());

        try (Stream<Path> stream = Files.list(Paths.get(uploadFolder))) {
            return stream
                .filter(path -> !storage.contains(path.getFileName().toString()))
                .filter(this::deleteFile)
                .count();
        } catch (IOException e) {
            return 0L;
        }
    }

    public boolean deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public ResponseEntity<byte[]> download(Picture picture, boolean fileDownload) throws IOException {
        Path imagePath = Paths.get(uploadFolder, picture.getStoragePath());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", picture.getMimeType());

        if (fileDownload) {
            headers.set("Content-Disposition",
                    format("attachment; filename=\"{}\"", picture.getOriginalName()));
        }

        FileInputStream in = new FileInputStream(imagePath.toString());
        return new ResponseEntity<>(transfertStream(in).toByteArray(), headers, HttpStatus.OK);
    }

    private ByteArrayOutputStream transfertStream(InputStream in)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int l = in.read(buffer);
        while(l >= 0) {
            out.write(buffer, 0, l);
            l = in.read(buffer);
        }
        return out;
    }
}
