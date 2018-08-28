package imie.campus.core.repositories;

import imie.campus.core.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Represents an entity repository interface.
 * @param <E> The entity type
 * @param <ID> The type of the entity's primary key
 * @author Fabien
 */
@NoRepositoryBean
public interface EntityRepository<E extends BaseEntity<ID>, ID extends Serializable>
        extends
            JpaRepository<E, ID>,
            JpaSpecificationExecutor<E> {}
