package auca.com.question5_task_api.controller;



import auca.com.question5_task_api.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> taskList = new ArrayList<>();

    public TaskController() {
        // Sample tasks
        taskList.add(new Task(1L, "Buy groceries", "Milk, eggs, bread", false, "MEDIUM", "2025-03-20"));
        taskList.add(new Task(2L, "Finish report", "Complete quarterly report", false, "HIGH", "2025-03-18"));
        taskList.add(new Task(3L, "Call mom", "Wish happy birthday", true, "LOW", "2025-03-15"));
    }

    // GET /api/tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskList;
    }

    // GET /api/tasks/{taskId}
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskList.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/tasks/status?completed={true/false}
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam boolean completed) {
        return taskList.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    // GET /api/tasks/priority/{priority}
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        return taskList.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    // POST /api/tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        Long newId = taskList.stream().mapToLong(Task::getTaskId).max().orElse(0) + 1;
        newTask.setTaskId(newId);
        taskList.add(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // PUT /api/tasks/{taskId}
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            if (t.getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                taskList.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/tasks/{taskId}/complete - mark as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskCompleted(@PathVariable Long taskId) {
        for (Task t : taskList) {
            if (t.getTaskId().equals(taskId)) {
                t.setCompleted(true);
                return ResponseEntity.ok(t);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/tasks/{taskId}
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = taskList.removeIf(t -> t.getTaskId().equals(taskId));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}