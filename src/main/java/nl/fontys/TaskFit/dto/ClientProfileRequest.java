package nl.fontys.TaskFit.dto;


public record ClientProfileRequest(
    String companyName,
    String location
) {
}