package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;

public class UserServiceTests {
    @Test
    public void testGetById() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = new User("an id", "a name", "a hash");
        given(userRepository.findById("an id")).willReturn(Optional.of(user));
    }

    @Test
    public void testSearchByNameContaining() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User user = new User("an id", "a name", "a hash");
        given(userService.searchByNameContaining("a name", true)).willReturn(List.of(user));
        given(userService.searchByNameContaining("a name", false)).willReturn(List.of(user));
    }

    @Test
    public void testGetAll() {
        User user = new User("an id", "a name", "a hash");
        User user1 = new User("an id1", "a name1", "a hash1");
        User user2 = new User("an id2", "a name2", "a hash2");
        List<User> users = List.of(user, user1, user2);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        given(userRepository.findAll()).willReturn(users);
    }

    @Test
    public void testFindAllPublic() {
        User user = new User("an id", "a name", "a hash");
        user.setPublicProfile(true);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        given(userRepository.findAllPublic()).willReturn(List.of(user));

        User user1 = new User("an id1", "a name1", "a hash1");
        user1.setPublicProfile(false);
        given(userRepository.findAllPublic()).willReturn(List.of(user1));
    }
}
