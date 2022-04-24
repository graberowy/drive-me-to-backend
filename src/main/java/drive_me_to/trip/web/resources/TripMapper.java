package drive_me_to.trip.web.resources;

import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.web.resources.DriverDTO;
import drive_me_to.trip.repository.Trip;
import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.web.resources.PassengerDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TripMapper {

    TripDTO mapToTripDTODetails(Trip trip);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "distance", target = "distance")
    @BeanMapping(ignoreByDefault = true)
    TripDTO mapToTripDTOSummary(Trip trip);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullStartAddress", target = "fullStartAddress")
    @Mapping(source = "fullFinishAddress", target = "fullFinishAddress")
    @Mapping(source = "preferLanguage", target = "preferLanguage")
    @Mapping(source = "preferCarMake", target = "preferCarMake")
    @Mapping(source = "preferRouteType", target = "preferRouteType")
    @Mapping(source = "paid", target = "paid")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "distance", target = "distance")
    @Mapping(source = "passenger", target = "passenger")
    @Mapping(source = "driver", target = "driver")
    @BeanMapping(ignoreByDefault = true)
    TripDTO mapToTripDTOBasic(Trip trip);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "startAddress", target = "startAddress")
    @Mapping(source = "finishAddress", target = "finishAddress")
    @Mapping(source = "fullStartAddress", target = "fullStartAddress")
    @Mapping(source = "fullFinishAddress", target = "fullFinishAddress")
    @Mapping(source = "preferLanguage", target = "preferLanguage")
    @Mapping(source = "preferCarMake", target = "preferCarMake")
    @Mapping(source = "preferRouteType", target = "preferRouteType")
    @Mapping(source = "paid", target = "paid")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "distance", target = "distance")
    @Mapping(source = "passenger", target = "passenger")
    @Mapping(source = "driver", target = "driver")
    @BeanMapping(ignoreByDefault = true)
    Trip mapToTripBasic(TripDTO tripDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mobile", target = "mobile")
    @BeanMapping(ignoreByDefault = true)
    PassengerDTO mapToPassengerDTO(Passenger passenger);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "languages", target = "languages")
    @BeanMapping(ignoreByDefault = true)
    DriverDTO mapToDriverDTO(Driver driver);

    @InheritInverseConfiguration(name = "mapToTripDTODetails")
    Trip mapToTripDetails(TripDTO tripDTO);

    @InheritInverseConfiguration(name = "mapToTripDTOSummary")
    Trip mapToTripSummary(TripDTO tripDTO);

    @InheritInverseConfiguration(name = "mapToPassengerDTO")
    Passenger mapToPassenger(PassengerDTO passengerDTO);

    @InheritInverseConfiguration(name = "mapToDriverDTO")
    Driver mapToDriver(DriverDTO driverDTO);

}
