package nl.fontys.TaskFit.dto;
import java.util.UUID;

public record ClientProfileResponse(
    UUID userId,
    String companyName,
    String location
) {
}
