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

@RestController
public class ManagerController {

    @Autowired
    private IManagerService service;

    // Get all buildings
    @PostMapping(value = "/manager/buildings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Building>> getBuildings() throws MyEntityNotFoundException {
        List<Building> buildings = service.getBuildings();
        return new ResponseEntity<List<Building>>(buildings, HttpStatus.OK);
    }

    // Get all apartments pertaining to building
    @PostMapping(value = "/manager/apartments/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Apartment>> getApartmentsInBuilding(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Apartment> apartments = service.getApartmentsInBuilding(id);
        return new ResponseEntity<List<Apartment>>(apartments, HttpStatus.OK);
    }

    // Get all tenants
    @PostMapping(value = "/manager/tenants/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Tenant>> getTenantsInApartment(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Tenant> tenants = service.getTenantsInApartment(id);
        return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
    }

    // Create tenant
    @PostMapping(value = "/manager/create-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        service.createTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.CREATED);
    }

    // Update tenant
    @PutMapping(value = "/manager/update-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant) throws MyEntityNotFoundException {
        service.updateTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.OK);
    }

    // Delete tenant
    @DeleteMapping(value = "/manager/delete-tenant/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Tenant> deleteTenant(@PathVariable("id") long id) throws MyEntityNotFoundException {
        service.deleteTenant(id);
        return new ResponseEntity<Tenant>(HttpStatus.NO_CONTENT);
    }

    // Get all tasks
    @PostMapping(value = "/manager/tasks")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Task>> getTasks() throws MyEntityNotFoundException {
        List<Task> tasks = service.getTasks();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks pertaining to tenant
    @PostMapping(value = "/manager/tasks-by-tenant/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Task>> findCasesByTenantId(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Task> tasks = service.findTasksByTenantId(id);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks by type
    @PostMapping(value = "/manager/tasks-by-type/{type}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Task>> getTasksByType(@PathVariable("type") String type) throws MyEntityNotFoundException {
        List<Task> tasks = service.getTasksByType(type);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks by fix date
    @PostMapping(value = "/manager/tasks-by-fix-date")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<Task>> getTasksByFixDate() throws MyEntityNotFoundException {
        List<Task> tasks = service.getTasksByFixDate();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get messages pertaining to task
    @PostMapping(value = "/manager/messages-by-task/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<TaskMessage>> getMessagesByTask(@PathVariable("no") long no) throws MyEntityNotFoundException {
        List<TaskMessage> messages = service.getTaskMessagesByTask(no);
        return new ResponseEntity<List<TaskMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/manager/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<TaskMessage> createMessage(@RequestBody TaskMessage message) {
        service.createMessage(message);
        return new ResponseEntity<TaskMessage>(HttpStatus.CREATED);
    }

    // Delete message
    @DeleteMapping(value = "/manager/delete-message/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<TaskMessage> deleteMessage(@PathVariable("no") long no) throws MyEntityNotFoundException {
        service.deleteMessage(no);
        return new ResponseEntity<TaskMessage>(HttpStatus.NO_CONTENT);
    }

}