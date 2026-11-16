package nl.fontys.TaskFit.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User {
    @Id
    private UUID id;
    private String name;
    private String password;

    @Column (unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    public User(String name, String password, String email, Role role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UUID getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

}
