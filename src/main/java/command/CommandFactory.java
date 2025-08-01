package src.main.java.command;

import src.main.java.repository.TaskRepository;

import java.util.Scanner;

public class CommandFactory {
    private final TaskRepository taskRepository;
    private final Scanner scanner;
    private final CommandHistory history;

    public CommandFactory(TaskRepository taskRepository, Scanner scanner, CommandHistory history) {
        this.taskRepository = taskRepository;
        this.scanner = scanner;
        this.history = history;
    }

    public Command createCommand(String commandName) {
        return switch (commandName) {
            case "help" -> new HelpCommand(taskRepository);
            case "list" -> new ListAllTaskCommand(taskRepository);
            case "list todo" -> new ListTasksByStatusCommand(taskRepository, "TODO");
            case "list in-progress" -> new ListTasksByStatusCommand(taskRepository, "IN_PROGRESS");
            case "list done" -> new ListTasksByStatusCommand(taskRepository, "DONE");
            case "add" -> new AddTaskCommand(taskRepository, scanner);
            case "update" -> new UpdateTaskCommand(taskRepository, scanner);
            case "mark-in-progress" -> new MarkTaskStatusCommand(taskRepository, scanner, "IN_PROGRESS", "in progress");
            case "mark-done" -> new MarkTaskStatusCommand(taskRepository, scanner, "DONE", "done");
            case "delete" -> new DeleteTaskCommand(taskRepository, scanner);
            case "undo" -> new UndoCommand(taskRepository, history);
            case "redo" -> new RedoCommand(taskRepository, history);
            case "history" -> new HistoryCommand(taskRepository, history);
            default -> null;
        };
    }
}