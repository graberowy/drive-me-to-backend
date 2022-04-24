package drive_me_to.driver.service;

import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DriverServiceTest {
    private final DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
    private final DriverServiceBasic driverServiceBasic = new DriverServiceBasic(driverRepository);

    @Test
    void when_send_request_with_valid_id_then_driver_with_provided_id_should_be_returned() {
        //given
        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));
        driver.setId(1L);

        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        //when
        Optional<Driver> driverReturned = driverServiceBasic.findById(1L);
        //then
        assertEquals("Sebastian Nowak", driverReturned.get().getName());
        assertEquals(Set.of(Languages.POLISH), driverReturned.get().getLanguages());
        assertEquals(1L, driverReturned.get().getId());
    }

    @Test
    void when_add_new_driver_than_driver_should_be_added_to_repo() {
        //given
        Driver firstDriver = new Driver();
        firstDriver.setName("Sebastian Nowak");
        firstDriver.setLanguages(Set.of(Languages.POLISH));

        Driver secondDriver = new Driver();
        secondDriver.setName("Sebastian Nowak");
        secondDriver.setLanguages(Set.of(Languages.POLISH));
        secondDriver.setId(1L);

        Mockito.when(driverRepository.save(firstDriver)).thenReturn(secondDriver);
        //when
        Driver driver = driverServiceBasic.save(firstDriver);
        //then
        assertEquals("Sebastian Nowak", driver.getName());
        assertEquals(Set.of(Languages.POLISH), driver.getLanguages());
        assertEquals(1L, driver.getId());
        Mockito.verify(driverRepository).save(firstDriver);
    }

    @Test
    void when_update_existing_driver_then_data_should_be_updated() {
        //given
        Driver firstDriver = new Driver();
        firstDriver.setName("Sebastian Nowak");
        firstDriver.setLanguages(Set.of(Languages.POLISH));
        firstDriver.setId(1L);

        Driver secondDriver = new Driver();
        secondDriver.setName("Jan Nowak");
        secondDriver.setLanguages(Set.of(Languages.GERMAN));
        secondDriver.setId(1L);

        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(firstDriver));
        Mockito.when(driverRepository.save(secondDriver)).thenReturn(secondDriver);
        //when
        Optional<Driver> driver = driverServiceBasic.update(secondDriver);
        //then
        assertEquals("Jan Nowak", driver.get().getName());
        assertEquals(Set.of(Languages.GERMAN), driver.get().getLanguages());
        assertEquals(1L, driver.get().getId());
    }

    @Test
    void when_request_find_all_than_all_drivers_should_be_returned() {
        //given
        Driver firstDriver = new Driver();
        firstDriver.setName("Sebastian Nowak");
        firstDriver.setLanguages(Set.of(Languages.POLISH));
        firstDriver.setId(1L);

        Driver secondDriver = new Driver();
        secondDriver.setName("Jan Nowak");
        secondDriver.setLanguages(Set.of(Languages.GERMAN));
        secondDriver.setId(2L);

        Mockito.when(driverRepository.findAll()).thenReturn(List.of(firstDriver, secondDriver));
        //when
        List<Driver> driverList = driverServiceBasic.findAll();
        //then
        assertFalse(driverList.isEmpty());
        assertEquals(2, driverList.size());
    }

    @Test
    void when_delete_user_by_id_then_user_should_be_removed() {
        //given
        Driver firstDriver = new Driver();
        firstDriver.setName("Sebastian Nowak");
        firstDriver.setLanguages(Set.of(Languages.POLISH));
        firstDriver.setId(1L);

        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(firstDriver));
        //when
        driverServiceBasic.delete(1L);
        //then
        Mockito.verify(driverRepository).deleteById(1L);
    }
}