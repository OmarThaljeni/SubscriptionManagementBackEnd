package Application.Controller;

import Application.Models.User;
import Application.Security.Payload.UserRequest;
import Application.Security.Payload.AuthenticationResponse;
import Application.Services.UserManagementsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/SubscriptionManagement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserManagementsController {

    private final UserManagementsServices userManagementsServices;

    @GetMapping("/list-users")
    public Collection<User> getUserLists() {
        return userManagementsServices.getUserLists();
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        return userManagementsServices.deleteUser(id);
    }
    @PostMapping("/add-customer")
    public AuthenticationResponse addCustomer(@RequestBody UserRequest userRequest) {
        return userManagementsServices.addCustomer(userRequest);
    }

    @PutMapping("/update-customer/{id}")
    public AuthenticationResponse updateCustomer(@PathVariable("id") Integer id,@RequestBody UserRequest userRequest) {
        return userManagementsServices.updateCustomer(id, userRequest);
    }
    }
