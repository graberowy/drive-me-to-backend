package drive_me_to.passenger.web.resources;

import drive_me_to.passenger.repository.Passenger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerMapperTest {

    private final PassengerMapper passengerMapper = new PassengerMapperImpl();

    @Test
    void when_use_mapToPassengerDTO_then_passengerDTO_should_be_returned() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);
        //when
        PassengerDTO passengerDTO = passengerMapper.mapToPassengerDTO(passenger);
        //then
        assertEquals(passenger.getName(), passengerDTO.getName());
        assertEquals(passenger.getMobile(), passengerDTO.getMobile());
        assertEquals(passenger.getId(), passengerDTO.getId());
        assertNull(passengerDTO.getTrips());
    }

    @Test
    void when_use_mapToPassenger_then_passenger_should_be_returned() {
        //given
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setMobile("111222333");
        passengerDTO.setId(1L);
        passengerDTO.setName("Jan Kowalski");
        //when
        Passenger passenger = passengerMapper.mapToPassenger(passengerDTO);
        //then
        assertEquals(passengerDTO.getMobile(), passenger.getMobile());
        assertEquals(passengerDTO.getName(), passenger.getName());
        assertEquals(passengerDTO.getId(), passenger.getId());
        assertTrue(passenger.getTrips().isEmpty());
    }

}