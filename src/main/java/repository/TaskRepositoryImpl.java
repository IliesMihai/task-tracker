package src.main.java.repository;

import src.main.java.json.JsonProcessor;
import src.main.java.json.JsonProcessorImpl;
import src.main.java.model.Status;
import src.main.java.model.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    private static final String FILE_PATH = "tasks.json";
    private final JsonProcessor jsonProcessor = new JsonProcessorImpl();

    public TaskRepositoryImpl() {
        createFileIfNotExists();
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String json = reader.lines().collect(Collectors.joining());
            tasks = jsonProcessor.parseJsonArray(json);
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        List<Task> tasks = getAllTasks();
        task.setId(generateTaskId());
        task.setStatus(Status.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        tasks.add(task);
        saveTasks(tasks);
    }

    @Override
    public void updateTaskDescription(Long id, String description) {
        List<Task> tasks = getAllTasks();
        tasks.forEach(task -> {
            if (task.getId().equals(id)) {
                task.setDescription(description);
                task.setUpdatedAt(LocalDateTime.now());
            }
        });
        saveTasks(tasks);
    }

    @Override
    public void updateTaskStatus(Long id, String status) {
        List<Task> tasks = getAllTasks();
        tasks.forEach(task -> {
            if (task.getId().equals(id)) {
                task.setStatus(Status.valueOf(status));
                task.setUpdatedAt(LocalDateTime.now());
            }
        });
        saveTasks(tasks);
    }

    @Override
    public boolean checkIfTaskExists(Long id) {
        List<Task> tasks = getAllTasks();
        return tasks.stream().anyMatch(task -> task.getId().equals(id));
    }

    @Override
    public void deleteTask(Long id) {
        List<Task> tasks = getAllTasks();
        tasks.removeIf(task -> task.getId().equals(id));
        saveTasks(tasks);
    }

    private void saveTasks(List<Task> tasks) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(jsonProcessor.serializeJsonArray(tasks));
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());

        }
    }

    private Long generateTaskId() {
        List<Task> tasks = getAllTasks();
        return tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("[]");
            } catch (Exception e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }
}