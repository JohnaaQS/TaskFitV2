package nl.fontys.TaskFit.service;
import nl.fontys.TaskFit.model.User;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import nl.fontys.TaskFit.exception.EmailAlreadyExistsException;
import java.util.UUID;
import nl.fontys.TaskFit.exception.UserNotFoundException;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    
    public User register(User user){
        for (User existing : users) {
            if (existing.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
            }
        }
        users.add(user);
        return user;
    }

    public User findById(UUID id) {
    for (User user : users) {
        if (user.getId().equals(id)) {
            return user;
        }
    }

    throw new UserNotFoundException("User not found");
    }
}
