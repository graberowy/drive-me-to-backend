package drive_me_to.car.web.resources;

import drive_me_to.car.repository.Car;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.web.resources.DriverDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CarMapperTest {

    private final CarMapper carMapper = new CarMapperImpl();

    @Test
    void when_use_mapToCarDTO_then_carDTO_should_be_returned() {
        //given
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));

        Car car = new Car();
        car.setId(1L);
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));
        car.setDriver(driver);
        //when
        CarDTO carDTO = carMapper.mapToCarDTO(car);
        //then
        assertEquals(car.getId(), carDTO.getId());
        assertEquals(car.getStatus(), carDTO.getStatus());
        assertEquals(car.getMake(), carDTO.getMake());
        assertEquals(car.getPricePerKm(), carDTO.getPricePerKm());
        assertEquals(car.getDriver().getId(), carDTO.getDriver().getId());
        assertEquals(car.getDriver().getName(), carDTO.getDriver().getName());
        assertNull(carDTO.getDriver().getLanguages());
        assertNull(carDTO.getDriver().getOrders());
        assertNull(carDTO.getDriver().getCar());
    }

    @Test
    void when_use_mapToCar_then_car_should_be_returned() {
        //given
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(1L);
        driverDTO.setName("Sebastian Nowak");
        driverDTO.setLanguages(Set.of(Languages.POLISH));

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setMake(CarMake.BMW);
        carDTO.setStatus(CarStatus.READY_TO_GO);
        carDTO.setCurrency("PLN");
        carDTO.setPricePerKm(BigDecimal.valueOf(2));
        carDTO.setDriver(driverDTO);
        //when
        Car car =carMapper.mapToCar(carDTO);
        //then
        assertEquals(carDTO.getId(), car.getId());
        assertEquals(carDTO.getStatus(), car.getStatus());
        assertEquals(carDTO.getMake(), car.getMake());
        assertEquals(carDTO.getPricePerKm(), car.getPricePerKm());
        assertEquals(carDTO.getDriver().getId(), car.getDriver().getId());
        assertEquals(carDTO.getDriver().getName(), car.getDriver().getName());
        assertTrue(car.getDriver().getLanguages().isEmpty());
        assertTrue(car.getDriver().getOrders().isEmpty());
        assertNull(car.getDriver().getCar());
    }

}