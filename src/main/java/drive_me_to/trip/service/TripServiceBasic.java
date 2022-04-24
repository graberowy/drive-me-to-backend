package drive_me_to.trip.service;

import drive_me_to.client.CalculateRoute;
import drive_me_to.client.HEREMapsAPI;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.Languages;
import drive_me_to.data.enums.RouteType;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.service.DriverServiceBasic;
import drive_me_to.generic.service.GenericServiceBasic;
import drive_me_to.passenger.service.PassengerServiceBasic;
import drive_me_to.trip.repository.Trip;
import drive_me_to.trip.repository.TripRepository;
import drive_me_to.passenger.repository.Passenger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TripServiceBasic extends GenericServiceBasic<Trip, Long> implements TripService {
    private final PassengerServiceBasic passengerServiceBasic;
    private final DriverServiceBasic driverServiceBasic;
    private final TripRepository tripRepository;
    private final HEREMapsAPI hereMapsAPI;

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param tripRepository   specified type interface
     * @param passengerServiceBasic
     * @param driverServiceBasic
     * @param hereMapsAPI
     */
    public TripServiceBasic(TripRepository tripRepository, PassengerServiceBasic passengerServiceBasic, DriverServiceBasic driverServiceBasic, HEREMapsAPI hereMapsAPI) {
        super(tripRepository);
        this.passengerServiceBasic = passengerServiceBasic;
        this.tripRepository = tripRepository;
        this.driverServiceBasic = driverServiceBasic;
        this.hereMapsAPI = hereMapsAPI;
    }


    @Override
    public Trip save(Trip trip) {
        Optional<Passenger> passenger = passengerServiceBasic.findById(trip.getPassenger().getId());
        if (passenger.isPresent()) {
            CalculateRoute calculateRoute = hereMapsAPI.getRoute(trip.getStartAddress(),
                    trip.getFinishAddress(),
                    setRouteOption(trip.getPreferRouteType()
                    ).toString());
            return tripRepository.save(setTripDetails(trip, calculateRoute));
        } else throw new IllegalArgumentException("no passenger present");
    }


    private Trip setTripDetails(Trip trip, CalculateRoute calculateRoute) {
        trip.setId(null);
        trip.setPaid(false);
        trip.setDistance(getDistanceInKm(calculateRoute));
        trip.setFullStartAddress(getFullAddress(trip.getStartAddress()));
        trip.setFullFinishAddress(getFullAddress(trip.getFinishAddress()));
        machDriverByLanguageAndCarMake(trip.getPreferLanguage(), trip.getPreferCarMake(), trip);
        trip.setPrice(calculateTripPrice(trip.getDriver(), trip));
        return trip;
    }

    private RouteType setRouteOption(RouteType mode) {
        return mode != null ? mode : RouteType.FAST;
    }

    private Double getDistanceInKm(CalculateRoute calculateRoute) {
        return (double) (calculateRoute
                .getRoutes()
                .get(0)
                .getSections()
                .get(0)
                .getSummary()
                .getLength()
                / 1000);
    }

    private String getFullAddress(String address) {
        return hereMapsAPI.getLocalization(address)
                .getLocation()
                .get(0)
                .getAddressDetails();
    }

    private void machDriverByLanguageAndCarMake(Languages language, CarMake carMake, Trip trip) {
        if (language != null && carMake != null) {
            driverServiceBasic.findAll()
                    .stream()
                    .filter(driver -> driver.getCar().getMake().equals(carMake))
                    .filter(driver -> driver.getLanguages().contains(language))
                    .findFirst()
                    .ifPresent(trip::setDriver);
        } else if (language == null && carMake != null) {
            driverServiceBasic.findAll()
                    .stream()
                    .filter(driver -> driver.getCar().getMake().equals(carMake))
                    .filter(driver -> driver.getLanguages().contains(Languages.ENGLISH))
                    .findFirst()
                    .ifPresent(trip::setDriver);
        } else if (language != null) {
            driverServiceBasic.findAll()
                    .stream()
                    .filter(driver -> driver.getLanguages().contains(language))
                    .findFirst()
                    .ifPresent(trip::setDriver);
        } else {
            driverServiceBasic.findAll()
                    .stream()
                    .filter(driver -> driver.getLanguages().contains(Languages.ENGLISH))
                    .findFirst()
                    .ifPresent(trip::setDriver);
        }

    }

    private BigDecimal calculateTripPrice(Driver driver, Trip trip) {
        return driver.getCar().getPricePerKm()
                .multiply(BigDecimal.valueOf(trip.getDistance()));
    }

    @Override
    public Optional<Trip> setPayAndGrade(Long id, Boolean status, Integer grade) {
        return tripRepository.findById(id)
                .map(trip -> changePaidStatus(status, trip))
                .filter(trip -> trip.getPaid().equals(true))
                .map(trip -> changeRating(grade, trip))
                .map(tripRepository::save);


    }

    private Trip changePaidStatus(Boolean status, Trip trip) {
        trip.setPaid(status);
        return trip;
    }

    private Trip changeRating(Integer grade, Trip trip) {
        trip.setRating(grade);
        return trip;
    }
}
