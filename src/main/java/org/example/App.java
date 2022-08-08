package org.example;

import org.example.configs.MyConfig;
import org.example.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        ReceiveSecretKey receiveSecretKey = context.getBean("receiveSecretKey", ReceiveSecretKey.class);

        //получаем список всех пользователей
        receiveSecretKey.getAllUsers();

        //сохраняем пользователя с id=3
        User user = new User(3L, "James", "Brown", (byte) 40);
        receiveSecretKey.saveUser(user);

        //изменяем пользователя с id=3
        user.setName("Thomas");
        user.setLastName("Shelby");
        receiveSecretKey.updateUser(user);

        //удаляем пользователя с id=3
        receiveSecretKey.deleteUser(3L);
    }
}
