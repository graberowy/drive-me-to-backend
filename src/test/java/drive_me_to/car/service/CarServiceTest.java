package drive_me_to.car.service;

import drive_me_to.car.repository.Car;
import drive_me_to.car.repository.CarRepository;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CarServiceTest {
    private final CarRepository carRepository = Mockito.mock(CarRepository.class);
    private final CarServiceBasic carServiceBasic = new CarServiceBasic(carRepository);

    @Test
    void when_send_request_with_valid_id_then_car_with_provided_id_should_be_returned() {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));
        car.setId(1L);
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        //when
        Optional<Car> carReturned = carServiceBasic.findById(1L);
        //then
        assertEquals(CarMake.BMW, carReturned.get().getMake());
        assertEquals(CarStatus.READY_TO_GO, carReturned.get().getStatus());
        assertEquals(BigDecimal.valueOf(2), carReturned.get().getPricePerKm());
        assertEquals("PLN", carReturned.get().getCurrency());
        assertEquals(1L, carReturned.get().getId());
    }

    @Test
    void when_add_new_car_than_car_should_be_added_to_repo() {
        //given
        Car firstCar = new Car();
        firstCar.setMake(CarMake.BMW);
        firstCar.setStatus(CarStatus.READY_TO_GO);
        firstCar.setCurrency("PLN");
        firstCar.setPricePerKm(BigDecimal.valueOf(2));

        Car secondCar = new Car();
        secondCar.setMake(CarMake.BMW);
        secondCar.setStatus(CarStatus.READY_TO_GO);
        secondCar.setCurrency("PLN");
        secondCar.setPricePerKm(BigDecimal.valueOf(2));
        secondCar.setId(1L);

        Mockito.when(carRepository.save(firstCar)).thenReturn(secondCar);
        //when
        Car car = carServiceBasic.save(firstCar);
        //then
        assertEquals(CarMake.BMW, car.getMake());
        assertEquals(CarStatus.READY_TO_GO, car.getStatus());
        assertEquals("PLN", car.getCurrency());
        assertEquals(BigDecimal.valueOf(2), car.getPricePerKm());
        assertEquals(1L, car.getId());
        Mockito.verify(carRepository).save(firstCar);
    }

    @Test
    void when_update_existing_car_then_data_should_be_updated() {
        //given
        Car firstCar = new Car();
        firstCar.setMake(CarMake.BMW);
        firstCar.setStatus(CarStatus.READY_TO_GO);
        firstCar.setCurrency("PLN");
        firstCar.setPricePerKm(BigDecimal.valueOf(2));
        firstCar.setId(1L);

        Car secondCar = new Car();
        secondCar.setMake(CarMake.SUBARU);
        secondCar.setStatus(CarStatus.DRIVING);
        secondCar.setCurrency("EUR");
        secondCar.setPricePerKm(BigDecimal.valueOf(5));
        secondCar.setId(1L);

        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(firstCar));
        Mockito.when(carRepository.save(secondCar)).thenReturn(secondCar);
        //when
        Optional<Car> car = carServiceBasic.update(secondCar);
        //then
        assertEquals(CarMake.SUBARU, car.get().getMake());
        assertEquals(CarStatus.DRIVING, car.get().getStatus());
        assertEquals(BigDecimal.valueOf(5), car.get().getPricePerKm());
        assertEquals("EUR", car.get().getCurrency());
        assertEquals(1L, car.get().getId());
    }

    @Test
    void when_request_find_all_than_all_users_should_be_returned() {
        //given
        Car firstCar = new Car();
        firstCar.setMake(CarMake.BMW);
        firstCar.setStatus(CarStatus.READY_TO_GO);
        firstCar.setCurrency("PLN");
        firstCar.setPricePerKm(BigDecimal.valueOf(2));
        firstCar.setId(1L);

        Car secondCar = new Car();
        secondCar.setMake(CarMake.SUBARU);
        secondCar.setStatus(CarStatus.DRIVING);
        secondCar.setCurrency("EUR");
        secondCar.setPricePerKm(BigDecimal.valueOf(5));
        secondCar.setId(2L);

        Mockito.when(carRepository.findAll()).thenReturn(List.of(firstCar, secondCar));
        //when
        List<Car> carList = carServiceBasic.findAll();
        //then
        assertFalse(carList.isEmpty());
        assertEquals(2, carList.size());
    }

    @Test
    void when_delete_user_by_id_then_user_should_be_removed() {
        //given
        Car firstCar = new Car();
        firstCar.setMake(CarMake.BMW);
        firstCar.setStatus(CarStatus.READY_TO_GO);
        firstCar.setCurrency("PLN");
        firstCar.setPricePerKm(BigDecimal.valueOf(2));
        firstCar.setId(1L);

        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(firstCar));
        //when
        carServiceBasic.delete(1L);
        //then
        Mockito.verify(carRepository).deleteById(1L);
    }
}