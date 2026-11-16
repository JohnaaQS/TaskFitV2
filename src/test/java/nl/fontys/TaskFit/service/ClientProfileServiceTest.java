package nl.fontys.TaskFit.service;

import nl.fontys.TaskFit.model.ClientProfile;
import nl.fontys.TaskFit.model.User;
import nl.fontys.TaskFit.model.Role;
import nl.fontys.TaskFit.exception.NotAClientException;
import nl.fontys.TaskFit.exception.UserNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientProfileServiceTest {

    private UserService userService;
    private ClientProfileService service;

    // Zorgt dat elke test met nieuwe, lege services begint.
    @BeforeEach
    void setUp() {
        userService = new UserService();
        service = new ClientProfileService(userService);
    }

    @Test
    void saveProfile_newClient_createsProfile() {
        // Arrange: registreer een client en maak een bedrijfsprofiel.
        User user = userService.register(
                new User("Johnny", "test123", "johnny@jqsdigital.nl", Role.Client)
        );

        UUID userId = user.getId();

        ClientProfile profile = new ClientProfile(
                userId, "JQS Digital", "Eindhoven"
        );

        // Act: sla het profiel op.
        SaveProfileResult result = service.saveProfile(userId, profile);

        // Assert: controleer of het profiel nieuw is aangemaakt.
        assertTrue(result.created());
        assertSame(profile, result.profile());
        assertEquals("JQS Digital", result.profile().getCompanyName());
        assertEquals("Eindhoven", result.profile().getLocation());
    }

    @Test
    void saveProfile_existingClient_updatesProfile() {
        // Arrange: registreer een client met een bestaand profiel.
        User user = userService.register(
                new User("Johnny", "test123", "johnny@jqsdigital.nl", Role.Client)
        );

        UUID userId = user.getId();

        ClientProfile firstProfile = new ClientProfile(
                userId, "JQS Digital", "Eindhoven"
        );

        service.saveProfile(userId, firstProfile);

        // Maak een aangepast profiel voor dezelfde gebruiker.
        ClientProfile updatedProfile = new ClientProfile(
                userId, "JQS Digital V2", "Amsterdam"
        );

        // Act: vervang het bestaande profiel.
        SaveProfileResult result = service.saveProfile(userId, updatedProfile);

        // Assert: controleer of het profiel bijgewerkt is.
        assertFalse(result.created());
        assertSame(updatedProfile, result.profile());
        assertEquals("JQS Digital V2", result.profile().getCompanyName());
        assertEquals("Amsterdam", result.profile().getLocation());
    }

    @Test
    void saveProfile_userNotFound_throwsUserNotFoundException() {
        // Arrange: gebruik een ID dat niet bij een gebruiker hoort.
        UUID userId = UUID.randomUUID();

        ClientProfile profile = new ClientProfile(
                userId, "JQS Digital", "Eindhoven"
        );

        // Act + Assert: onbekende gebruiker moet een exception geven.
        assertThrows(
                UserNotFoundException.class,
                () -> service.saveProfile(userId, profile)
        );
    }

    @Test
    void saveProfile_userNotClient_throwsNotAClientException() {
        // Arrange: registreer een freelancer in plaats van een client.
        User user = userService.register(
                new User("Johnny", "test123", "johnny@jqsdigital.nl", Role.Freelancer)
        );

        UUID userId = user.getId();

        ClientProfile profile = new ClientProfile(
                userId, "JQS Digital", "Eindhoven"
        );

        // Act + Assert: een freelancer mag geen clientprofiel hebben.
        assertThrows(
                NotAClientException.class,
                () -> service.saveProfile(userId, profile)
        );
    }
}