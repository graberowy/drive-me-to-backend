package drive_me_to.car.repository;

import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    void when_add_new_car_no_id_then_new_record_should_be_added() {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));

        carRepository.save(car);
        //when
        List<Car> carList = carRepository.findAll();
        //then
        assertEquals(1, carList.size());
    }

    @Test
    void when_delete_existing_car_then_record_should_be_removed() {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));

        carRepository.save(car);
        carRepository.delete(car);
        //when
        List<Car> carList = carRepository.findAll();
        //then
        assertTrue(carList.isEmpty());
    }

}