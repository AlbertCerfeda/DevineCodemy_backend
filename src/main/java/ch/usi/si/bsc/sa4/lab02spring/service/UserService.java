package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> searchByNameContaining(String string) {
        return userRepository.findAllByNameContaining(string);
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(CreateUserDTO createUserDTO) {
        var hash = PasswordHashingService.getInstance().hashPassword(createUserDTO.getPassword());
        var user = new User(createUserDTO.getName(), hash);
        return userRepository.save(user);
    }

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
}
