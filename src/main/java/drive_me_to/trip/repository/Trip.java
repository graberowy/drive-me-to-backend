package drive_me_to.trip.repository;

import drive_me_to.data.Data;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.Languages;
import drive_me_to.data.enums.RouteType;
import drive_me_to.driver.repository.Driver;
import drive_me_to.passenger.repository.Passenger;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "trips")
public class Trip extends Data<Long> {
    @NotNull
    @NotBlank
    @Column(name = "start_address")
    private String startAddress;
    @NotNull
    @NotBlank
    @Column(name = "finish_address")
    private String finishAddress;
    @Column(name = "full_start_address")
    private String fullStartAddress;
    @Column(name = "full_finish_address")
    private String fullFinishAddress;
    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_language")
    private Languages preferLanguage;
    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_car_make")
    private CarMake preferCarMake;
    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_route_type")
    private RouteType preferRouteType;
    @Column(columnDefinition = "BIT")
    private Boolean paid;
    private BigDecimal price;
    private Double distance;
    @NotNull
    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Driver driver;
    @PositiveOrZero
    @Max(value = 5)
    private Integer rating;

    public void setRating(Integer rating) {
        if (paid) {
            this.rating = rating;
        }
    }

    @PreRemove
    private void removeOrderTripFromDriver() {
        if (driver != null) {
            driver.getOrders().remove(this);
        }
    }

}
