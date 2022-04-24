package drive_me_to.passenger.web.resources;

import drive_me_to.generic.web.resources.DataDTO;
import drive_me_to.trip.web.resources.TripDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerDTO extends DataDTO<Long> {
    private String name;
    private String mobile;
    private List<TripDTO> trips;
}
