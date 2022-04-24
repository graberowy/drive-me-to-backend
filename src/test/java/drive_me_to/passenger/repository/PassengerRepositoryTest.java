package drive_me_to.passenger.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void when_add_new_passenger_with_no_id_then_new_record_should_be_added() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(null);

        passengerRepository.save(passenger);
        //when
        List<Passenger> passengerList = passengerRepository.findAll();
        //then
        assertEquals(1, passengerList.size());
        assertNotNull(passengerList.get(0).getId());
    }

    @Test
    void when_delete_existing_passenger_then_record_should_be_removed() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(null);


        passengerRepository.save(passenger);
        passengerRepository.delete(passenger);
        //when
        List<Passenger> passengerList = passengerRepository.findAll();
        //then
        assertTrue(passengerList.isEmpty());
    }


}