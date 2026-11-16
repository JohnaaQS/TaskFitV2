package nl.fontys.TaskFit.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import nl.fontys.TaskFit.exception.EmailAlreadyExistsException;
import nl.fontys.TaskFit.model.User;
import nl.fontys.TaskFit.model.Role;

class UserServiceTest {

    @Test
    void register_validData_returnsUser() {
        // Klaarzetten: service, user met naam/e-mail/rol
        UserService service = new UserService();
        User user = new User("Jan", "password", "jan@test.nl", Role.Freelancer);
        // Uitvoeren: register aanroepen
        User result = service.register(user);
        // Controleren: id is niet null, naam/e-mail/rol kloppen
        assertNotNull(result.getId());
        assertEquals("Jan", result.getName());
        assertEquals("jan@test.nl", result.getEmail());
        assertEquals(Role.Freelancer, result.getRole());
    }

    @Test
    void register_emailAlreadyExists_throwsEmailAlreadyExistsException() {
        // Klaarzetten: service, eerste user met jan@test.nl registreren
        UserService service= new UserService();
        User user1 = new User("jan", "password", "jan@test.nl", Role.Freelancer);
        service.register(user1);
        // Uitvoeren + controleren: tweede user met jan@test.nl, verwacht de exception
        User user2 = new User("Jan", "password", "jan@test.nl", Role.Freelancer);
        assertThrows(EmailAlreadyExistsException.class, () -> service.register(user2));
    }
}