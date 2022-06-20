package drive_me_to.security.app_user.web.controller;

import drive_me_to.security.app_user.repository.AppUser;
import drive_me_to.security.app_user.service.AppUserServiceBasic;
import drive_me_to.security.app_user.web.resources.AppUserDTO;
import drive_me_to.security.app_user.web.resources.AppUserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserServiceBasic appUserServiceBasic;
    private final AppUserMapper appUserMapper;

    public AppUserController(AppUserServiceBasic appUserServiceBasic, AppUserMapper appUserMapper) {
        this.appUserServiceBasic = appUserServiceBasic;
        this.appUserMapper = appUserMapper;
    }

    @PostMapping
    public ResponseEntity<AppUserDTO> saveNew(@RequestBody AppUserDTO appUserDTO) {
        AppUser appUser = appUserMapper.mapToAppUser(appUserDTO);
        return ResponseEntity.ok(appUserMapper.mapToAppUserDTO(appUserServiceBasic.save(appUser)));
    }

    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAll() {
        return ResponseEntity.ok(appUserServiceBasic.findAll().stream()
                .map(appUserMapper::mapToAppUserDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{userLogin}")
    public ResponseEntity<AppUserDTO> findAppUserByLogin(@PathVariable String userLogin) {
        return appUserServiceBasic.findByUserLogin(userLogin)
                .map(appUserMapper::mapToAppUserDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
