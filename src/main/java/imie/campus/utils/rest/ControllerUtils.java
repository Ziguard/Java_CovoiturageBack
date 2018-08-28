package imie.campus.utils.rest;

import imie.campus.core.entities.BaseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;

public class ControllerUtils {

    public static ResponseEntity<String> entityLocation(final UriComponentsBuilder builder,
                                                        String controllerPath,
                                                        BaseEntity<? extends Serializable> entity) {
        HttpHeaders headers = new HttpHeaders();
        URI location = builder
                .path(buildRessourcePath(controllerPath))
                .buildAndExpand(entity.primaryKey())
                .toUri();
        headers.setLocation(location);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private static String buildRessourcePath(String controllerPath) {
        return controllerPath +
                (!controllerPath.endsWith("/") ? "/" : "") + "{id}";
    }
}
