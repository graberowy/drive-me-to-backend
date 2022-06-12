package drive_me_to.security.app_user.service;

import drive_me_to.generic.service.GenericServiceBasic;
import drive_me_to.security.app_user.repository.AppUser;
import drive_me_to.security.app_user.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceBasic extends GenericServiceBasic<AppUser, Long> implements AppUserService {

    private final AppUserRepository appUserRepository;

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param appUserRepository specified type interface
     */
    public AppUserServiceBasic(AppUserRepository appUserRepository) {
        super(appUserRepository);
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Optional<AppUser> findByUserLogin(String userLogin) {
        return appUserRepository.findByUserLogin(userLogin);
    }
}
