package drive_me_to.trip.web.resources;

import drive_me_to.car.repository.Car;
import drive_me_to.car.web.resources.CarDTO;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.web.resources.DriverDTO;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.web.resources.PassengerDTO;
import drive_me_to.trip.repository.Trip;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TripMapperTest {

    private final TripMapper tripMapper = new TripMapperImpl();

    @Test
    void when_use_mapToTripDTOSummary_then_summary_of_tripDTO_should_be_returned() {
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
        //when
        TripDTO tripDTO = tripMapper.mapToTripDTOSummary(tripAllDetails);
        //then
        assertEquals(tripAllDetails.getDistance(), tripDTO.getDistance());
        assertEquals(tripAllDetails.getPrice(), tripDTO.getPrice());
        assertEquals(tripAllDetails.getId(), tripDTO.getId());
        assertNull(tripDTO.getPaid());
        assertNull(tripDTO.getDriver());
        assertNull(tripDTO.getFullFinishAddress());
        assertNull(tripDTO.getFullStartAddress());
        assertNull(tripDTO.getDriver());
        assertNull(tripDTO.getStartAddress());
        assertNull(tripDTO.getFinishAddress());
        assertNull(tripDTO.getPassenger());
        assertNull(tripDTO.getPreferCarMake());
        assertNull(tripDTO.getPreferLanguage());
        assertNull(tripDTO.getPreferRouteType());
        assertNull(tripDTO.getRating());
    }

    @Test
    void when_use_mapToTripSummary_then_summary_of_trip_should_be_returned() {
        //given
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("Jan Kowalski");
        passengerDTO.setMobile("555666888");
        passengerDTO.setId(1L);

        CarDTO carDTO = new CarDTO();
        carDTO.setMake(CarMake.BMW);
        carDTO.setStatus(CarStatus.READY_TO_GO);
        carDTO.setCurrency("PLN");
        carDTO.setPricePerKm(BigDecimal.valueOf(1.5));
        carDTO.setId(1L);

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setName("Sebastian Nowak");
        driverDTO.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driverDTO.setId(1L);
        driverDTO.setCar(carDTO);

        TripDTO tripAllDetailsDTO = new TripDTO();
        tripAllDetailsDTO.setStartAddress("ogrodowa8lodz");
        tripAllDetailsDTO.setFinishAddress("przelajowa9lodz");
        tripAllDetailsDTO.setPassenger(passengerDTO);
        tripAllDetailsDTO.setId(1L);
        tripAllDetailsDTO.setPaid(false);
        tripAllDetailsDTO.setDistance(8.0);
        tripAllDetailsDTO.setPrice(BigDecimal.valueOf(12.00));
        tripAllDetailsDTO.setDriver(driverDTO);
        tripAllDetailsDTO.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripAllDetailsDTO.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");
        //when
        Trip trip = tripMapper.mapToTripSummary(tripAllDetailsDTO);
        //then
        assertEquals(tripAllDetailsDTO.getDistance(), trip.getDistance());
        assertEquals(tripAllDetailsDTO.getPrice(), trip.getPrice());
        assertEquals(tripAllDetailsDTO.getId(), trip.getId());
        assertNull(trip.getPaid());
        assertNull(trip.getDriver());
        assertNull(trip.getFullFinishAddress());
        assertNull(trip.getFullStartAddress());
        assertNull(trip.getDriver());
        assertNull(trip.getStartAddress());
        assertNull(trip.getFinishAddress());
        assertNull(trip.getPassenger());
        assertNull(trip.getPreferCarMake());
        assertNull(trip.getPreferLanguage());
        assertNull(trip.getPreferRouteType());
        assertNull(trip.getRating());
    }

    @Test
    void when_use_mapToTripDTOBasic_then_basic_of_tripDTO_should_be_returned() {
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
        //when
        TripDTO tripDTO = tripMapper.mapToTripDTOBasic(tripAllDetails);
        //then
        assertEquals(tripAllDetails.getId(), tripDTO.getId());
        assertEquals(tripAllDetails.getFullStartAddress(), tripDTO.getFullStartAddress());
        assertEquals(tripAllDetails.getFullFinishAddress(), tripDTO.getFullFinishAddress());
        assertEquals(tripAllDetails.getPrice(), tripDTO.getPrice());
        assertEquals(tripAllDetails.getDistance(), tripDTO.getDistance());
        assertEquals(tripAllDetails.getPaid(), tripDTO.getPaid());
        assertNull(tripDTO.getFinishAddress());
        assertNull(tripDTO.getStartAddress());
        assertEquals(tripAllDetails.getDriver().getName(), tripDTO.getDriver().getName());
        assertEquals(tripAllDetails.getDriver().getId(), tripDTO.getDriver().getId());
        assertEquals(tripAllDetails.getDriver().getLanguages().size(), tripDTO.getDriver().getLanguages().size());
        assertNull(tripDTO.getDriver().getCar());
        assertNull(tripDTO.getDriver().getOrders());
        assertEquals(tripAllDetails.getPassenger().getId(), tripDTO.getPassenger().getId());
        assertEquals(tripAllDetails.getPassenger().getMobile(), tripDTO.getPassenger().getMobile());
        assertEquals(tripAllDetails.getPassenger().getName(), tripDTO.getPassenger().getName());
        assertNull(tripDTO.getPassenger().getTrips());
    }

    @Test
    void when_use_mapToTripBasic_then_basic_of_trip_should_be_returned() {
        //given
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("Jan Kowalski");
        passengerDTO.setMobile("555666888");
        passengerDTO.setId(1L);

        CarDTO carDTO = new CarDTO();
        carDTO.setMake(CarMake.BMW);
        carDTO.setStatus(CarStatus.READY_TO_GO);
        carDTO.setCurrency("PLN");
        carDTO.setPricePerKm(BigDecimal.valueOf(1.5));
        carDTO.setId(1L);

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setName("Sebastian Nowak");
        driverDTO.setLanguages(Set.of(Languages.POLISH, Languages.ENGLISH));
        driverDTO.setId(1L);
        driverDTO.setCar(carDTO);

        TripDTO tripAllDetailsDTO = new TripDTO();
        tripAllDetailsDTO.setStartAddress("ogrodowa8lodz");
        tripAllDetailsDTO.setFinishAddress("przelajowa9lodz");
        tripAllDetailsDTO.setPassenger(passengerDTO);
        tripAllDetailsDTO.setId(1L);
        tripAllDetailsDTO.setPaid(false);
        tripAllDetailsDTO.setDistance(8.0);
        tripAllDetailsDTO.setPrice(BigDecimal.valueOf(12.00));
        tripAllDetailsDTO.setDriver(driverDTO);
        tripAllDetailsDTO.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripAllDetailsDTO.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");
        //when
        Trip trip = tripMapper.mapToTripBasic(tripAllDetailsDTO);
        //then
        assertEquals(tripAllDetailsDTO.getId(), trip.getId());
        assertEquals(tripAllDetailsDTO.getFullStartAddress(), trip.getFullStartAddress());
        assertEquals(tripAllDetailsDTO.getFullFinishAddress(), trip.getFullFinishAddress());
        assertEquals(tripAllDetailsDTO.getPrice(), trip.getPrice());
        assertEquals(tripAllDetailsDTO.getDistance(), trip.getDistance());
        assertEquals(tripAllDetailsDTO.getPaid(), trip.getPaid());
        assertEquals(tripAllDetailsDTO.getFinishAddress(), trip.getFinishAddress());
        assertEquals(tripAllDetailsDTO.getStartAddress(), trip.getStartAddress());
        assertEquals(tripAllDetailsDTO.getDriver().getName(), trip.getDriver().getName());
        assertEquals(tripAllDetailsDTO.getDriver().getId(), trip.getDriver().getId());
        assertEquals(tripAllDetailsDTO.getDriver().getLanguages().size(), trip.getDriver().getLanguages().size());
        assertNull(trip.getDriver().getCar());
        assertTrue(trip.getDriver().getOrders().isEmpty());
        assertEquals(tripAllDetailsDTO.getPassenger().getId(), trip.getPassenger().getId());
        assertEquals(tripAllDetailsDTO.getPassenger().getMobile(), trip.getPassenger().getMobile());
        assertEquals(tripAllDetailsDTO.getPassenger().getName(), trip.getPassenger().getName());
        assertTrue(trip.getPassenger().getTrips().isEmpty());
    }

    @Test
    void when_use_mapToTripDTODetails_then_details_of_tripDTO_should_be_returned() {
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

        Trip tripAllDetails = new Trip();
        tripAllDetails.setStartAddress("ogrodowa8lodz");
        tripAllDetails.setFinishAddress("przelajowa9lodz");
        tripAllDetails.setPassenger(passenger);
        tripAllDetails.setId(1L);
        tripAllDetails.setPaid(true);
        tripAllDetails.setDistance(8.0);
        tripAllDetails.setPrice(BigDecimal.valueOf(12.00));
        tripAllDetails.setDriver(driver);
        tripAllDetails.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripAllDetails.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");
        tripAllDetails.setRating(5);
        //when
        TripDTO tripDTO = tripMapper.mapToTripDTODetails(tripAllDetails);
        //then
        assertEquals(tripAllDetails.getId(), tripDTO.getId());
        assertEquals(tripAllDetails.getFullStartAddress(), tripDTO.getFullStartAddress());
        assertEquals(tripAllDetails.getFullFinishAddress(), tripDTO.getFullFinishAddress());
        assertEquals(tripAllDetails.getPrice(), tripDTO.getPrice());
        assertEquals(tripAllDetails.getDistance(), tripDTO.getDistance());
        assertEquals(tripAllDetails.getPaid(), tripDTO.getPaid());
        assertEquals(tripAllDetails.getFinishAddress(), tripDTO.getFinishAddress());
        assertEquals(tripAllDetails.getStartAddress(), tripDTO.getStartAddress());
        assertEquals(tripAllDetails.getDriver().getName(), tripDTO.getDriver().getName());
        assertEquals(tripAllDetails.getDriver().getId(), tripDTO.getDriver().getId());
        assertEquals(tripAllDetails.getDriver().getLanguages().size(), tripDTO.getDriver().getLanguages().size());
        assertNull(tripDTO.getDriver().getCar());
        assertNull(tripDTO.getDriver().getOrders());
        assertEquals(tripAllDetails.getPassenger().getId(), tripDTO.getPassenger().getId());
        assertEquals(tripAllDetails.getPassenger().getMobile(), tripDTO.getPassenger().getMobile());
        assertEquals(tripAllDetails.getPassenger().getName(), tripDTO.getPassenger().getName());
        assertNull(tripDTO.getPassenger().getTrips());
        assertEquals(tripAllDetails.getRating(), tripDTO.getRating());
    }

}