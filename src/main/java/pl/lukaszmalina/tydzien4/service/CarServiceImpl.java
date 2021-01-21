package pl.lukaszmalina.tydzien4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszmalina.tydzien4.entity.Car;
import pl.lukaszmalina.tydzien4.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    CarRepository repository;

    @Autowired
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> getCars() {
        return repository.getCars();
    }

    @Override
    public Optional<Car> getCarById(long id) {
        return repository.getCarById(id);
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        return repository.getCarsByColor(color);
    }

    @Override
    public boolean addCar(Car car) {
        return repository.addCar(car);
    }

    @Override
    public boolean updateCar(Car car) {
        return repository.updateCar(car);
    }

    @Override
    public boolean removeCarById(long id) {
        return repository.removeCarById(id);
    }
}
