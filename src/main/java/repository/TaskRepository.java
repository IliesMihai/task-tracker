package src.main.java.repository;

import src.main.java.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> getAllTasks();

    void addTask(Task task);

    void deleteTask(Long id);

    void updateTaskDescription(Long id, String description);

    void updateTaskStatus(Long id, String status);

    boolean checkIfTaskExists(Long id);
}
