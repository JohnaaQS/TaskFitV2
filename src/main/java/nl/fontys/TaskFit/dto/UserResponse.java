package nl.fontys.TaskFit.dto;
import nl.fontys.TaskFit.model.Role;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email, Role role) {
}
