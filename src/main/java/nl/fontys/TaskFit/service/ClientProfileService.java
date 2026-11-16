package nl.fontys.TaskFit.service;

import nl.fontys.TaskFit.model.ClientProfile;
import nl.fontys.TaskFit.model.User;
import nl.fontys.TaskFit.model.Role;
import nl.fontys.TaskFit.exception.NotAClientException;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@Service
public class ClientProfileService {

    private final UserService userService;

    private final Map<UUID, ClientProfile> profiles = new HashMap<>();

    public ClientProfileService(UserService userService) {
        this.userService = userService;
    }

    public SaveProfileResult saveProfile(UUID userId, ClientProfile profile) {

        //Zoek de gebruiker op.
        User user = userService.findById(userId);

        // Alleen CLIENT-gebruikers mogen een profiel hebben (anders 403).
        if (user.getRole() != Role.Client) {
            throw new NotAClientException("User is not a client");
        }

        //Controleer of de userId overeenkomt.
        if (!userId.equals(profile.getUserId())) {
            throw new IllegalArgumentException("Profile userId does not match");
        }

        // put() maakt een profiel aan of vervangt het bestaande profiel.
        ClientProfile previous = profiles.put(userId, profile);

        // Geen vorig profiel betekent nieuw aangemaakt (201 i.p.v. 200).
        boolean created = previous == null;

        // Geef het profiel en de aanmaakstatus terug aan de controller.
        return new SaveProfileResult(profile, created);
    }
}