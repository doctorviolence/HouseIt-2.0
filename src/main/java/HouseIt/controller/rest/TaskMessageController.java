package HouseIt.controller.rest;

import HouseIt.entities.TaskMessage;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITaskMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class TaskMessageController {

    @Autowired
    private ITaskMessageService taskMessageService;

    // Get messages pertaining to task
    @PostMapping(value = "/messages/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<TaskMessage>> getTaskMessagesByTask(@PathVariable("no") long no) throws MyEntityNotFoundException {
        List<TaskMessage> messages = taskMessageService.getTaskMessagesByTask(no);
        return new ResponseEntity<List<TaskMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/messages/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> createTaskMessage(@RequestBody TaskMessage message) {
        taskMessageService.createMessage(message);
        return new ResponseEntity<TaskMessage>(HttpStatus.CREATED);
    }

    // Update message
    @PutMapping(value = "/messages/update-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> updateTaskMessage(@RequestBody TaskMessage message) throws MyEntityNotFoundException {
        taskMessageService.updateMessage(message);
        return new ResponseEntity<TaskMessage>(HttpStatus.OK);
    }

    // Delete message
    @DeleteMapping(value = "/messages/delete-message/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> deleteTaskMessage(@PathVariable("no") long no) throws MyEntityNotFoundException {
        taskMessageService.deleteMessage(no);
        return new ResponseEntity<TaskMessage>(HttpStatus.NO_CONTENT);
    }

}
