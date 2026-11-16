package nl.fontys.TaskFit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.fontys.TaskFit.model.ClientProfile;

public interface ClientProfileRepository extends JpaRepository<ClientProfile, UUID> {


}