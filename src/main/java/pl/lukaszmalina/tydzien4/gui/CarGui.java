package pl.lukaszmalina.tydzien4.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.lukaszmalina.tydzien4.entity.Car;

import java.util.LinkedHashMap;

@Route("cars")
public class CarGui extends VerticalLayout {

    public CarGui() {
        Button button = new Button("Fetch");
        button.addClickListener(event -> {
            RestTemplate restTemplate = new RestTemplate();
            LinkedHashMap entity = restTemplate.getForObject("http://localhost:8080/api/cars", LinkedHashMap.class);
            LinkedHashMap content = (LinkedHashMap) entity.get("content");
            System.out.println(content);
        });
        add(button);
    }
}
