package drive_me_to.car.service;

import drive_me_to.car.repository.Car;
import drive_me_to.car.repository.CarRepository;
import drive_me_to.generic.service.GenericServiceBasic;
import org.springframework.stereotype.Service;

@Service
public class CarServiceBasic extends GenericServiceBasic<Car, Long> implements CarService {
    /**
     * This is constructor for use generic operations on specific type
     *
     * @param carRepository specified type interface
     */
    public CarServiceBasic(CarRepository carRepository) {
        super(carRepository);
    }
}
