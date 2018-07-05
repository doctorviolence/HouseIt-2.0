package HouseIt.controller.rest;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    private IManagerService userService;

    // Get all buildings
    @PostMapping(value = "/manager/buildings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Building>> getBuildings() throws MyEntityNotFoundException {
        List<Building> buildings = userService.getBuildings();
        return new ResponseEntity<List<Building>>(buildings, HttpStatus.OK);
    }

    // Get all apartments pertaining to building
    @PostMapping(value = "/manager/apartments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@RequestParam("id") long id) throws MyEntityNotFoundException {
        List<Apartment> apartments = userService.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get all tenants
    @PostMapping(value = "/manager/tenants")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Tenant>> getTenantsInApartment(@RequestParam("id") long id) throws MyEntityNotFoundException {
        List<Tenant> tenants = userService.getTenantsInApartment(id);
        return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
    }

    // Create tenant
    @PostMapping(value = "/manager/create-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        userService.createTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.CREATED);
    }

    // Update tenant
    @PutMapping(value = "/manager/update-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant) {
        userService.updateTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.OK);
    }

    // Delete tenant
    @DeleteMapping(value = "/manager/delete-tenant")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> deleteTenant(@RequestParam("id") long id) throws MyEntityNotFoundException {
        userService.deleteTenant(id);
        return new ResponseEntity<Tenant>(HttpStatus.NO_CONTENT);
    }

    // Get all cases
    @PostMapping(value = "/manager/cases")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Case>> getCases() throws MyEntityNotFoundException {
        List<Case> cases = userService.getCases();
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases pertaining to tenant
    @PostMapping(value = "/manager/cases-by-tenant")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Case>> findCasesByTenantId(@RequestParam("id") long id) throws MyEntityNotFoundException {
        List<Case> cases = userService.findCasesByTenantId(id);
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases by type
    @PostMapping(value = "/manager/cases-by-type")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Case>> getCasesByType(@RequestParam("type") String type) throws MyEntityNotFoundException {
        List<Case> cases = userService.getCasesByType(type);
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get cases by fix date
    @PostMapping(value = "/manager/cases-by-fix-date")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Case>> getCasesByFixDate() throws MyEntityNotFoundException {
        List<Case> cases = userService.getCasesByFixDate();
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Get messages pertaining to case
    @PostMapping(value = "/manager/messages-by-case")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<CaseMessage>> getMessagesByCase(@RequestParam("no") long no) throws MyEntityNotFoundException {
        List<CaseMessage> messages = userService.getMessagesByCase(no);
        return new ResponseEntity<List<CaseMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/manager/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<CaseMessage> createMessage(@RequestBody CaseMessage message) {
        userService.createMessage(message);
        return new ResponseEntity<CaseMessage>(HttpStatus.CREATED);
    }

    // Delete message
    @DeleteMapping(value = "/manager/delete-message")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<CaseMessage> deleteMessage(@RequestParam("no") long no) throws MyEntityNotFoundException {
        userService.deleteMessage(no);
        return new ResponseEntity<CaseMessage>(HttpStatus.NO_CONTENT);
    }

}