package drive_me_to.trip.web.resources;

import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.Languages;
import drive_me_to.data.enums.RouteType;
import drive_me_to.driver.web.resources.DriverDTO;
import drive_me_to.generic.web.resources.DataDTO;
import drive_me_to.passenger.web.resources.PassengerDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripDTO extends DataDTO<Long> {

    private String startAddress;
    private String finishAddress;
    private String fullStartAddress;
    private String fullFinishAddress;
    private Languages preferLanguage;
    private CarMake preferCarMake;
    private RouteType preferRouteType;
    private Boolean paid;
    private BigDecimal price;
    private Double distance;
    private PassengerDTO passenger;
    private DriverDTO driver;
    private Integer rating;
}
