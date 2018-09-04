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

    // Get task
    @GetMapping(value = "/messages/-find-by-id/{id}")
    public ResponseEntity<TaskMessage> getMessageById(@PathVariable long id) throws MyEntityNotFoundException {
        TaskMessage t = taskMessageService.findMessage(id);
        return new ResponseEntity<TaskMessage>(t, HttpStatus.OK);
    }

    // Get all task messages
    @GetMapping(value = "/messages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskMessage>> getAllTaskMessagess() throws MyEntityNotFoundException {
        List<TaskMessage> tasks = taskMessageService.getAllTaskMessages();
        return new ResponseEntity<List<TaskMessage>>(tasks, HttpStatus.OK);
    }

    // Get messages pertaining to task
    @GetMapping(value = "/messages/{no}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<TaskMessage>> getTaskMessagesByTask(@PathVariable("no") long no) throws MyEntityNotFoundException {
        List<TaskMessage> messages = taskMessageService.getTaskMessagesByTask(no);
        return new ResponseEntity<List<TaskMessage>>(messages, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "/messages/create-message", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<TaskMessage> createTaskMessage(@RequestBody TaskMessage m) {
        TaskMessage message = taskMessageService.createMessage(m);
        return new ResponseEntity<TaskMessage>(message, HttpStatus.CREATED);
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
