package nl.fontys.TaskFit.model;

import java.util.UUID;

//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_profiles")
public class ClientProfile {
    @Id
    private UUID userId;
    private String companyName;
    private String location;

    protected ClientProfile() {
    }

    public ClientProfile(UUID userId, String companyName, String location) {
        this.userId = userId;
        this.companyName = companyName;
        this.location = location;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }
}