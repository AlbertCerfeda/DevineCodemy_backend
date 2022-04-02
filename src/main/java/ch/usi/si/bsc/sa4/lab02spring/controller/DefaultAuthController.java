package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/")
public class DefaultAuthController {
    private final UserService userService;
    private final RestTemplate restTemplate;

    @Autowired
    public DefaultAuthController(UserService userService) {
        this.userService = userService;
        restTemplate = new RestTemplate();
    }

    @RequestMapping("/**")
    public String getUserToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity result = restTemplate.exchange("http://localhost:8080/users/foo", HttpMethod.GET, entity,String.class);
        HttpHeaders responseHeaders = result.getHeaders();
        String body = (String) result.getBody();
        if (result.getBody() == null) {
            System.out.println("A BIT OF A PROBLEM");
        } else {
            System.out.println("ALLELUIA");
        }
//        String fromLocation = result.toString();
        System.out.println(body);
        System.out.println(responseHeaders.toString());
        System.out.println("location: " + responseHeaders.getLocation()); // For testing header
        System.out.println("Origin: " + responseHeaders.getOrigin());
        System.out.println("Content-type: " + responseHeaders.getContentType());
        return body;
    }
}

