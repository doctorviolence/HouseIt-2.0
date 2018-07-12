package HouseIt.controller.rest;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Task;
import HouseIt.model.TaskMessage;
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
    private ITenantService service;

    // Get tasks pertaining to tenant
    @PostMapping(value = "/tenant/tasks/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<Task>> getTasksByTenantId(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Task> tasks = service.findTasksByTenantId(id);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Create task
    @PostMapping(value = "/tenant/create-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> createTask(@RequestBody Task t) {
        service.createTask(t);
        return new ResponseEntity<Task>(HttpStatus.CREATED);
    }

    // Update task
    @PutMapping(value = "/tenant/update-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> updateTask(@RequestBody Task t) throws MyEntityNotFoundException {
        service.updateTask(t);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    // Delete task
    @DeleteMapping(value = "/tenant/delete-task/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") long id) throws MyEntityNotFoundException {
        service.deleteTask(id);
        return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
    }

    // Get messages pertaining to task
    @PostMapping(value = "/tenant/messages/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<TaskMessage>> getTaskMessagesByTask(@PathVariable("no") long no) throws MyEntityNotFoundException {
        List<TaskMessage> messages = service.getTaskMessagesByTask(no);
        return new ResponseEntity<List<TaskMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/tenant/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> createTaskMessage(@RequestBody TaskMessage message) {
        service.createMessage(message);
        return new ResponseEntity<TaskMessage>(HttpStatus.CREATED);
    }

    // Update message
    @PutMapping(value = "/tenant/update-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> updateTaskMessage(@RequestBody TaskMessage message) throws MyEntityNotFoundException {
        service.updateMessage(message);
        return new ResponseEntity<TaskMessage>(HttpStatus.OK);
    }

    // Delete message
    @DeleteMapping(value = "/tenant/delete-message/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> deleteTaskMessage(@PathVariable("no") long no) throws MyEntityNotFoundException {
        service.deleteMessage(no);
        return new ResponseEntity<TaskMessage>(HttpStatus.NO_CONTENT);
    }

}