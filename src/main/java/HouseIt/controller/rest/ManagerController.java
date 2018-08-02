package HouseIt.controller.rest;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class ManagerController {

    @Autowired
    private IManagerService managerService;

    // Get managers
    @PostMapping(value = "/managers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Manager>> getManagers() throws MyEntityNotFoundException {
        List<Manager> managers = managerService.getManagers();
        return new ResponseEntity<List<Manager>>(managers, HttpStatus.OK);
    }

    // Create manager
    @PostMapping(value = "/managers/create-manager", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
        managerService.createManager(manager);
        return new ResponseEntity<Manager>(HttpStatus.CREATED);
    }

    // Update manager
    @PutMapping(value = "/managers/update-manager", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> updateManager(@RequestBody Manager manager) throws MyEntityNotFoundException {
        managerService.updateManager(manager);
        return new ResponseEntity<Manager>(HttpStatus.OK);
    }

    // Delete manager
    @DeleteMapping(value = "/managers/delete-manager/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> deleteManager(@PathVariable("id") long id) throws MyEntityNotFoundException {
        managerService.deleteManager(id);
        return new ResponseEntity<Manager>(HttpStatus.NO_CONTENT);
    }

}