package drive_me_to.trip.web.controller;

import drive_me_to.trip.repository.Trip;
import drive_me_to.trip.service.TripServiceBasic;
import drive_me_to.trip.web.resources.TripDTO;
import drive_me_to.trip.web.resources.TripMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TripController {

    private final TripServiceBasic tripServiceBasic;
    private final TripMapper tripMapper;

    public TripController(TripServiceBasic tripServiceBasic, TripMapper tripMapper) {
        this.tripServiceBasic = tripServiceBasic;
        this.tripMapper = tripMapper;
    }

    @GetMapping("/trips/{id}/summary")
    public ResponseEntity<TripDTO> getTripSummary(@PathVariable Long id) {
        return tripServiceBasic.findById(id)
                .map(tripMapper::mapToTripDTOSummary)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/trips")
    public ResponseEntity<TripDTO> saveNew(@RequestBody TripDTO tripDTO){
        Trip trip = tripMapper.mapToTripBasic(tripDTO);
        return ResponseEntity.ok(tripMapper.mapToTripDTOBasic(tripServiceBasic.save(trip)));
    }

    @PutMapping("/trips/{id}/paid/{status}/rating/{grade}")
    public ResponseEntity<TripDTO> updatePaidAndRating(@PathVariable Long id, @PathVariable Boolean status, @PathVariable Integer grade) {
        return tripServiceBasic.setPayAndGrade(id, status, grade)
                .map(tripMapper::mapToTripDTODetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
