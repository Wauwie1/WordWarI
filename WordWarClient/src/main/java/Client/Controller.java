package Client;

import Responses.IResponse;
import Responses.Response;
import javafx.event.ActionEvent;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

public class Controller {

    public void button_Get_Clicked(ActionEvent actionEvent) {
        RestTemplate restTemplate = new RestTemplate();
        IResponse<Object> greeting = restTemplate.getForObject("http://localhost:8081/login", Response.class);
        System.out.println(greeting.toString());

//        String URL = "ws://localhost:8081/tutorialspoint-websocket";
//        WebSocketClient client = new StandardWebSocketClient();
//
//        WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        stompClient.connect(URL, sessionHandler);
//
//        new Scanner(System.in).nextLine();
    }
}
