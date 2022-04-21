package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Objects;
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
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public List<User> getAllPublic() { return userRepository.findAllPublic(); }
    
    
    /**
     * Returns, if the user exists, whether its profile is public or not.
     * @param id the ID of the user to look for.
     * @return an Optional containing, if the user exists, a boolean value that tells if the profile is public o not.
     */
    public Optional<Boolean> isUserPublic(String id){
        Optional<User> optionalUser = userRepository.isUserPublic(id);
        return optionalUser.map((user)->user.isProfilePublic());
    } //TODO: Function tested but never used

    /**
     * Returns a User with a specific ID.
     * @param id the id of the user to look for.
     * @return an Optional containing the User if there is one with the provided ID.
     */
    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public List<User> searchByNameContaining(String string, boolean publicProfile) {
        return publicProfile
                ? userRepository.findAllByNameContainingAndPublicProfileTrue(string)
                : userRepository.findAllByNameContainingAndPublicProfileFalse(string);
    }

    /**
     * Returns true if a user with specific name exists.
     * @param name the name of the user to look for.
     * @return a Boolean
     */
    public Boolean userExists(String name) {
        return userRepository.existsByName(name);
    }

    /**
     * Create the user and saves it into the Database
     * @param createUserDTO User to be saved
     * @return User The user which is created
     */
    public User createUser(CreateUserDTO createUserDTO) {
        var user = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),createUserDTO.getEmail());
        return userRepository.save(user);
    }

    /**
     * Update the user and save it into the Database
     * @param user The user to be saved
     * @return User updated */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes the user by getting hte id of it.
     * @param id takes in the User Id.
     * */
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
   }

    public boolean checkBodyFormat(CreateUserDTO user) {
        boolean checkingFlag = true;
        if((Objects.equals(user.getName(), "")) ||
                Objects.equals(user.getEmail(), "") ||
                Objects.equals(user.getUsername(), "") ||
                Objects.equals(user.getId(), "")) {
            checkingFlag = false;
        }
        return checkingFlag;
    }

    /**
     * Return the user matching the given authenticationToken.
     * @param authenticationToken token that belongs to user.
     * @return Optional<User> user.
     */
    public Optional<User> getUserByToken(OAuth2AuthenticationToken authenticationToken) throws IllegalArgumentException {
        if (authenticationToken == null) {
            throw new IllegalArgumentException("Token is null.");
        }

        // Retrieves the User from the OAuth2
        OAuth2User u = authenticationToken.getPrincipal();
        Optional<User> user = getById(u.getName());
        return user;
    }
}
    
    

