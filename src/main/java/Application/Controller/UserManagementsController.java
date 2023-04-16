package Application.Controller;

import Application.Models.User;
import Application.Services.UserManagementsServices;
import lombok.RequiredArgsConstructor;
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


    }
