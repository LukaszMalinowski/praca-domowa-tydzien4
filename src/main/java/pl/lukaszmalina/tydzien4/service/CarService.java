package pl.lukaszmalina.tydzien4.service;

import pl.lukaszmalina.tydzien4.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();

    Optional<Car> getCarById(long id);

    List<Car> getCarsByColor(String color);

    boolean addCar(Car car);

    boolean updateCar(Car car);

    boolean removeCarById(long id);
}
