package src.main.java.command;

import src.main.java.model.Task;
import src.main.java.repository.TaskRepository;

import java.util.Scanner;

public class AddTaskCommand extends Command {

    private final Scanner scanner;
    private Long addedTaskId;
    private String taskDescription;
    private boolean isFirstExecution = true;

    public AddTaskCommand(TaskRepository taskRepository, Scanner scanner) {
        super(taskRepository);
        this.scanner = scanner;
        this.isUndoable = true;
    }

    @Override
    public void execute() {
        Task task = new Task();
        
        if (isFirstExecution) {
            System.out.println("Enter the task description:");
            this.taskDescription = scanner.nextLine();
            isFirstExecution = false;
        }
        
        task.setDescription(taskDescription);
        taskRepository.addTask(task);
        this.addedTaskId = task.getId();
        System.out.println("Task added: " + task.getDescription());
    }
    
    @Override
    public void undo() {
        if (addedTaskId != null) {
            taskRepository.deleteTask(addedTaskId);
            System.out.println("Undid add task with ID: " + addedTaskId);
        }
    }
    
    @Override
    public String getDescription() {
        return "Add task" + (addedTaskId != null ? " (ID: " + addedTaskId + ")" : "");
    }
}