package drive_me_to.driver.web.resources;

import drive_me_to.car.repository.Car;
import drive_me_to.car.web.resources.CarDTO;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DriverMapperTest {

    private final DriverMapper driverMapper = new DriverMapperImpl();

    @Test
    void when_use_mapToDriverDTO_on_Driver_then_driverDTO_should_be_returned() {
        //given
        Car car = new Car();
        car.setId(1L);
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));

        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));
        driver.setCar(car);
        //when
        DriverDTO driverDTO = driverMapper.mapToDriverDTO(driver);
        //then
        assertEquals(driver.getId(), driverDTO.getId());
        assertEquals(driver.getLanguages(), driverDTO.getLanguages());
        assertEquals(driver.getOrders().size(), driverDTO.getOrders().size());
        assertEquals(driver.getName(), driverDTO.getName());
        assertEquals(driver.getCar().getId(), driverDTO.getCar().getId());
        assertEquals(driver.getCar().getMake(), driverDTO.getCar().getMake());
        assertEquals(driver.getCar().getPricePerKm(), driverDTO.getCar().getPricePerKm());
        assertEquals(driver.getCar().getCurrency(), driverDTO.getCar().getCurrency());
        assertEquals(driver.getCar().getStatus(), driverDTO.getCar().getStatus());

    }

    @Test
    void when_use_mapToDriver_then_driver_should_be_returned() {
        //given
        CarDTO carDTO = new CarDTO();
        carDTO.setMake(CarMake.BMW);
        carDTO.setStatus(CarStatus.READY_TO_GO);
        carDTO.setCurrency("PLN");
        carDTO.setPricePerKm(BigDecimal.valueOf(2));
        carDTO.setId(2L);

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setName("Sebastian Nowak");
        driverDTO.setLanguages(Set.of(Languages.POLISH));
        driverDTO.setCar(carDTO);
        driverDTO.setId(2L);
        //when
        Driver driver = driverMapper.mapToDriver(driverDTO);
        //then
        assertEquals(driverDTO.getId(), driver.getId());
        assertEquals(driverDTO.getLanguages(), driver.getLanguages());
        assertTrue(driver.getOrders().isEmpty());
        assertEquals(driverDTO.getName(), driver.getName());
        assertEquals(driverDTO.getCar().getId(), driver.getCar().getId());
        assertEquals(driverDTO.getCar().getMake(), driver.getCar().getMake());
        assertEquals(driverDTO.getCar().getPricePerKm(), driver.getCar().getPricePerKm());
        assertEquals(driverDTO.getCar().getCurrency(), driver.getCar().getCurrency());
        assertEquals(driverDTO.getCar().getStatus(), driver.getCar().getStatus());
    }

}