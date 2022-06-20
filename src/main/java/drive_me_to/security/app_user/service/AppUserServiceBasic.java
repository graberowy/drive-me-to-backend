package drive_me_to.security.app_user.service;

import drive_me_to.generic.service.GenericServiceBasic;
import drive_me_to.security.app_user.repository.AppUser;
import drive_me_to.security.app_user.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class AppUserServiceBasic extends GenericServiceBasic<AppUser, Long> implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param appUserRepository specified type interface
     * @param passwordEncoder
     */
    public AppUserServiceBasic(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        super(appUserRepository);
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUser> findByUserLogin(String userLogin) {
        return appUserRepository.findByUserLogin(userLogin);
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUserLogin(userLogin);
        if (appUser.isPresent()) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            appUser.get().getRoles().forEach(roleType -> {
                authorities.add(new SimpleGrantedAuthority(roleType.toString()));
            });
            return new User(appUser.get().getUserLogin(), appUser.get().getUserPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found in database");
        }

    }

    @Override
    public AppUser save(AppUser appUser) {
        appUser.setId(null);
        appUser.setUserPassword(passwordEncoder.encode(appUser.getUserPassword()));
        return appUserRepository.save(appUser);
    }
}
