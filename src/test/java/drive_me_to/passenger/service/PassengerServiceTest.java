package drive_me_to.passenger.service;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PassengerServiceTest {
    private final PassengerRepository passengerRepository = Mockito.mock(PassengerRepository.class);
    private final PassengerServiceBasic passengerServiceBasic = new PassengerServiceBasic(passengerRepository);

    @Test
    void when_send_request_with_valid_id_then_passenger_with_provided_id_should_be_returned() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);
        Mockito.when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        //when
        Optional<Passenger> passengerReturned = passengerServiceBasic.findById(1L);
        //then
        assertEquals("Jan Kowalski", passengerReturned.get().getName());
        assertEquals("555666888", passengerReturned.get().getMobile());
        assertEquals(1L, passengerReturned.get().getId());
    }

    @Test
    void when_add_new_passenger_than_passenger_should_be_added_to_repo() {
        //given
        Passenger firstPassenger = new Passenger();
        firstPassenger.setName("Jan Kowalski");
        firstPassenger.setMobile("555666888");

        Passenger secondPassenger = new Passenger();
        secondPassenger.setName("Jan Kowalski");
        secondPassenger.setMobile("555666888");
        secondPassenger.setId(1L);

        Mockito.when(passengerRepository.save(firstPassenger)).thenReturn(secondPassenger);
        //when
        Passenger passenger = passengerServiceBasic.save(firstPassenger);
        //then
        assertEquals("Jan Kowalski", passenger.getName());
        assertEquals("555666888", passenger.getMobile());
        assertEquals(1L, passenger.getId());
        Mockito.verify(passengerRepository).save(firstPassenger);
    }

    @Test
    void when_update_existing_user_then_data_should_be_updated() {
        //given
        Passenger firstPassenger = new Passenger();
        firstPassenger.setName("Jan Kowalski");
        firstPassenger.setMobile("555666888");
        firstPassenger.setId(1L);

        Passenger secondPassenger = new Passenger();
        secondPassenger.setName("Sebastian Nowak");
        secondPassenger.setMobile("111222333");
        secondPassenger.setId(1L);

        Mockito.when(passengerRepository.findById(1L)).thenReturn(Optional.of(firstPassenger));
        Mockito.when(passengerRepository.save(secondPassenger)).thenReturn(secondPassenger);
        //when
        Optional<Passenger> passengerUpdated = passengerServiceBasic.update(secondPassenger);
        //then
        assertEquals("Sebastian Nowak", passengerUpdated.get().getName());
        assertEquals("111222333", passengerUpdated.get().getMobile());
        assertEquals(1L, passengerUpdated.get().getId());
    }

    @Test
    void when_request_find_all_than_all_passengers_should_be_returned() {
        //given
        Passenger firstPassenger = new Passenger();
        firstPassenger.setName("Jan Kowalski");
        firstPassenger.setMobile("555666888");
        firstPassenger.setId(1L);

        Passenger secondPassenger = new Passenger();
        secondPassenger.setName("Sebastian Nowak");
        secondPassenger.setMobile("111222333");
        secondPassenger.setId(2L);

        Mockito.when(passengerRepository.findAll()).thenReturn(List.of(firstPassenger, secondPassenger));
        //when
        List<Passenger> passengerList = passengerServiceBasic.findAll();
        //then
        assertFalse(passengerList.isEmpty());
        assertEquals(2, passengerList.size());
    }

    @Test
    void when_delete_passenger_by_id_then_user_should_be_removed() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Mockito.when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        //when
        passengerServiceBasic.delete(1L);
        //then
        Mockito.verify(passengerRepository).deleteById(1L);
    }
}