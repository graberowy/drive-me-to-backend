package drive_me_to.driver.repository;

import drive_me_to.data.Data;
import drive_me_to.data.enums.Languages;
import drive_me_to.trip.repository.Trip;
import drive_me_to.car.repository.Car;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "drivers")
public class Driver extends Data<Long> {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Languages.class)
    private Set<Languages> languages = new HashSet<>();
    @OneToOne(cascade = CascadeType.PERSIST)
    private Car car;
    @NotNull
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "driver")
    private List<Trip> orders = new ArrayList<>();



    public void setOrders(List<Trip> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
    }

}
