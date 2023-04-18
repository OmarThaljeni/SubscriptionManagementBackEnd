package Application.Services;

import Application.Models.User;
import Application.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementsServices {
    private final UserRepository userRepository;

    public Collection<User> getUserLists() {
        return userRepository.findByRolesNotEmpty();
    }

    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
