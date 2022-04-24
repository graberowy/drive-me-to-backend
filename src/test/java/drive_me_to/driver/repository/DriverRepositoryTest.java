package drive_me_to.driver.repository;

import drive_me_to.car.repository.Car;
import drive_me_to.car.repository.CarRepository;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DriverRepositoryTest {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CarRepository carRepository;


    @Test
    void when_add_new_driver_then_new_record_should_be_added() {
        //given
        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));

        driverRepository.save(driver);
        //when
        List<Driver> driverList = driverRepository.findAll();
        //then
        assertEquals(1, driverList.size());
    }

    @Test
    void when_add_new_driver_with_car_then_both_records_should_be_added() {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));
        driver.setCar(car);

        driverRepository.save(driver);
        //when
        List<Driver> driverList = driverRepository.findAll();
        List<Car> carList = carRepository.findAll();
        //then
        assertEquals(1, driverList.size());
        assertEquals(1, carList.size());
    }

    @Test
    void when_delete_existing_driver_related_car_should_not_be_removed() {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));
        driver.setCar(car);

        driverRepository.save(driver);
        driverRepository.delete(driver);
        //when
        List<Driver> driverList = driverRepository.findAll();
        List<Car> carList = carRepository.findAll();
        //then
        assertTrue(driverList.isEmpty());
        assertEquals(1, carList.size());
    }

}