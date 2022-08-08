package org.example;

import org.example.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Получатель секретного ключа
 */
@Component
public class ReceiveSecretKey {
    /**
     * URL для запросов
     */
    private static final String URL = "http://94.198.50.185:7081/api/users/";
    /**
     * Имя ключа для заголовка
     */
    private static final String HEADER_COOKIE_PARAM_NAME = "cookie";

    private String cookie;
    private final StringBuilder resultSecretKey = new StringBuilder();

    private final RestTemplate restTemplate;
    public ReceiveSecretKey(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Получает всех пользователей с sessionID
     *
     * @return все пользователи {@link User}
     */
    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();

        cookie = responseEntity.getHeaders().get("set-cookie").toString();
        String[] s = cookie.split(";");
        cookie = s[0].substring(1);
        return allUsers;
    }

    /**
     * Сохраняет нового пользователя
     *
     * @param user пользователь
     */
    public void saveUser(User user) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(HEADER_COOKIE_PARAM_NAME, cookie);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
        resultSecretKey.append(responseEntity.getBody());
    }

    /**
     * Изменяет пользователя
     *
     * @param updateUser пользователь
     */
    public void updateUser(User updateUser) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(HEADER_COOKIE_PARAM_NAME, cookie);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(updateUser, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        resultSecretKey.append(responseEntity.getBody());
    }

    /**
     * Удаляет пользователя
     *
     * @param id пользователя
     */
    public void deleteUser(Long id) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(HEADER_COOKIE_PARAM_NAME, cookie);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + id, HttpMethod.DELETE, requestEntity, String.class);
        resultSecretKey.append(responseEntity.getBody()); // итоговый код: 5ebfebe7cb975dfcf9

        System.out.println("------ " + resultSecretKey + " ------");
    }
}
