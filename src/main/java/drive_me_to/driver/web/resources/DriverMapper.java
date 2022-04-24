package drive_me_to.driver.web.resources;

import drive_me_to.car.repository.Car;
import drive_me_to.car.web.resources.CarDTO;
import drive_me_to.driver.repository.Driver;
import drive_me_to.trip.repository.Trip;
import drive_me_to.trip.web.resources.TripDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DriverMapper {


    DriverDTO mapToDriverDTO(Driver driver);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "currentLocation", target = "currentLocation")
    @Mapping(source = "pricePerKm", target = "pricePerKm")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "make", target = "make")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    CarDTO mapToCarDTO(Car car);

    @Mapping(source = "id", target = "id")
    @BeanMapping(ignoreByDefault = true)
    TripDTO mapToTripDTO(Trip trip);

    @InheritInverseConfiguration(name = "mapToDriverDTO")
    Driver mapToDriver(DriverDTO driverDTO);

    @InheritInverseConfiguration(name = "mapToCarDTO")
    Car mapToCar(CarDTO carDTO);

    @InheritInverseConfiguration(name = "mapToTripDTO")
    Trip mapToTrip(TripDTO tripDTO);


}
