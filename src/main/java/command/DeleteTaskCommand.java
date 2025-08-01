package src.main.java.command;

import src.main.java.model.Task;
import src.main.java.repository.TaskRepository;

import java.util.Scanner;

public class DeleteTaskCommand extends Command {

    private final Scanner scanner;
    private Task deletedTask;
    private Long taskId;
    private boolean isFirstExecution = true;

    public DeleteTaskCommand(TaskRepository taskRepository, Scanner scanner) {
        super(taskRepository);
        this.scanner = scanner;
        this.isUndoable = true;
    }

    @Override
    public void execute() {
        if (isFirstExecution) {
            System.out.println("Enter the task id to delete:");
            this.taskId = Long.parseLong(scanner.nextLine());
            isFirstExecution = false;
        }
        
        // Store the task before deleting it
        this.deletedTask = taskRepository.getAllTasks().stream()
            .filter(task -> task.getId().equals(taskId))
            .findFirst()
            .orElse(null);
            
        if (deletedTask != null) {
            taskRepository.deleteTask(taskId);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }
    
    @Override
    public void undo() {
        if (deletedTask != null) {
            taskRepository.addTask(deletedTask);
            // Update taskId to the new ID after re-adding
            this.taskId = deletedTask.getId();
            System.out.println("Undid delete task: " + deletedTask.getDescription());
        }
    }
    
    @Override
    public String getDescription() {
        return "Delete task" + (deletedTask != null ? " (" + deletedTask.getDescription() + ")" : "");
    }
}