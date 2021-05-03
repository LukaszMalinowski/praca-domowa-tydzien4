package pl.lukaszmalina.tydzien4.gui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lukaszmalina.tydzien4.service.CarService;

@Route ("cars")
public class CarGui extends VerticalLayout {

    CarService service;

    @Autowired
    public CarGui(CarService service) {
        this.service = service;

        System.out.println(service.getCars());
    }
}
