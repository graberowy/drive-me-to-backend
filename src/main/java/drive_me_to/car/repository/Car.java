package drive_me_to.car.repository;

import drive_me_to.data.Data;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.CarMake;
import drive_me_to.driver.repository.Driver;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car extends Data<Long> {
    @Column(name = "current_location")
    private String currentLocation;
    @NotNull
    @PositiveOrZero
    @Column(name = "price_per_km")
    private BigDecimal pricePerKm;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarMake make;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "car")
    private Driver driver;

}
