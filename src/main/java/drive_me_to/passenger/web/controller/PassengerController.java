package drive_me_to.passenger.web.controller;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.service.PassengerServiceBasic;
import drive_me_to.passenger.web.resources.PassengerDTO;
import drive_me_to.passenger.web.resources.PassengerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
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

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<PassengerDTO> findPassengerByMobile(@PathVariable String mobile) {
        return passengerServiceBasic.findByMobile(mobile)
                .map(passengerMapper::mapToPassengerDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PassengerDTO> updatePassengerDetails(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        passengerDTO.setId(id);
        Passenger passenger = passengerMapper.mapToPassenger(passengerDTO);
        return passengerServiceBasic.partialUpdate(passenger)
                .map(passengerMapper::mapToPassengerDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
