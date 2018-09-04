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

    // Get apartment
    @GetMapping(value = "/apartments/find-by-id/{id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable long id) throws MyEntityNotFoundException {
        Apartment a = apartmentService.findApartment(id);
        return new ResponseEntity<Apartment>(a, HttpStatus.OK);
    }

    // Get all apartments
    @GetMapping(value = "/apartments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getApartments() {
        List<Apartment> apartments = apartmentService.getAllApartments();
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get apartments pertaining to building id
    @GetMapping(value = "/apartments/apartments-in-building/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@PathVariable long id) {
        List<Apartment> apartments = apartmentService.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get empty apartments pertaining to building
    @GetMapping(value = "/apartments/empty-apartments-in-building/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getEmptyApartmentsInBuilding(@PathVariable long id) {
        List<Apartment> apartments = apartmentService.getEmptyApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }


    // Create apartment
    @PostMapping(value = "/apartments/create-apartment", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment a) {
        Apartment apartment = apartmentService.createApartment(a);
        return new ResponseEntity<Apartment>(apartment, HttpStatus.CREATED);
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