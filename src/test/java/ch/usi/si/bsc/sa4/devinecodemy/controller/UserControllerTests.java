package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UpdateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.DynamicJsonObject;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@AutoConfigureMockMvc
@DisplayName("he User Controller")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {
    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user1;
    private User user2;
    private List<User> userList;

    private FakeOAuth2User fakeOAuth2User;
    private FakeOAuth2User invalidOAuth2User;

    private OAuth2AuthenticationToken fakeAuthenticationToken;
    private OAuth2AuthenticationToken invalidAuthenticationToken;
    private OAuth2AuthenticationToken nonexistentAuthenticationToken;

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

        fakeOAuth2User = new FakeOAuth2User("a name");
        invalidOAuth2User = new FakeOAuth2User("invalid");

        fakeAuthenticationToken = fakeOAuth2User.getOAuth2AuthenticationToken();
        invalidAuthenticationToken = invalidOAuth2User.getOAuth2AuthenticationToken();

        given(userService.getById("an id")).willReturn(Optional.of(user1));
        given(userService.getById("invalid")).willReturn(Optional.of(user1));
        given(userService.getById("nonexistent")).willReturn(Optional.empty());

        given(userService.isIdEqualToken(fakeAuthenticationToken, "invalid")).willReturn(false);
        given(userService.isIdEqualToken(fakeAuthenticationToken, "an id")).willReturn(true);
//        given(userService.isIdEqualToken(any(), any())).willReturn(true);

        given(userService.updateUser(user1)).willReturn(user1);
    }

    @Test
    @DisplayName("List of public users")
    void testGetAll() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User.getOAuth2AuthenticationToken())).willReturn(user1);
        MvcResult result = mockMvc.perform(get("/users")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());

        // We have to use DynamicJsonObject as it's not possible for Jackson to deserialize a UserDTO.
        List<DynamicJsonObject> responseList = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<DynamicJsonObject>>() {});
        assertEquals(2, responseList.size());
        assertEquals("a name", responseList.get(0).get("name"));
        assertEquals("another name", responseList.get(1).get("name"));
    }

    @Test
    @DisplayName("Put /users/{id}")
    void testUpdateUser() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User.getOAuth2AuthenticationToken())).willReturn(user1);
        UserDTO u1 = new UserDTO(user1, false);

        // Test for Status 200 OK
        MvcResult resultOk = mockMvc.perform(put("/users/an id/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u1))
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
                .andReturn();
        assertEquals(200, resultOk.getResponse().getStatus());

        // Test for Status 404 NOT FOUND
        MvcResult resultNotFound = mockMvc.perform(put("/users/nonexistent/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u1))
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
                .andReturn();
        assertEquals(404, resultNotFound.getResponse().getStatus());

        assertThrows(NestedServletException.class, () -> {
            MvcResult result = mockMvc.perform(put("/users/invalid/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(u1))
                            .with(SecurityMockMvcRequestPostProcessors
                                    .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
                    .andReturn();
        });

        UpdateUserDTO updateUserDTO = Mockito.mock(UpdateUserDTO.class);
        given(updateUserDTO.isPublicProfileInitialized()).willReturn(true);
        given(updateUserDTO.isPublicProfile()).willReturn(true);

        // Don't mockMvc this: Can't supply a mock UpdateUserDTO via API endpoint
        ResponseEntity<UserDTO> okResponse = userController.updateUser(fakeAuthenticationToken, "an id", updateUserDTO);
        assertEquals(HttpStatus.OK, okResponse.getStatusCode());
        verify(updateUserDTO).isPublicProfile();

        UpdateUserDTO privateUpdateUserDTO = Mockito.mock(UpdateUserDTO.class);
        given(privateUpdateUserDTO.isPublicProfileInitialized()).willReturn(false);

        // Don't mockMvc this: Can't supply a mock UpdateUserDTO via API endpoint
        ResponseEntity<UserDTO> okResponse2 = userController.updateUser(fakeAuthenticationToken, "an id", privateUpdateUserDTO);
        assertEquals(HttpStatus.OK, okResponse2.getStatusCode());
        verify(privateUpdateUserDTO, Mockito.never()).isPublicProfile();
    }

    @Test
    @DisplayName("GET /users/search?name=string")
    void testGetByNameContaining(){
        given(userService.searchByNameContaining("name", true)).willReturn(userList);
        ResponseEntity<List<UserDTO>> responseEntity = userController.getByNameContaining("name");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<UserDTO> responseList = responseEntity.getBody();
        assertEquals(2, responseList.size());
        assertEquals("a name", responseList.get(0).getName());
        assertEquals("another name", responseList.get(1).getName());
    }

    @Test
    @DisplayName("GET /users/{id}}")
    void testGetById(){
        ResponseEntity<UserDTO> nonexistentResponse = userController.getById(null, "nonexistent");
        assertEquals(HttpStatus.NOT_FOUND, nonexistentResponse.getStatusCode());

        User publicUser = Mockito.mock(User.class);
        given(publicUser.isProfilePublic()).willReturn(true);
        User privateUser = Mockito.mock(User.class);
        given(privateUser.isProfilePublic()).willReturn(false);
        given(userService.getById("public")).willReturn(Optional.of(publicUser));
        given(userService.getById("private")).willReturn(Optional.of(privateUser));

        given(userService.isIdEqualToken(any(), any())).willReturn(false);

        ResponseEntity<UserDTO> publicResponseMismatchingToken = userController.getById(fakeAuthenticationToken, "public");
        assertEquals(HttpStatus.OK, publicResponseMismatchingToken.getStatusCode());
        verify(publicUser).toPublicUserDTO();

        ResponseEntity<UserDTO> privateResponseMismatchingToken = userController.getById(fakeAuthenticationToken, "private");
        assertEquals(HttpStatus.OK, privateResponseMismatchingToken.getStatusCode());
        verify(privateUser).toPrivateUserDTO();

        Mockito.reset(publicUser, privateUser); // Clears the `verify()` counter
        given(userService.isIdEqualToken(any(), any())).willReturn(true);

        ResponseEntity<UserDTO> publicResponseMatchingToken = userController.getById(fakeAuthenticationToken, "public");
        assertEquals(HttpStatus.OK, publicResponseMatchingToken.getStatusCode());
        verify(publicUser).toPublicUserDTO();

        ResponseEntity<UserDTO> privateResponseMatchingToken = userController.getById(fakeAuthenticationToken, "private");
        assertEquals(HttpStatus.OK, privateResponseMatchingToken.getStatusCode());
        verify(privateUser).toPublicUserDTO();
    }

    @Test
    @DisplayName("GET /users/user")
    void testGetUser(){
        User publicUser = Mockito.mock(User.class);

        given(userService.getUserByToken(fakeAuthenticationToken)).willReturn(publicUser);
        given(userService.getUserByToken(invalidAuthenticationToken)).willThrow(new InvalidAuthTokenException());
        given(userService.getUserByToken(nonexistentAuthenticationToken)).willThrow(new UserInexistentException());

        ResponseEntity<UserDTO> okResponse = userController.getUser(fakeAuthenticationToken);
        assertEquals(HttpStatus.OK, okResponse.getStatusCode());
        verify(publicUser).toPublicUserDTO();

        ResponseEntity<UserDTO> unauthorizedResponse = userController.getUser(invalidAuthenticationToken);
        assertEquals(HttpStatus.UNAUTHORIZED, unauthorizedResponse.getStatusCode());

        ResponseEntity<UserDTO> nonexistentResponse = userController.getUser(nonexistentAuthenticationToken);
        assertEquals(HttpStatus.NOT_FOUND, nonexistentResponse.getStatusCode());
    }
}

