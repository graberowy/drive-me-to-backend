package drive_me_to.car.web.resources;

import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.CarMake;
import drive_me_to.driver.web.resources.DriverDTO;
import drive_me_to.generic.web.resources.DataDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO extends DataDTO<Long> {

    private String currentLocation;
    private BigDecimal pricePerKm;
    private String currency;
    private CarMake make;
    private CarStatus status;
    private DriverDTO driver;
}
