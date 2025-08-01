package src.main.java.command;

import src.main.java.repository.TaskRepository;

import java.util.Scanner;

public class MarkTaskStatusCommand extends Command {

    private final Scanner scanner;
    private final String status;
    private final String displayName;
    private Long taskId;
    private String oldStatus;
    private boolean isFirstExecution = true;

    public MarkTaskStatusCommand(TaskRepository taskRepository, Scanner scanner, String status, String displayName) {
        super(taskRepository);
        this.scanner = scanner;
        this.status = status;
        this.displayName = displayName;
        this.isUndoable = true;
    }

    @Override
    public void execute() {
        if (isFirstExecution) {
            System.out.println("Enter the task id to mark as \"" + displayName + "\":");
            this.taskId = Long.parseLong(scanner.nextLine());
            isFirstExecution = false;
        }
        
        if (!taskRepository.checkIfTaskExists(taskId)) {
            System.out.println("Task not found. Run \"list\" to see all tasks.");
            return;
        }
        
        // Store old status before updating
        this.oldStatus = taskRepository.getAllTasks().stream()
            .filter(task -> task.getId().equals(taskId))
            .findFirst()
            .map(task -> task.getStatus().toString())
            .orElse(null);
        
        taskRepository.updateTaskStatus(taskId, status);
        System.out.println("Task status updated.");
    }
    
    @Override
    public void undo() {
        if (taskId != null && oldStatus != null) {
            taskRepository.updateTaskStatus(taskId, oldStatus);
            System.out.println("Undid status change for task ID " + taskId + " back to: " + oldStatus);
        }
    }
    
    @Override
    public String getDescription() {
        return "Mark task as " + displayName + (taskId != null ? " (ID: " + taskId + ")" : "");
    }
}