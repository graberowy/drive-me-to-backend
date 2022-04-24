package drive_me_to.trip.repository;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TripRepositoryTest {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void when_add_new_trip_no_id_then_new_record_should_be_added() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(null);

        passengerRepository.save(passenger);

        Trip trip = new Trip();
        trip.setStartAddress("ogrodowa8lodz");
        trip.setFinishAddress("przelajowa9lodz");
        trip.setPassenger(passenger);

        tripRepository.save(trip);
        //when
        List<Trip> tripList = tripRepository.findAll();
        //then
        assertEquals(1, tripList.size());
    }

    @Test
    void when_add_new_trip_without_passenger_details_record_should_not_be_added() {
        Trip trip = new Trip();
        trip.setStartAddress("ogrodowa8lodz");
        trip.setFinishAddress("przelajowa9lodz");

        //when
        //then
        assertThrows(ConstraintViolationException.class, () -> {
            tripRepository.save(trip);
        });
    }

    @Test
    void when_delete_existing_trip_then_record_should_be_removed_without_passenger() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(null);

        passengerRepository.save(passenger);

        Trip trip = new Trip();
        trip.setStartAddress("ogrodowa8lodz");
        trip.setFinishAddress("przelajowa9lodz");
        trip.setPassenger(passenger);

        tripRepository.save(trip);
        tripRepository.delete(trip);
        //when
        List<Trip> tripList = tripRepository.findAll();
        List<Passenger> passengerList = passengerRepository.findAll();
        //then
        assertEquals(1, passengerList.size());
        assertTrue(tripList.isEmpty());
    }

}