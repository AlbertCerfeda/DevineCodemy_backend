package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserAlreadyExistsException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.repository.UserRepository;

import java.util.*;

/**
 * The UserService class provides all operations relating users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final StatisticsService statisticsService;
    
    /**
     * Construct a UserService by autowiring its dependencies.
     * @param userRepository the UserRepository.
     * @param statisticsService the statistics service.
     */
    @Autowired
    public UserService(UserRepository userRepository, StatisticsService statisticsService) {
        this.userRepository = userRepository;
        this.statisticsService = statisticsService;
    }

    /**
     * Get a list of users with a specific name.
     * @return a list of users with a specific name.
     * */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Get a list of all public users
     * @return a list of all public users
     * */
    public List<User> getAllPublic() { return userRepository.findAllPublic(); }

    /**
     * Returns, if the user exists, whether its profile is public or not.
     *
     * @param id the ID of the user to look for.
     * @return an Optional containing, if the user exists, a boolean value that tells if the profile is public o not.
     */
    public Optional<Boolean> isUserPublic(String id) {
        Optional<User> optionalUser = userRepository.isUserPublic(id);
        return optionalUser.map(User::isProfilePublic);
    }

    /**
     * Returns a User with a specific ID.
     *
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
     * Returns true if a user with specific ID exists.
     *
     * @param userId the userId of the user to look for.
     * @return a Boolean
     */
    public Boolean userIdExists(String userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Returns true if a user with specific name exists.
     *
     * @param name the name of the user to look for.
     * @return a Boolean
     */
    public boolean userNameExists(String name) {
        return userRepository.existsByName(name);
    }

    /**
     * Create the user and saves it into the Database.
     *
     * @param createUserDTO User to be saved
     * @return User The user which is created
     * @throws IllegalArgumentException   if the createUserDTO contains invalid values.
     * @throws UserAlreadyExistsException if the user we are trying to add already exists.
     */
    public User addUser(CreateUserDTO createUserDTO) throws IllegalArgumentException, UserAlreadyExistsException {
        if (createUserDTO.getUsername() == null || createUserDTO.getName() == null || createUserDTO.getId() == null) {
            throw new IllegalArgumentException("Both username, id and name must be inserted.");
        } else if (!checkBodyFormat(createUserDTO)) {
            throw new IllegalArgumentException("Values of username or password cannot be empty.");
        } else if (userIdExists(createUserDTO.getId())) {
            throw new UserAlreadyExistsException("ID is already taken.");
        }

        User user = new User(createUserDTO.getId(), createUserDTO.getName(), createUserDTO.getUsername(), createUserDTO.getEmail(), createUserDTO.getAvatarUrl(),
                createUserDTO.getBio(), new SocialMedia(createUserDTO.getTwitter(), createUserDTO.getSkype(), createUserDTO.getLinkedin()));
        userRepository.save(user);
        statisticsService.addStats(user.getId());
        return user;
    }

    /**
     * Update the user and save it into the Database
     *
     * @param user The user to be saved
     * @return User updated
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes the user by getting hte id of it.
     *
     * @param id takes in the User Id.
     */
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Checks if the body of the request is valid.
     * @param user The user to be created.
     * @return true if the body is valid, else false
     */
    public static boolean checkBodyFormat(CreateUserDTO user) {
        return !(Objects.equals(user.getName(), "") ||
                Objects.equals(user.getEmail(), "") ||
                Objects.equals(user.getUsername(), "") ||
                Objects.equals(user.getId(), ""));
    }

    /**
     * Return true if ID matches the user of the corresponding token
     *
     * @param authenticationToken token that belongs to user.
     * @param id                  the id of the user
     * @return result of the comparison between token's user's id and id
     */
    public boolean isIdEqualToken(OAuth2AuthenticationToken authenticationToken, String id) {
        User u;
        try {
            u = getUserByToken(authenticationToken);
        } catch (InvalidAuthTokenException | UserInexistentException e) {
            return false;
        }
        return u.getId().equals(id);
    }

    /**
     * Return the user matching the given authenticationToken.
     *
     * @param authenticationToken the OAuth2 auth token.
     * @return the User.
     * @throws InvalidAuthTokenException if the auth token is invalid.
     * @throws UserInexistentException   if the user does not exist.
     */
    public User getUserByToken(OAuth2AuthenticationToken authenticationToken) throws InvalidAuthTokenException, UserInexistentException {
        if (authenticationToken == null) {
            throw new InvalidAuthTokenException();
        }

        // Retrieves the User from the OAuth2
        OAuth2User u = authenticationToken.getPrincipal();
        Optional<User> user = getById(u.getName());
        if (user.isEmpty()) {
            throw new UserInexistentException(u.getName());
        } else {
            return user.get();
        }
    }
}
    
    

