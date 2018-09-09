package HouseIt.controller.rest;

import HouseIt.exception.FileUploadException;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.Building;
import HouseIt.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    // Get building
    @GetMapping(value = "/buildings/find-by-id/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable long id) throws MyEntityNotFoundException {
        Building b = buildingService.findBuilding(id);
        return new ResponseEntity<Building>(b, HttpStatus.OK);
    }

    // Get buildings
    @GetMapping(value = "/buildings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Building>> getBuildings() throws MyEntityNotFoundException {
        List<Building> buildings = buildingService.getBuildings();
        return new ResponseEntity<List<Building>>(buildings, HttpStatus.OK);
    }

    // Create building
    @PostMapping(value = "/buildings/create-building", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> createBuilding(@RequestBody Building b) {
        Building building = buildingService.createBuilding(b);
        return new ResponseEntity<Building>(building, HttpStatus.CREATED);
    }

    // Update building
    @PutMapping(value = "/buildings/update-building", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> updateBuilding(@RequestBody Building building) throws MyEntityNotFoundException {
        buildingService.updateBuilding(building);
        return new ResponseEntity<Building>(HttpStatus.OK);
    }

    // Delete building
    @DeleteMapping(value = "/buildings/delete-building/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Building> deleteBuilding(@PathVariable("id") long id) throws MyEntityNotFoundException {
        buildingService.deleteBuilding(id);
        return new ResponseEntity<Building>(HttpStatus.NO_CONTENT);
    }

    // Upload building image
    @PostMapping(value = "/buildings/upload-image", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws FileUploadException, IOException {
        buildingService.uploadImage(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}