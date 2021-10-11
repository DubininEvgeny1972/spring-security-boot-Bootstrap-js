package web.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
    @Override
    public String toString() {
        return name;
    }
}
