package nl.fontys.TaskFit.service;

import nl.fontys.TaskFit.model.ClientProfile;

public record SaveProfileResult(
        ClientProfile profile,
        boolean created
) {
}