package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class ListTasksByStatusCommand extends Command {

    private final String status;

    public ListTasksByStatusCommand(TaskRepository taskRepository, String status) {
        super(taskRepository);
        this.status = status;
    }

    @Override
    public void execute() {
        String displayStatus = switch (status) {
            case "TODO" -> "todo";
            case "IN_PROGRESS" -> "in progress";
            case "DONE" -> "done";
            default -> status.toLowerCase();
        };
        
        System.out.println("Tasks " + displayStatus + ":");
        System.out.println(taskRepository.getAllTaskByStatus(status));
    }
    
    @Override
    public String getDescription() {
        return "List tasks by status (" + status + ")";
    }
}