package drive_me_to.security.app_user.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import drive_me_to.security.app_user.repository.AppUser;
import drive_me_to.security.app_user.service.AppUserServiceBasic;
import drive_me_to.security.app_user.web.resources.AppUserDTO;
import drive_me_to.security.app_user.web.resources.AppUserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

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

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Optional<AppUser> user = appUserServiceBasic.findByUserLogin(username);

                if (user.isPresent()) {
                    String access_token = JWT.create()
                            .withSubject(user.get().getUserLogin())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                            .withIssuedAt(new Date(System.currentTimeMillis()))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles", user.get().getRoles().stream().map(Enum::toString).collect(Collectors.toList()))
                            .sign(algorithm);
                    String refresh_token = JWT.create()
                            .withSubject(user.get().getUserLogin())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                            .withIssuedAt(new Date(System.currentTimeMillis()))
                            .withIssuer(request.getRequestURL().toString())
                            .sign(algorithm);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }


            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is not valid");
        }
    }
}
