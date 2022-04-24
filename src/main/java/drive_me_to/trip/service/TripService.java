package drive_me_to.trip.service;

import drive_me_to.generic.service.GenericService;
import drive_me_to.trip.repository.Trip;

import java.util.Optional;

public interface TripService extends GenericService<Trip, Long> {
    Optional<Trip> setPayAndGrade(Long id, Boolean status, Integer grade);
}
