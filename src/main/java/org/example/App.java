package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.User;
import org.example.utils.UserApiClient;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class App
{
    public static void main( String[] args ){
        RestTemplate template = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/";
        UserApiClient userApiClient = new UserApiClient(template, url);
        TypeReference<List<User>> typeReference = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users;
        try {
             users = objectMapper.readValue(userApiClient.getAllUsers().getBody(), typeReference);
        }catch ( JsonProcessingException e ) {
            users = null;
            e.printStackTrace();
        }
        System.out.println(users);
        String sessionId = userApiClient.getSessionId();
        String result = userApiClient.createUser(sessionId).getBody() + userApiClient.updateUser(sessionId).getBody() + userApiClient.deleteUser(sessionId).getBody();
        System.out.println(result);
    }
}