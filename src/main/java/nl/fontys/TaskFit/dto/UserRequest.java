package nl.fontys.TaskFit.dto;
import nl.fontys.TaskFit.model.Role;

public record UserRequest(
    String name,
    String password,
    String email,
    Role role
) {
}
