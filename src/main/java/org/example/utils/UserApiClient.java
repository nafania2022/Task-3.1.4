package org.example.utils;

import org.example.models.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserApiClient {
    private final RestTemplate template;
    private final String baseUrl;
    private String sessionId;



    public UserApiClient(RestTemplate template, String baseUrl) {
        this.template = template;
        this.baseUrl = baseUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ResponseEntity<String> getAllUsers() {
        ResponseEntity<String> response = template.getForEntity(baseUrl + "/users", String.class );
        sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return response;
    }

    public ResponseEntity<String> createUser(String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE,  sessionId);
        User user = new User(3L, "James", "Brown", (byte) 30);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return template.exchange(baseUrl + "/users", HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> updateUser(String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE,sessionId);
        User user = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return template.exchange(baseUrl + "/users", HttpMethod.PUT, entity, String.class);
    }

    public ResponseEntity<String> deleteUser(String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return template.exchange(baseUrl + "/users/3", HttpMethod.DELETE, entity, String.class);
    }
}