package imie.campus.core.controllers;

import imie.campus.core.contexts.AuthRequestContext;
import imie.campus.core.contexts.ContextProvider;
import imie.campus.core.entities.BaseEntity;
import imie.campus.core.mappers.EntityMapper;
import imie.campus.core.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

import static imie.campus.utils.rest.ControllerUtils.entityLocation;

/**
 * An abstract class describing the impl behaviour for a resource controller.
 * @author Fabien
 * @param <E> The type of the resource entity published by the controller
 * @param <TO> The type of the associated transfer object exposed to the user
 * @param <ID> The type of the published entity primary key identifier
 */
public abstract class AbstractController<E extends BaseEntity<ID>, TO, ID extends Serializable> {

    /**
     * The resource entity data-centric services
     */
    protected final EntityService<E, ID> service;

    /**
     * The resource entity mapper used to convert JPA persistent object to associated transfer object
     */
    protected EntityMapper<E, TO> mapper;

    /**
     * Returns the resource URI location
     * @return A URI prefix for accessing the resource entity
     */
    public abstract String getResourcePath();

    @Autowired
    private ContextProvider<AuthRequestContext> contextProvider;

    /**
     * Standard constructor for controller
     * @param service The entity services to use
     * @param mapper The entity mapper to use
     */
    protected AbstractController(EntityService<E, ID> service,
                                 EntityMapper<E, TO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TO> getAll() {
        return mapper.map(service.findAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public TO get(@PathVariable ID id) {
        return mapper.map(service.find(id));
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody TO transfer,
                                 UriComponentsBuilder builder,
                                 ServletRequest request) {
        E toCreate = mapper.reverse(transfer);
        E created = service.create(toCreate, contextProvider.provide(request));

        return entityLocation(builder, getResourcePath(), created);
    }

    @PutMapping("/{id:[\\d]+}")
    @ResponseBody
    public TO update(@Valid @RequestBody TO transfer,
                     @PathVariable ID id,
                     ServletRequest request) {
        E toUpdate = mapper.reverse(transfer);
        E updated =  service.update(id, toUpdate, contextProvider.provide(request));

        return mapper.map(updated);
    }
}
