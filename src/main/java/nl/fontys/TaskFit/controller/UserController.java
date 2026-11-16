package nl.fontys.TaskFit.controller;
import nl.fontys.TaskFit.dto.UserRequest;
import nl.fontys.TaskFit.model.User;
import nl.fontys.TaskFit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.fontys.TaskFit.dto.UserResponse;



@RestController                  // deze klasse handelt HTTP af en geeft JSON terug
@RequestMapping("/api/users")    // alle endpoints in deze klasse beginnen met /api/users
public class UserController {

    private final UserService userService;
    

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping                 // reageert op POST /api/users
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        //1: maak van de request een User (let op de volgorde van je parameters)
        User user = new User(request.name(), request.password(), request.email(),request.role());
        // 2: geef die User aan de service
        User savedUser = userService.register(user);
        // 3: maak van de User die terugkomt een UserResponse
        UserResponse response = new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
        // 4: geef die terug met status 201
        return ResponseEntity.status(201).body(response);
    }

    
}