package HouseIt.controller.rest;

import HouseIt.entities.Apartment;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.IApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class ApartmentController {

    @Autowired
    private IApartmentService apartmentService;

    // Get all apartments
    @PostMapping(value = "/apartments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getApartments() {
        List<Apartment> apartments = apartmentService.getAllApartments();
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get apartments pertaining to building id
    @PostMapping(value = "/apartments/apartments-in-building")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@RequestBody long id) {
        List<Apartment> apartments = apartmentService.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Create apartment
    @PostMapping(value = "/apartments/create-apartment", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        apartmentService.createApartment(apartment);
        return new ResponseEntity<Apartment>(HttpStatus.CREATED);
    }

    // Update apartment
    @PutMapping(value = "/apartments/update-apartment", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> updateApartment(@RequestBody Apartment apartment) throws MyEntityNotFoundException {
        apartmentService.updateApartment(apartment);
        return new ResponseEntity<Apartment>(HttpStatus.OK);
    }

    // Delete apartment
    @DeleteMapping(value = "/apartments/delete-apartment/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> deleteApartment(@PathVariable("id") long id) throws MyEntityNotFoundException {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<Apartment>(HttpStatus.NO_CONTENT);
    }

}