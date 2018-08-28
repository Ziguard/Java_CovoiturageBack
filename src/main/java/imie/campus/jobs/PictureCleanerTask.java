package imie.campus.jobs;

import imie.campus.dao.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static imie.campus.utils.commons.GeneralUtils.booleanValue;

@Component
public class PictureCleanerTask {

    @Value("${campus.upload.clean-at-startup}")
    private Boolean cleanAtStartup;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PictureService pictureService;

    @Autowired
    public PictureCleanerTask(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostConstruct
    public void initialize() {
        if (booleanValue(cleanAtStartup))
            cleanUp();
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void cleanUp() {
        logger.info("Cleaning up unused pictures from database and upload folder...");

        List<Integer> unusedPictures = pictureService.getUnusedPictures();
        unusedPictures.forEach(pictureService::delete);
        long deleted = pictureService.cleanUploadFolder();

        logger.info("Terminated clean up : {} row(s) dropped from database, {} file(s) deleted from upload folder.",
                unusedPictures.size(), deleted);
    }
}
