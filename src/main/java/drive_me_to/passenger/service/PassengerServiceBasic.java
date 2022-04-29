package drive_me_to.passenger.service;

import drive_me_to.generic.service.GenericServiceBasic;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerServiceBasic extends GenericServiceBasic<Passenger, Long> implements PassengerService {

    private final PassengerRepository passengerRepository;
    /**
     * This is constructor for use generic operations on specific type
     *
     * @param passengerRepository specified type interface
     */
    public PassengerServiceBasic(PassengerRepository passengerRepository) {
        super(passengerRepository);
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Optional<Passenger> findByMobile(String mobile) {
        return passengerRepository.findByMobile(mobile);
    }

    @Override
    public Optional<Passenger> partialUpdate(Passenger passenger) {
        return passengerRepository.findById(passenger.getId())
                .map(passengerToUpdate -> {
                    if (passenger.getName() != null) passengerToUpdate.setName(passenger.getName());
                    if (passenger.getMobile() != null) passengerToUpdate.setMobile(passenger.getMobile());
                    return passengerToUpdate;
                })
                .map(passengerRepository::save);
    }
}
