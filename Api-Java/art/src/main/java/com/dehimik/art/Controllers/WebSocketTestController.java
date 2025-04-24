package com.dehimik.art.Controllers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketTestController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public String handleTestMessage(String message) {
        System.out.println("Отримано повідомлення: " + message);
        return "Сервер відповідає: " + message;
    }
}
