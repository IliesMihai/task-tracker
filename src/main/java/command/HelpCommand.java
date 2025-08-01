package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class HelpCommand extends Command {

    public HelpCommand(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");
        System.out.println("  - help : Show available commands");
        System.out.println("  - list : List all tasks");
        System.out.println("  - list todo : List all tasks with status \"todo\"");
        System.out.println("  - list in-progress : List all tasks with status \"in progress\"");
        System.out.println("  - list done : List all tasks with status \"done\"");
        System.out.println("  - add : Add a new task");
        System.out.println("  - update : Update a task");
        System.out.println("  - mark-in-progress : Mark a task as \"in progress\"");
        System.out.println("  - mark-done : Mark a task as \"done\"");
        System.out.println("  - delete : Delete a task");
        System.out.println("  - undo : Undo the last command");
        System.out.println("  - redo : Redo the last undone command");
        System.out.println("  - history : Show command history");
        System.out.println("  - exit : Exit the application");
    }
    
    @Override
    public String getDescription() {
        return "Help";
    }
}