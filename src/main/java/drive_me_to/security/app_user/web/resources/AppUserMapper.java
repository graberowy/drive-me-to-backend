package drive_me_to.security.app_user.web.resources;

import drive_me_to.security.app_user.repository.AppUser;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AppUserMapper {

    AppUserDTO mapToAppUserDTO(AppUser appUser);

    @InheritInverseConfiguration(name = "mapToAppUserDTO")
    AppUser mapToAppUser(AppUserDTO appUserDTO);
}
