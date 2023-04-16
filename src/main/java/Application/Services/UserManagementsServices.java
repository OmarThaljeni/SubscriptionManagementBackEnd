package Application.Services;

import Application.Models.User;
import Application.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementsServices {
    private final UserRepository repository;

    public Collection<User> getUserLists() {
        return repository.findByRolesNotEmpty();
    }


}
