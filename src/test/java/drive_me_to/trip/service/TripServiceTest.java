package drive_me_to.trip.service;

import drive_me_to.car.repository.Car;
import drive_me_to.client.CalculateRoute;
import drive_me_to.client.GeoCode;
import drive_me_to.client.HEREMapsAPI;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.data.enums.RouteType;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.service.DriverServiceBasic;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.service.PassengerServiceBasic;
import drive_me_to.trip.repository.Trip;
import drive_me_to.trip.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

class TripServiceTest {
    private final TripRepository tripRepository = Mockito.mock(TripRepository.class);
    private final PassengerServiceBasic passengerServiceBasic = Mockito.mock(PassengerServiceBasic.class);
    private final DriverServiceBasic driverServiceBasic = Mockito.mock(DriverServiceBasic.class);
    private final HEREMapsAPI hereMapsAPI = Mockito.mock(HEREMapsAPI.class);
    private final TripServiceBasic tripServiceBasic = new TripServiceBasic(tripRepository, passengerServiceBasic, driverServiceBasic, hereMapsAPI);

    @Test
    void when_add_new_trip_with_valid_details_new_record_should_be_added() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Trip trip = new Trip();
        trip.setStartAddress("ogrodowa8lodz");
        trip.setFinishAddress("przelajowa9lodz");
        trip.setPassenger(passenger);
        trip.setId(1L);

        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(1.5));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driver.setId(1L);
        driver.setCar(car);

        GeoCode.Coordinates coordinatesStart = new GeoCode.Coordinates();
        coordinatesStart.setLat((float) 51.77857);
        coordinatesStart.setLng((float) 19.45245);

        GeoCode.Position positionStart = new GeoCode.Position();
        positionStart.setAddressDetails("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        positionStart.setCoordinates(coordinatesStart);

        GeoCode geoCodeStart = new GeoCode();
        geoCodeStart.setLocation(List.of(positionStart));

        GeoCode.Coordinates coordinatesFinish = new GeoCode.Coordinates();
        coordinatesFinish.setLat((float) 51.74482);
        coordinatesFinish.setLng((float) 19.40752);

        GeoCode.Position positionFinish = new GeoCode.Position();
        positionFinish.setAddressDetails("ulica Przełajowa 9, 94-045 Łódź, Polska");
        positionFinish.setCoordinates(coordinatesFinish);

        GeoCode geoCodeFinish = new GeoCode();
        geoCodeFinish.setLocation(List.of(positionFinish));


        CalculateRoute.Summary summary = new CalculateRoute.Summary();
        summary.setLength(8119L);

        CalculateRoute.Transport transport = new CalculateRoute.Transport();
        transport.setMode("car");

        CalculateRoute.Section section = new CalculateRoute.Section();
        section.setTransport(transport);
        section.setSummary(summary);

        CalculateRoute.Route route = new CalculateRoute.Route();
        route.setSections(List.of(section));

        CalculateRoute calculateRoute = new CalculateRoute();
        calculateRoute.setRoutes(List.of(route));

        Trip tripAllDetails = new Trip();
        tripAllDetails.setStartAddress("ogrodowa8lodz");
        tripAllDetails.setFinishAddress("przelajowa9lodz");
        tripAllDetails.setPassenger(passenger);
        tripAllDetails.setId(1L);
        tripAllDetails.setPaid(false);
        tripAllDetails.setDistance(8.0);
        tripAllDetails.setPrice(BigDecimal.valueOf(12.00));
        tripAllDetails.setDriver(driver);
        tripAllDetails.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripAllDetails.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");

        Mockito.when(passengerServiceBasic.findById(1L)).thenReturn(Optional.of(passenger));
        Mockito.when(passengerServiceBasic.findById(1L)).thenReturn(Optional.of(passenger));
        Mockito.when(hereMapsAPI.getLocalization(trip.getStartAddress())).thenReturn(geoCodeStart);
        Mockito.when(hereMapsAPI.getLocalization(trip.getFinishAddress())).thenReturn(geoCodeFinish);
        Mockito.when(hereMapsAPI.getRoute(trip.getStartAddress(), trip.getFinishAddress(), RouteType.FAST.toString()))
                .thenReturn(calculateRoute);
        Mockito.when(driverServiceBasic.findAll()).thenReturn(List.of(driver));
        Mockito.when(tripRepository.save(trip)).thenReturn(tripAllDetails);
        //when
        Trip tripReturned = tripServiceBasic.save(trip);
        //then
        assertEquals(1, tripReturned.getId());
        assertEquals("ulica Ogrodowa 8, 91-062 Łódź, Polska", tripReturned.getFullStartAddress());
        assertEquals("ulica Przełajowa 9, 94-045 Łódź, Polska", tripReturned.getFullFinishAddress());
        assertEquals(BigDecimal.valueOf(12.00), tripReturned.getPrice());
        assertEquals(8.0, tripReturned.getDistance());
        assertEquals(driver, tripReturned.getDriver());
        assertEquals(false, tripReturned.getPaid());
    }

    @Test
    void when_setPayAndGrade_on_existing_trip_then_data_should_be_updated() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(1.5));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driver.setId(1L);
        driver.setCar(car);


        Trip firstTrip = new Trip();
        firstTrip.setStartAddress("ogrodowa8lodz");
        firstTrip.setFinishAddress("przelajowa9lodz");
        firstTrip.setPassenger(passenger);
        firstTrip.setId(1L);
        firstTrip.setPaid(false);
        firstTrip.setDistance(8.0);
        firstTrip.setPrice(BigDecimal.valueOf(12.00));
        firstTrip.setDriver(driver);
        firstTrip.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        firstTrip.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");

        Trip tripToUpdate = new Trip();
        tripToUpdate.setStartAddress("ogrodowa8lodz");
        tripToUpdate.setFinishAddress("przelajowa9lodz");
        tripToUpdate.setPassenger(passenger);
        tripToUpdate.setId(1L);
        tripToUpdate.setPaid(true);
        tripToUpdate.setDistance(8.0);
        tripToUpdate.setPrice(BigDecimal.valueOf(12.00));
        tripToUpdate.setDriver(driver);
        tripToUpdate.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripToUpdate.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");
        tripToUpdate.setRating(5);

        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(firstTrip));
        Mockito.when(tripRepository.save(any())).thenReturn(tripToUpdate);
        //when
        Optional<Trip> trip = tripServiceBasic.setPayAndGrade(1L, true, 5);
        //then
        assertEquals(tripToUpdate, trip.get());
    }

    @Test
    void when_send_request_with_valid_id_then_trip_with_provided_id_should_be_returned() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(1.5));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driver.setId(1L);
        driver.setCar(car);
        Trip firstTrip = new Trip();
        firstTrip.setStartAddress("ogrodowa8lodz");
        firstTrip.setFinishAddress("przelajowa9lodz");
        firstTrip.setPassenger(passenger);
        firstTrip.setId(1L);
        firstTrip.setPaid(false);
        firstTrip.setDistance(8.0);
        firstTrip.setPrice(BigDecimal.valueOf(12.00));
        firstTrip.setDriver(driver);
        firstTrip.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        firstTrip.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");

        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(firstTrip));
        //when
        Optional<Trip> trip = tripServiceBasic.findById(1L);
        //then
        assertEquals(firstTrip, trip.get());

    }

    @Test
    void when_request_find_all_than_all_trips_should_be_returned() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(1.5));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driver.setId(1L);
        driver.setCar(car);
        Trip firstTrip = new Trip();
        firstTrip.setStartAddress("ogrodowa8lodz");
        firstTrip.setFinishAddress("przelajowa9lodz");
        firstTrip.setPassenger(passenger);
        firstTrip.setId(1L);
        firstTrip.setPaid(false);
        firstTrip.setDistance(8.0);
        firstTrip.setPrice(BigDecimal.valueOf(12.00));
        firstTrip.setDriver(driver);
        firstTrip.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        firstTrip.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");

        Mockito.when(tripRepository.findAll()).thenReturn(List.of(firstTrip));
        //when
        List<Trip> tripList = tripServiceBasic.findAll();
        //then
        assertFalse(tripList.isEmpty());
        assertEquals(1, tripList.size());
    }

    @Test
    void when_delete_user_by_id_then_user_should_be_removed() {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(1.5));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driver.setId(1L);
        driver.setCar(car);
        Trip firstTrip = new Trip();
        firstTrip.setStartAddress("ogrodowa8lodz");
        firstTrip.setFinishAddress("przelajowa9lodz");
        firstTrip.setPassenger(passenger);
        firstTrip.setId(1L);
        firstTrip.setPaid(false);
        firstTrip.setDistance(8.0);
        firstTrip.setPrice(BigDecimal.valueOf(12.00));
        firstTrip.setDriver(driver);
        firstTrip.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        firstTrip.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");

        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(firstTrip));
        //when
        tripServiceBasic.delete(1L);
        //then
        Mockito.verify(tripRepository).deleteById(1L);
    }
}