package app.db.services.impl;

import app.db.entities.Car;
import app.db.reposotories.CarRepository;
import app.db.services.interfaces.CarService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Optional<Car> findById(long id) {
        return carRepository.findById( id);
    }

    @Override
    public List<Car> findAll() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public void save(Car car) {
        if (car != null) {
            carRepository.save(car);
        }
    }

    @Override
    public void update(Car car) {
        if (car != null && carRepository.existsById(car.getId())) {
            carRepository.save(car);
        }
    }

    @Override
    public void delete(Car car) {
        if (car != null && carRepository.existsById(car.getId())) {
            carRepository.deleteById(car.getId());
        }
    }

    @Override
    public void deleteById(long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        }
    }
}
