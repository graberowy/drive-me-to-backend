package drive_me_to.passenger.web.controller;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.service.PassengerServiceBasic;
import drive_me_to.passenger.web.resources.PassengerDTO;
import drive_me_to.passenger.web.resources.PassengerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerServiceBasic passengerServiceBasic;
    private final PassengerMapper passengerMapper;

    public PassengerController(PassengerServiceBasic passengerServiceBasic, PassengerMapper passengerMapper) {
        this.passengerServiceBasic = passengerServiceBasic;
        this.passengerMapper = passengerMapper;
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> addNewPassenger(@RequestBody PassengerDTO passengerDTO) {
        Passenger passenger = passengerMapper.mapToPassenger(passengerDTO);
        return ResponseEntity.ok(passengerMapper.mapToPassengerDTO(passengerServiceBasic.save(passenger)));
    }
}
