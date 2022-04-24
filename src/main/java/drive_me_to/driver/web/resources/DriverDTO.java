package drive_me_to.driver.web.resources;

import drive_me_to.car.web.resources.CarDTO;
import drive_me_to.data.enums.Languages;
import drive_me_to.generic.web.resources.DataDTO;
import drive_me_to.trip.web.resources.TripDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO extends DataDTO<Long> {

    private String name;
    private Set<Languages> languages;
    private CarDTO car;
    private List<TripDTO> orders;
}
