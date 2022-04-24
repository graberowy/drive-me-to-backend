package drive_me_to.trip.web.controller;

import drive_me_to.car.repository.Car;
import drive_me_to.client.CalculateRoute;
import drive_me_to.client.GeoCode;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.trip.repository.Trip;
import drive_me_to.trip.service.TripServiceBasic;
import drive_me_to.trip.web.resources.TripMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TripController.class)
@ContextConfiguration(classes = {TripMapperImpl.class, TripController.class})
class TripControllerTest {

    @MockBean
    private TripServiceBasic tripServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_post_request_which_has_no_existing_trip_then_trip_should_be_returned_as_response() throws Exception {
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
        driver.setLanguages(Set.of(Languages.POLISH));
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

        Mockito.when(tripServiceBasic.save(any())).thenReturn(tripAllDetails);
        //when
        //then
        mockMvc.perform(post("/api/v1/trips/")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"startAddress\": \"ogrodowa8lodz\",\n" +
                                "  \"finishAddress\": \"przelajowa9lodz\",\n" +
                                "  \"passenger\": { \"id\": 1 }\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.startAddress").doesNotExist())
                .andExpect(jsonPath("$.finishAddress").doesNotExist())
                .andExpect(jsonPath("$.passenger.id", equalTo(1)))
                .andExpect(jsonPath("$.paid", equalTo(false)))
                .andExpect(jsonPath("$.fullStartAddress", equalTo("ulica Ogrodowa 8, 91-062 Łódź, Polska")))
                .andExpect(jsonPath("$.fullFinishAddress", equalTo("ulica Przełajowa 9, 94-045 Łódź, Polska")))
                .andExpect(jsonPath("$.distance", equalTo(8.0)))
                .andExpect(jsonPath("$.price", equalTo(12.00)))
                .andExpect(jsonPath("$.driver.id", equalTo(1)))
                .andExpect(jsonPath("$.driver.name", equalTo("Sebastian Nowak")))
                .andExpect(jsonPath("$.driver.languages").isNotEmpty());
    }

    @Test
    void when_send_summary_get_request_by_id_with_existing_trip_then_trip_summary_should_be_returned() throws Exception {
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

        Mockito.when(tripServiceBasic.findById(1L)).thenReturn(Optional.of(tripAllDetails));
        //when
        //then
        mockMvc.perform(get("/api/v1/trips/1/summary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.distance", equalTo(8.0)))
                .andExpect(jsonPath("$.price", equalTo(12.00)));
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(tripServiceBasic.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/trips/1/summary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_put_request_which_existing_id_then_data_should_be_updated() throws Exception {
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

        Mockito.when(tripServiceBasic.setPayAndGrade(1L, true, 5)).thenReturn(Optional.of(tripToUpdate));
        //when
        //then
        mockMvc.perform(put("/api/v1/trips/1/paid/true/rating/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.paid", equalTo(true)))
                .andExpect(jsonPath("$.rating", equalTo(5)));
    }

    @Test
    void when_send_put_request_which_not_existing_id_then_not_found_error_should_be_returned() throws Exception {
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
        tripToUpdate.setId(2L);
        tripToUpdate.setPaid(true);
        tripToUpdate.setDistance(8.0);
        tripToUpdate.setPrice(BigDecimal.valueOf(12.00));
        tripToUpdate.setDriver(driver);
        tripToUpdate.setFullStartAddress("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        tripToUpdate.setFullFinishAddress("ulica Przełajowa 9, 94-045 Łódź, Polska");
        tripToUpdate.setRating(5);

        Mockito.when(tripServiceBasic.setPayAndGrade(1L, true, 5)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(put("/api/v1/trips/1/paid/true/rating/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

}