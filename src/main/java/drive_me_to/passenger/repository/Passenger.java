package drive_me_to.passenger.repository;

import drive_me_to.data.Data;
import drive_me_to.security.app_user.repository.AppUser;
import drive_me_to.trip.repository.Trip;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "passengers")
public class Passenger extends Data<Long> {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String mobile;
    @NotNull
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "passenger")
    private List<Trip> trips = new ArrayList<>();
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "passenger")
    private AppUser appUser;

    public void setTrips(List<Trip> trips) {
        this.trips.clear();
        this.trips.addAll(trips);
    }
}
