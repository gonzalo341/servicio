package prueba.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // 1. Obtener todas las tareas (GET /tasks)
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // 2. Crear una nueva tarea (POST /tasks)
    @PostMapping
    public Task createTask(@RequestBody Task newTask) {
        Task taskToSave = new Task(
                counter.incrementAndGet(),
                newTask.description(),
                false
        );
        tasks.add(taskToSave);
        return taskToSave;
    }

    // 3. Eliminar una tarea por su ID (DELETE /tasks/1)
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable long id) {
        boolean removed = tasks.removeIf(task -> task.id() == id);
        return removed ? "Tarea eliminada correctamente" : "No se encontró la tarea";
    }
    // 4. Marcar una tarea como completada (PUT /tasks/1)
    @PutMapping("/{id}")
    public Task completeTask(@PathVariable long id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.id() == id) {
                Task updated = new Task(current.id(), current.description(), true);
                tasks.set(i, updated);
                return updated;
            }
        }
        return null;
    }

}