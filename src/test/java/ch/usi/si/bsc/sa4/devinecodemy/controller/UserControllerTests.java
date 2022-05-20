package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private FakeOAuth2User fakeOAuth2User;

    @BeforeEach
    void setup() {
        fakeOAuth2User = new FakeOAuth2User("an id");
    }

    @Test
    @DisplayName("List of public users")
    void testGetAll(){
        // TODO
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

