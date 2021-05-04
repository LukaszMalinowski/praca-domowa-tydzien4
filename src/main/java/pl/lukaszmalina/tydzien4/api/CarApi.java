package pl.lukaszmalina.tydzien4.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lukaszmalina.tydzien4.entity.Car;
import pl.lukaszmalina.tydzien4.service.CarService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/cars")
public class CarApi {

    CarService service;

    @Autowired
    public CarApi(CarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        List<Car> cars = service.getCars();

        cars.forEach(this::addSelfLink);

        return new ResponseEntity<>(CollectionModel.of(cars, linkTo(CarApi.class).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> carOptional = service.getCarById(id);


        if(carOptional.isPresent()) {
            Car car = carOptional.get();
            car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class).slash(id).withSelfRel());
            return new ResponseEntity<>(car, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> cars = service.getCarsByColor(color);

        cars.forEach(this::addSelfLink);

        return new ResponseEntity<>(CollectionModel.of(cars, linkTo(CarApi.class).withSelfRel()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@Validated @RequestBody Car car) {
        boolean isAdded = service.addCar(car);

        if(isAdded)
            return new ResponseEntity<>(HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<Void> updateCar(@Validated @RequestBody Car car) {
        boolean isAdded = service.updateCar(car);

        if(isAdded)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCarField(@RequestBody Car newCar) {
        List<Car> cars = service.getCars();
        Optional<Car> carOptional = cars.stream().filter(oldCar -> oldCar.getId() == newCar.getId()).findFirst();

        if(carOptional.isPresent()) {
            Car updatedCar = carOptional.get();

            if(newCar.getMark() != null && !newCar.getMark().isEmpty()) {
                service.updateCar(new Car(updatedCar.getId(), newCar.getMark(), updatedCar.getModel(), updatedCar.getColor()));
                return new ResponseEntity<>(HttpStatus.OK);
            }

            if(newCar.getModel() != null && !newCar.getModel().isEmpty()) {
                service.updateCar(new Car(updatedCar.getId(), updatedCar.getMark(), newCar.getModel(), updatedCar.getColor()));
                return new ResponseEntity<>(HttpStatus.OK);
            }

            if(newCar.getColor() != null && !newCar.getColor().isEmpty()) {
                service.updateCar(new Car(updatedCar.getId(), updatedCar.getMark(), updatedCar.getModel(), newCar.getColor()));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        boolean isRemoved = service.removeCarById(id);

        if(isRemoved)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    void addSelfLink(Car car) {
        car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class).slash(car.getId()).withSelfRel());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addThreeCars() {
        addCar(new Car(1L, "Ford", "Mustang", "Red"));
        addCar(new Car(2L, "Audi", "A5", "Black"));
        addCar(new Car(3L, "BMW", "X3", "White"));
    }
}
