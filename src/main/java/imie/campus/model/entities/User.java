package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;
import imie.campus.security.model.RoleTemplate;
import imie.campus.security.model.UserTemplate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "user", uniqueConstraints =
    @UniqueConstraint(columnNames = {"USR_USERNAME", "USR_EMAIL"}))

public class User extends BaseEntity<Integer> implements UserTemplate<String>, Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_USER_ID")
    private Integer id;

    private static long serialVersionUID = 1L;

    /**
     * Username
     */
    @Column(name = "USR_USERNAME", length = 40, nullable = false, unique = true)
    private String username;

    /**
     * The user password hash
     */
    @Column(name = "USR_PASSWORD", length = 40, nullable = false)
    private String password;

    /**
     * Salt associated with the hashed pass
     */
    @Column(name="USR_SALT", nullable = false)
    private String salt;

    /**
     * Last name
     */
    @Column(name = "USR_LAST_NAME", length = 40, nullable = false)
    private String lastName;

    /**
     * First name
     */
    @Column(name = "USR_FIRST_NAME", length = 40, nullable = false)
    private String firstName;

    /**
     * Mail address
     */
    @Column(name = "USR_EMAIL", length = 40, nullable = false, unique = true)
    private String email;

    /**
     * Phone number
     */
    @Column(name = "USR_PHONE", length = 40, nullable = false)
    private String phone;

    /**
     * Attributed roles
     */
    @ManyToMany(cascade = ALL, fetch = EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "USR_USER_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "USR_ROLE_ID", nullable = false, updatable = false)
            }
    )
    private Set<Role> roles;

    /**
     * The events owned by the user
     */
    @OneToMany(mappedBy = "owner")
    private Set<Event> events;

    /**
     * The user creation date
     */
    @Column(name = "USR_CREATION_DATETIME")
    private LocalDateTime creationDate;

    /**
     * Indicate whether user is active
     */
    @Column(name = "USR_ACTIVE")
    private boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Set<? extends RoleTemplate<?>> getRoles() {
        return roles;
    }

    @Override
    public Optional<String> getSalt() {
        return Optional.ofNullable(salt);
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
