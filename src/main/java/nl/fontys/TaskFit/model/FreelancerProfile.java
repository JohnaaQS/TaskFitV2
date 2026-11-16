package nl.fontys.TaskFit.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table (name = "freelancer_profiles")
public class FreelancerProfile {
    @Id
    private UUID userId;
    private String postcode;

    @Column (length = 1000)
    private String motivationText;

    protected FreelancerProfile() {
    }

    public FreelancerProfile(UUID userId, String postcode, String motivationText) {
        this.userId = userId;
        this.postcode = postcode;
        this.motivationText = motivationText;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getMotivationText() {
        return motivationText;
    }
}

