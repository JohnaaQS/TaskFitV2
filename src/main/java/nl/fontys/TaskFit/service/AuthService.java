package nl.fontys.TaskFit.service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.fontys.TaskFit.exception.EmailAlreadyExistsException;
import nl.fontys.TaskFit.exception.InvalidCredentialsException;
import nl.fontys.TaskFit.model.ClientProfile;
import nl.fontys.TaskFit.model.FreelancerProfile;
import nl.fontys.TaskFit.model.Role;
import nl.fontys.TaskFit.model.User;
import nl.fontys.TaskFit.repository.ClientProfileRepository;
import nl.fontys.TaskFit.repository.FreelancerProfileRepository;
import nl.fontys.TaskFit.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       ClientProfileRepository clientProfileRepository,
                       FreelancerProfileRepository freelancerProfileRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.freelancerProfileRepository = freelancerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User en leeg profiel worden samen opgeslagen of samen teruggedraaid.
    @Transactional
    public User register(String name, String email, String password, Role role) {
        // 1: e-mail schoonmaken, zodat JAN@test.nl en jan@test.nl hetzelfde adres zijn
        String cleanEmail = normalizeEmail(email);

        // 2: eerst controleren, want hashen is duur
        if (userRepository.existsByEmail(cleanEmail)) {
            throw new EmailAlreadyExistsException("Email already exists: " + cleanEmail);
        }

        // 3: wachtwoord hashen en user opslaan
        String hashedPassword = passwordEncoder.encode(password);
        User user = userRepository.save(new User(name, hashedPassword, cleanEmail, role));

        // 4: leeg profiel aanmaken op basis van de rol
        if (role == Role.Client) {
            clientProfileRepository.save(new ClientProfile(user.getId(), null, null));
        } else {
            freelancerProfileRepository.save(new FreelancerProfile(user.getId(), null, null));
        }

        return user;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Zelfde melding als bij een onbekend adres, zodat niet te zien is welke adressen bestaan.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}