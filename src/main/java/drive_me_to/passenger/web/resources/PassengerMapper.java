package drive_me_to.passenger.web.resources;

import drive_me_to.passenger.repository.Passenger;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PassengerMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mobile", target = "mobile")
    @BeanMapping(ignoreByDefault = true)
    PassengerDTO mapToPassengerDTO(Passenger passenger);

    @InheritInverseConfiguration(name = "mapToPassengerDTO")
    Passenger mapToPassenger(PassengerDTO passengerDTO);
}
