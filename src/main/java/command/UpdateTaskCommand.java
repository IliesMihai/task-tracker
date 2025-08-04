package src.main.java.command;

import src.main.java.model.Task;
import src.main.java.repository.TaskRepository;

import java.util.Scanner;

public class UpdateTaskCommand extends Command {

    private final Scanner scanner;
    private Long taskId;
    private String oldDescription;
    private String newDescription;
    private boolean isFirstExecution = true;

    public UpdateTaskCommand(TaskRepository taskRepository, Scanner scanner) {
        super(taskRepository);
        this.scanner = scanner;
        this.isUndoable = true;
    }

    @Override
    public void execute() {
        if (isFirstExecution) {
            System.out.println("Enter the task id:");
            this.taskId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter the new task description:");
            this.newDescription = scanner.nextLine();
            isFirstExecution = false;
        }
        
        if (!taskRepository.checkIfTaskExists(taskId)) {
            System.out.println("Task not found. Run \"list\" to see all tasks.");
            return;
        }
        
        // Store old description before updating
        this.oldDescription = taskRepository.getAllTasks().stream()
            .filter(task -> task.getId().equals(taskId))
            .findFirst()
            .map(Task::getDescription)
            .orElse(null);
        
        taskRepository.updateTaskDescription(taskId, newDescription);
        System.out.println("Task updated.");
    }
    
    @Override
    public void undo() {
        if (taskId != null && oldDescription != null) {
            taskRepository.updateTaskDescription(taskId, oldDescription);
            System.out.println("Undid update task ID " + taskId + " back to: " + oldDescription);
        }
    }
    
    @Override
    public String getDescription() {
        return "Update task" + (taskId != null ? " (ID: " + taskId + ")" : "");
    }
}