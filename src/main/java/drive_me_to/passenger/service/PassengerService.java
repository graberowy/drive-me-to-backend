package drive_me_to.passenger.service;

import drive_me_to.generic.service.GenericService;
import drive_me_to.passenger.repository.Passenger;

import java.util.Optional;

/**
 * This interface extends service methods on passenger
 */
public interface PassengerService extends GenericService<Passenger, Long> {
    Optional<Passenger> findByMobile(String mobile);
    Optional<Passenger> partialUpdate(Passenger passenger);
}
