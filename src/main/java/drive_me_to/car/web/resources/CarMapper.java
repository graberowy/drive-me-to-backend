package drive_me_to.car.web.resources;

import drive_me_to.car.repository.Car;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.web.resources.DriverDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CarMapper {

    CarDTO mapToCarDTO(Car car);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    DriverDTO mapToDriverDTO(Driver driver);

    @InheritInverseConfiguration(name = "mapToCarDTO")
    Car mapToCar(CarDTO carDTO);

    @InheritInverseConfiguration(name = "mapToDriverDTO")
    Driver mapToDriver(DriverDTO driverDTO);


}
