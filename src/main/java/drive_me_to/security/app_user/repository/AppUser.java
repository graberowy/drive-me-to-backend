package drive_me_to.security.app_user.repository;

import drive_me_to.data.Data;
import drive_me_to.data.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_users")
public class AppUser extends Data<Long> {
    @NotNull
    @NotBlank
    @Column(unique = true, name = "user_login")
    private String userLogin;
    @NotNull
    @NotBlank
    @Column(name = "user_password")
    private String userPassword;
    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleType.class)
    private Set<RoleType> roles = new HashSet<>();

}
