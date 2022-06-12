package drive_me_to.security.app_user.service;

import drive_me_to.generic.service.GenericService;
import drive_me_to.security.app_user.repository.AppUser;

import java.util.Optional;

public interface AppUserService extends GenericService<AppUser, Long> {
    Optional<AppUser> findByUserLogin(String userLogin);
}
