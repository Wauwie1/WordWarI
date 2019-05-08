package Client;

import javafx.event.ActionEvent;
import org.springframework.web.client.RestTemplate;

public class Controller {

    public void button_Get_Clicked(ActionEvent actionEvent) {
        RestTemplate restTemplate = new RestTemplate();
        Greeting greeting = restTemplate.getForObject("http://localhost:8081/greeting", Greeting.class);
        System.out.println(greeting.toString());
    }
}
