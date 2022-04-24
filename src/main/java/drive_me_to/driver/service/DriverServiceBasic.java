package drive_me_to.driver.service;

import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.repository.DriverRepository;
import drive_me_to.generic.service.GenericServiceBasic;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceBasic extends GenericServiceBasic<Driver, Long> implements DriverService {
    /**
     * This is constructor for use generic operations on specific type
     *
     * @param driverRepository specified type interface
     */
    public DriverServiceBasic(DriverRepository driverRepository) {
        super(driverRepository);
    }
}
