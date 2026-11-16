package nl.fontys.TaskFit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.fontys.TaskFit.model.FreelancerProfile;



public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile, UUID> {
    
}
