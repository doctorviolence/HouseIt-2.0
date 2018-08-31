package HouseIt.controller.rest;

import HouseIt.entities.Task;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class TaskController {

    @Autowired
    private ITaskService taskService;

    // Get all tasks
    @GetMapping(value = "/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTasks() throws MyEntityNotFoundException {
        List<Task> tasks = taskService.getTasks();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks by type
    @GetMapping(value = "/tasks/tasks-by-type/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTasksByType(@PathVariable("type") String type) throws MyEntityNotFoundException {
        List<Task> tasks = taskService.getTasksByType(type);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks by fix date
    @GetMapping(value = "/tasks/tasks-by-fix-date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTasksByFixDate() throws MyEntityNotFoundException {
        List<Task> tasks = taskService.getTasksByFixDate();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks pertaining to tenant
    @GetMapping(value = "/tasks-by-tenant/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<Task>> getTasksByTenantId(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Task> tasks = taskService.findTasksByTenantId(id);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Create task
    @PostMapping(value = "/tasks/create-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> createTask(@RequestBody Task t) {
        Task task = taskService.createTask(t);
        return new ResponseEntity<Task>(task, HttpStatus.CREATED);
    }

    // Update task
    @PutMapping(value = "/tasks/update-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> updateTask(@RequestBody Task t) throws MyEntityNotFoundException {
        taskService.updateTask(t);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    // Delete task
    @DeleteMapping(value = "/tasks/delete-task/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") long id) throws MyEntityNotFoundException {
        taskService.deleteTask(id);
        return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
    }

}
