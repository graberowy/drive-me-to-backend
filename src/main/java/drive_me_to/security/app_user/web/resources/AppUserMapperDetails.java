package drive_me_to.security.app_user.web.resources;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.web.resources.PassengerDTO;
import drive_me_to.security.app_user.repository.AppUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AppUserMapperDetails {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userLogin", target = "userLogin")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "passenger", target = "passenger")
    @BeanMapping(ignoreByDefault = true)
    AppUserDTO mapToAppUserDTODetails(AppUser appUser);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mobile", target = "mobile")
    @BeanMapping(ignoreByDefault = true)
    PassengerDTO mapToPassengerDTO(Passenger passenger);

    @InheritInverseConfiguration(name = "mapToPassengerDTO")
    Passenger mapToPassenger(PassengerDTO passengerDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userLogin", target = "userLogin")
    @Mapping(source = "userPassword", target = "userPassword")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "passenger", target = "passenger")
    @BeanMapping(ignoreByDefault = true)
    AppUser mapToAppUserDetails(AppUserDTO appUserDTO);
}
