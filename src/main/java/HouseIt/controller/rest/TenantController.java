package HouseIt.controller.rest;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TenantController {

    @Autowired
    private ITenantService userService;

    // Get cases pertaining to tenant
    @PostMapping(value = "/tenant/cases")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<Case>> getCasesByTenantId(@RequestParam("id") long id) throws MyEntityNotFoundException {
        List<Case> cases = userService.findCasesByTenantId(id);
        return new ResponseEntity<List<Case>>(cases, HttpStatus.OK);
    }

    // Create case
    @PostMapping(value = "/tenant/create-case", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Case> createCase(@RequestBody Case c) {
        userService.createCase(c);
        return new ResponseEntity<Case>(HttpStatus.CREATED);
    }

    // Update case
    @PutMapping(value = "/tenant/update-case", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Case> updateCase(@RequestBody Case c) {
        userService.updateCase(c);
        return new ResponseEntity<Case>(HttpStatus.OK);
    }

    // Delete case
    @DeleteMapping(value = "/tenant/delete-case")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Case> deleteCase(@RequestParam("id") long id) throws MyEntityNotFoundException {
        userService.deleteCase(id);
        return new ResponseEntity<Case>(HttpStatus.NO_CONTENT);
    }

    // Get messages pertaining to case
    @PostMapping(value = "/tenant/messages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<CaseMessage>> getCaseMessagesByCase(@RequestParam("no") long no) throws MyEntityNotFoundException {
        List<CaseMessage> messages = userService.getCaseMessagesByCase(no);
        return new ResponseEntity<List<CaseMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/tenant/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<CaseMessage> createCaseMessage(@RequestBody CaseMessage message) {
        userService.createMessage(message);
        return new ResponseEntity<CaseMessage>(HttpStatus.CREATED);
    }

    // Update message
    @PutMapping(value = "/tenant/update-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<CaseMessage> updateCaseMessage(@RequestBody CaseMessage message) {
        userService.updateMessage(message);
        return new ResponseEntity<CaseMessage>(HttpStatus.OK);
    }

    // Delete message
    @DeleteMapping(value = "/tenant/delete-message")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<CaseMessage> deleteCaseMessage(@RequestParam("id") long id) throws MyEntityNotFoundException {
        userService.deleteMessage(id);
        return new ResponseEntity<CaseMessage>(HttpStatus.NO_CONTENT);
    }

}