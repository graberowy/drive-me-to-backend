package drive_me_to.car.web.controller;

import drive_me_to.car.repository.Car;
import drive_me_to.car.service.CarServiceBasic;
import drive_me_to.car.web.resources.CarDTO;
import drive_me_to.car.web.resources.CarMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarMapper carMapper;
    private final CarServiceBasic carServiceBasic;

    public CarController(CarMapper carMapper, CarServiceBasic carServiceBasic) {
        this.carMapper = carMapper;
        this.carServiceBasic = carServiceBasic;
    }

    @PostMapping
    public ResponseEntity<CarDTO> saveNew (@RequestBody CarDTO carDTO) {
        Car car = carMapper.mapToCar(carDTO);
        return ResponseEntity.ok(carMapper.mapToCarDTO(carServiceBasic.save(car)));
    }
}
