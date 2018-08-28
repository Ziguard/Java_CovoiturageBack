package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;
import imie.campus.security.model.RoleTemplate;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "role")
public class Role extends BaseEntity<Integer> implements RoleTemplate<Object>, Serializable {

    private static long serialVersionUID = 1L;

    /**
     * L'identifiant technique du rôle.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ROL_ROLE_ID", length = 11, nullable = false, unique = true)
    private Integer id;

    /**
     * La clé (identifiant fonctionnel) du rôle
     */
    @Column(name = "ROL_KEY", length = 20, nullable = false, unique = true)
    private String key;

    /**
     * Le rôle parent du rôle courant
     */
    @ManyToOne
    @JoinColumn(name = "ROL_PARENT_ID")
    private Role parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
