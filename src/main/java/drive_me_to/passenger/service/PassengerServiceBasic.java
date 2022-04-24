package drive_me_to.passenger.service;

import drive_me_to.generic.service.GenericServiceBasic;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.repository.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceBasic extends GenericServiceBasic<Passenger, Long> implements PassengerService {
    /**
     * This is constructor for use generic operations on specific type
     *
     * @param passengerRepository specified type interface
     */
    public PassengerServiceBasic(PassengerRepository passengerRepository) {
        super(passengerRepository);
    }
}
