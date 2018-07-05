package HouseIt.controller.rest;

import HouseIt.dal.IUserDao;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Apartment;
import HouseIt.model.Building;
import HouseIt.model.Manager;
import HouseIt.model.User;
import HouseIt.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private IAdminService adminUserService;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get buildings
    @PostMapping(value = "/admin/buildings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Building>> getBuildings() throws MyEntityNotFoundException {
        List<Building> buildings = adminUserService.getBuildings();
        return new ResponseEntity<List<Building>>(buildings, HttpStatus.OK);
    }

    // Create building
    @PostMapping(value = "/admin/create-building", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> createBuilding(@RequestBody Building building) {
        adminUserService.createBuilding(building);
        return new ResponseEntity<Building>(HttpStatus.CREATED);
    }

    // Update building
    @PutMapping(value = "/admin/update-building", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> updateBuilding(@RequestBody Building building) {
        adminUserService.updateBuilding(building);
        return new ResponseEntity<Building>(HttpStatus.OK);
    }

    // Delete building
    @DeleteMapping(value = "/admin/delete-building")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> deleteBuilding(@RequestParam("id") long id) throws MyEntityNotFoundException {
        adminUserService.deleteBuilding(id);
        return new ResponseEntity<Building>(HttpStatus.NO_CONTENT);
    }

    // Get apartments pertaining to building id
    @PostMapping(value = "/admin/apartments-in-building")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@PathVariable long id) throws MyEntityNotFoundException {
        List<Apartment> apartments = adminUserService.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Create apartment
    @PostMapping(value = "/admin/create-apartment", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        adminUserService.createApartment(apartment);
        return new ResponseEntity<Apartment>(HttpStatus.CREATED);
    }

    // Update apartment
    @PutMapping(value = "/admin/update-apartment", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> updateApartment(@RequestBody Apartment apartment) {
        adminUserService.updateApartment(apartment);
        return new ResponseEntity<Apartment>(HttpStatus.OK);
    }

    // Delete apartment
    @DeleteMapping(value = "/admin/delete-apartment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Apartment> deleteApartment(@RequestParam("id") long id) throws MyEntityNotFoundException {
        adminUserService.deleteApartment(id);
        return new ResponseEntity<Apartment>(HttpStatus.NO_CONTENT);
    }

    // Get managers
    @PostMapping(value = "/admin/managers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Manager>> getManagers() throws MyEntityNotFoundException {
        List<Manager> managers = adminUserService.getManagers();
        return new ResponseEntity<List<Manager>>(managers, HttpStatus.OK);
    }

    // Create manager
    @PostMapping(value = "/admin/create-manager", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
        adminUserService.createManager(manager);
        return new ResponseEntity<Manager>(HttpStatus.CREATED);
    }

    // Update manager
    @PutMapping(value = "/admin/update-manager", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> updateManager(@RequestBody Manager manager) {
        adminUserService.updateManager(manager);
        return new ResponseEntity<Manager>(HttpStatus.OK);
    }

    // Delete manager
    @DeleteMapping(value = "/admin/delete-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Manager> deleteManager(@RequestParam("id") long id) throws MyEntityNotFoundException {
        adminUserService.deleteManager(id);
        return new ResponseEntity<Manager>(HttpStatus.NO_CONTENT);
    }

    // Create user
    @PostMapping(value = "/admin/create-user", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createEntity(user);
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }

    // Update user
    @PutMapping(value = "/admin/update-user", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        adminUserService.updateUser(user);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping(value = "/admin/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@RequestParam("id") long id) throws MyEntityNotFoundException {
        adminUserService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}