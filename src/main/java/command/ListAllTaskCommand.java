package src.main.java.command;

import src.main.java.repository.TaskRepository;

public class ListAllTaskCommand extends Command{

    public ListAllTaskCommand(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public void execute() {
        System.out.println("Tasks:");
        System.out.println(taskRepository.getAllTasks());
    }
    
    @Override
    public String getDescription() {
        return "List all tasks";
    }
}
