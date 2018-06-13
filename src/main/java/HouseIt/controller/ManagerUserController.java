package HouseIt.controller;

import HouseIt.exception.ResourceNotFoundException;
import HouseIt.exception.MissingInformationException;
import HouseIt.model.*;
import HouseIt.service.IManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerUserController {

    @Autowired
    private IManagerUserService userService;

    // Get all buildings
    @PostMapping(value = "/m-buildings")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Building>> getBuildings() throws ResourceNotFoundException {
        List<Building> buildings = userService.getBuildings();
        return new ResponseEntity<List<Building>>(buildings, HttpStatus.OK);
    }

    // Get all apartments pertaining to building
    @PostMapping(value = "/m-apartments/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@PathVariable long id) throws ResourceNotFoundException {
        List<Apartment> apartments = userService.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get all tenants
    @PostMapping(value = "/m-tenants/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Tenant>> getTenantsInApartment(@PathVariable long id) throws ResourceNotFoundException {
        List<Tenant> tenants = userService.getTenantsInApartment(id);
        return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
    }

    // Create tenant
    @PostMapping(value = "m-create-tenant", consumes = "application/json")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) throws MissingInformationException {
        userService.createTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.CREATED);
    }

    // Update tenant
    @PutMapping(value = "m-update-tenant", consumes = "application/json")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant) throws MissingInformationException {
        userService.updateTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.OK);
    }

    // Delete tenant
    @DeleteMapping(value = "m-delete-tenant/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Tenant> deleteTenant(@PathVariable long id) throws ResourceNotFoundException {
        userService.deleteTenant(id);
        return new ResponseEntity<Tenant>(HttpStatus.NO_CONTENT);
    }

    // Get all cases
    @PostMapping(value = "/m-cases")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Case>> getCases() throws ResourceNotFoundException {
        List<Case> cases = userService.getCases();
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases pertaining to tenant
    @PostMapping(value = "/m-cases-by-tenant/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Case>> findCasesByTenantId(@PathVariable long id) throws ResourceNotFoundException {
        List<Case> cases = userService.findCasesByTenantId(id);
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases by type
    @PostMapping(value = "/m-cases-by-type/{type}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Case>> getCasesByType(@PathVariable String type) throws ResourceNotFoundException {
        List<Case> cases = userService.getCasesByType(type);
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases by fix date
    @PostMapping(value = "/m-cases-by-fix-date")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Case>> getCasesByFixDate() throws ResourceNotFoundException {
        List<Case> cases = userService.getCasesByFixDate();
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get messages pertaining to case
    @PostMapping(value = "/m-messages-by-case/{no}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<CaseMessage>> getMessagesByCase(@PathVariable long no) throws ResourceNotFoundException {
        List<CaseMessage> messages = userService.getMessagesByCase(no);
        return new ResponseEntity<List<CaseMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "m-create-message", consumes = "application/json")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<CaseMessage> createMessage(@RequestBody CaseMessage message) throws MissingInformationException {
        userService.createMessage(message);
        return new ResponseEntity<CaseMessage>(HttpStatus.CREATED);
    }

    // Delete message
    @DeleteMapping(value = "m-delete-message/{no}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<CaseMessage> deleteMessage(@PathVariable long no) throws ResourceNotFoundException {
        userService.deleteMessage(no);
        return new ResponseEntity<CaseMessage>(HttpStatus.NO_CONTENT);
    }

}