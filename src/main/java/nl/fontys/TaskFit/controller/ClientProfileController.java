package nl.fontys.TaskFit.controller;

import nl.fontys.TaskFit.dto.ClientProfileRequest;
import nl.fontys.TaskFit.dto.ClientProfileResponse;
import nl.fontys.TaskFit.model.ClientProfile;
import nl.fontys.TaskFit.service.ClientProfileService;
import nl.fontys.TaskFit.service.SaveProfileResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class ClientProfileController {

    private final ClientProfileService service;

    // Constructor injection om de servicelogica te hergebruiken.
    public ClientProfileController(ClientProfileService service) {
        this.service = service;
    }

    @PutMapping("/{id}/client-profile")
    public ResponseEntity<ClientProfileResponse> saveProfile(
            @PathVariable UUID id,
            @RequestBody ClientProfileRequest request) {

        // Gebruik de URL-id zodat het profiel bij de juiste gebruiker hoort.
        ClientProfile profile = new ClientProfile(
                id,
                request.companyName(),
                request.location()
        );

        // De service controleert de gebruiker en slaat het profiel op.
        SaveProfileResult result = service.saveProfile(id, profile);

        // Zet het model om naar een response zonder interne logica mee te geven.
        ClientProfileResponse response = new ClientProfileResponse(
                result.profile().getUserId(),
                result.profile().getCompanyName(),
                result.profile().getLocation()
        );

        // Nieuw profiel krijgt 201, een bijgewerkt profiel krijgt 200.
        if (result.created()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.ok(response);
    }
}