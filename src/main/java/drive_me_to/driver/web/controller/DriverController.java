package drive_me_to.driver.web.controller;

import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.service.DriverServiceBasic;
import drive_me_to.driver.web.resources.DriverDTO;
import drive_me_to.driver.web.resources.DriverMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverMapper driverMapper;
    private final DriverServiceBasic driverServiceBasic;

    public DriverController(DriverMapper driverMapper, DriverServiceBasic driverServiceBasic) {
        this.driverMapper = driverMapper;
        this.driverServiceBasic = driverServiceBasic;
    }

    @PostMapping
    public ResponseEntity<DriverDTO> saveNew(@RequestBody DriverDTO driverDTO) {
        Driver driver = driverMapper.mapToDriver(driverDTO);
        return ResponseEntity.ok(driverMapper.mapToDriverDTO(driverServiceBasic.save(driver)));
    }
}
