package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@DisplayName("he User Controller")

public class UserControllerTests {
    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    private User user1;
    private User user2;
    private List<User> userList;


    @BeforeEach
    void setup() {
        userController = new UserController(userService);

        user1 = new User("an id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id","another name", "another username", "another email",
                "another avatar","another bio",
                new SocialMedia("another twitter","another skype", "another linkedin"));

        userList = List.of(user1, user2);

        given(this.userService.getAllPublic()).willReturn(userList);
    }

    @Test
    @DisplayName("List of public users")
    void testGetAll(){
        ResponseEntity<List<UserDTO>> responseEntity = userController.getAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<UserDTO> responseList = responseEntity.getBody();
        assertEquals(2, responseList.size());
        assertEquals("a name", responseList.get(0).getName());
        assertEquals("another name", responseList.get(1).getName());
    }

    @Test
    @DisplayName("Put /user/{id}")
    void testUpdateUser(){
        // TODO
    }

    @Test
    @DisplayName("GET /users/search?name=string")
    void testGetByNameContaining(){
        // TODO
    }

    @Test
    @DisplayName("GET /users/{id}}")
    void testGetById(){
        // TODO
    }

    @Test
    @DisplayName("GET /users/user")
    void testGetUser(){
        // TODO
    }
}

