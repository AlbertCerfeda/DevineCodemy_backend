package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Provides all operations relating users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    
    //
    
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public List<User> getAllPublic() { return userRepository.findAllPublic(); }
    
    
    /**
     * Returns, if the user exists, whether its profile is public or not.
     * @param id the ID of the user to look for.
     * @return an Optional containing, if the user exists, a boolean value that tells if the profile is public o not.
     */
    public Optional<Boolean> isUserPublic(String id) { return userRepository.isUserPublic(id); }
    
    /**
     * Returns a User with a specific ID.
     * @param id the id of the user to look for.
     * @return an Optional containing the User if there is one with the provided ID.
     */
    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }
    public List<User> searchByNameContaining(String string) {
        return userRepository.findAllByNameContaining(string);
    }
    
    //
    
    public User createUser(CreateUserDTO createUserDTO) {
        var hash = PasswordHashingService.getInstance().hashPassword(createUserDTO.getPassword());
        var user = new User(createUserDTO.getName(), hash);
        return userRepository.save(user);
    }
    
    
    //
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> changePassword(String userId, String oldPassword, String newPassword) {
        var optionalUser = this.getById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        optionalUser.get().changePassword(oldPassword, newPassword);
        return Optional.of(this.updateUser(optionalUser.get()));
    }
    
    //
}
