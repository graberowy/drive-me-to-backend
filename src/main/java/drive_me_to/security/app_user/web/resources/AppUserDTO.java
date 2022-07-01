package drive_me_to.security.app_user.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import drive_me_to.data.enums.RoleType;
import drive_me_to.generic.web.resources.DataDTO;
import drive_me_to.passenger.web.resources.PassengerDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO extends DataDTO<Long> {

    private String userLogin;
    private String userPassword;
    private Set<RoleType> roles;
    private PassengerDTO passenger;
}
