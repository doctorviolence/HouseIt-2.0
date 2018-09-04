package HouseIt.controller.rest;

import HouseIt.entities.Tenant;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    // Get tenant
    @GetMapping(value = "/tenants/-find-by-id/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable long id) throws MyEntityNotFoundException {
        Tenant t = tenantService.findTenant(id);
        return new ResponseEntity<Tenant>(t, HttpStatus.OK);
    }

    // Get all tenants
    //@GetMapping(value = "/tenants")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    //public ResponseEntity<List<Tenant>> getAllTenants() throws MyEntityNotFoundException {
    //    List<Tenant> tenants = tenantService.getTenants();
    //    return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
    //}

    // Get tenants pertaining to apartment
    @GetMapping(value = "/tenants/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Tenant>> getTenants(@PathVariable long id) {
        List<Tenant> tenants = tenantService.getTenantsInApartment(id);
        return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
    }

    // Create tenant
    @PostMapping(value = "/tenants/create-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant t) {
        Tenant tenant = tenantService.createTenant(t);
        return new ResponseEntity<Tenant>(tenant, HttpStatus.CREATED);
    }

    // Update tenant
    @PutMapping(value = "/tenants/update-tenant", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant) throws MyEntityNotFoundException {
        tenantService.updateTenant(tenant);
        return new ResponseEntity<Tenant>(HttpStatus.OK);
    }

    // Delete tenant
    @DeleteMapping(value = "/tenants/delete-tenant/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tenant> deleteTenant(@PathVariable("id") long id) throws MyEntityNotFoundException {
        tenantService.deleteTenant(id);
        return new ResponseEntity<Tenant>(HttpStatus.NO_CONTENT);
    }

}